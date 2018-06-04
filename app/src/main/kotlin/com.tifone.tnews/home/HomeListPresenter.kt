package com.tifone.tnews.home

import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.tifone.tnews.api.IMobileNewsApi
import com.tifone.tnews.bean.news.MultiNewsArticleBean
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean
import com.tifone.tnews.model.NetworkModel
import com.tifone.tnews.utils.RetrofitFactory
import com.tifone.tnews.utils.TimeUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Comparable
import java.lang.StringBuilder
import java.util.*

class HomeListPresenter(view: IHomeContrast.IHomeView) : IHomeContrast.IHomePresenter {
    private var mView: IHomeContrast.IHomeView = view
    private var random: Random = Random()
    private var mCategory: String? = null
    private var mTime: String = TimeUtils.getCurrentTimeStamp()
    private var mDataList: MutableList<MultiNewsArticleDataBean> = mutableListOf()
    private var mGson: Gson = Gson()
    private var mHistoryItemsId: MutableList<String> = mutableListOf()

    companion object STATIC {
        fun newInstance(view: IHomeContrast.IHomeView): HomeListPresenter {
            return HomeListPresenter(view)
        }
    }

    override fun loadData(vararg category: String) {
        if (mCategory == null) {
            mCategory = category[0]
        }
        mTime = if (mView.isLoadLatest()) {
            TimeUtils.getCurrentTimeStamp()
        } else {
            mDataList[mDataList.lastIndex].behot_time.toString()
        }
        getRandom().subscribeOn(Schedulers.io())
                .switchMap {
                    val dataList: MutableList<MultiNewsArticleBean.DataBean> = it.data
                    val newDataList: MutableList<MultiNewsArticleDataBean> = mutableListOf()
                    for (dataBean in dataList) {
                        val bean: MultiNewsArticleDataBean = mGson.fromJson(
                                dataBean.content, MultiNewsArticleDataBean::class.java)
                        if (TextUtils.isEmpty(bean.title)) {
                            continue
                        }
                        newDataList.add(bean)
                    }
                    // 返回
                    Observable.fromIterable(newDataList)
                }
                .filter {
                    val key = StringBuilder("")
                    key.append(it.group_id)
                            .append(it.item_id)
                            .append(it.item_version)
                    // 是否已经加载过，返回值
                    key.toString() !in mHistoryItemsId
                }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // consumer
                    doSetAdapter(it)
                }, {
                    // throwable
                    Log.e("tifone", "Get data error: ", it)
                })
    }

    override fun doSetAdapter(list: MutableList<MultiNewsArticleDataBean>) {
        mDataList.addAll(list)
        if (mView.isLoadLatest()) {
            val comparator: Comparator<MultiNewsArticleDataBean> = Comparator {
                bean1, bean2 ->
                // 对比规则
                bean2.behot_time - bean1.behot_time
            }
            Collections.sort(mDataList, comparator)
        }
        mView.onSetAdapter(mDataList)
    }

    private fun getRandom(): Observable<MultiNewsArticleBean> {
        return if (random.nextInt(10) % 2 == 0) {
            RetrofitFactory.getRetrofit().create(IMobileNewsApi::class.java).getNewsArticle(mCategory, mTime)
        } else {
            RetrofitFactory.getRetrofit().create(IMobileNewsApi::class.java).getNewsArticle2(mCategory, mTime)
        }
    }
}