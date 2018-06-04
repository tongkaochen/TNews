package com.tifone.tnews.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tifone.mfrv.pullload.PullLoadRecyclerView
import com.tifone.tnews.R
import com.tifone.tnews.adapter.HomePagerViewAdapter
import com.tifone.tnews.bean.home.HomeTestBean
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean

class HomeListFragment : BaseHomeFragment(),
        PullLoadRecyclerView.OnLoadListener,
        PullLoadRecyclerView.OnRefreshListener {

    var mRecyclerView: PullLoadRecyclerView? = null
    var mAdapter: HomePagerViewAdapter? = null
    var isRefreshDataStarted = false
    var isLoadDataStarted = false
    var mCategoryId = ""

    companion object Constant {
        val CATEGORY_ID: String = "category_id"

        fun newInstance(fragmentTitle: String, categoryId: String): HomeListFragment {
            var bundle = setupBundle(fragmentTitle)
            bundle.putString(CATEGORY_ID, categoryId)
            var fragment = HomeListFragment()
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCategoryId = arguments!!.getString(CATEGORY_ID)
    }

    override fun fragmentTitle(): String {
        return arguments?.getString(FRAGMENT_ID) ?: ""
    }

    override fun inflaterViewId(): Int = R.layout.home_list_frament_layout

    override fun initView(view: View) {
        mRecyclerView = view.findViewById(R.id.list_item_recycler_view)
        mRecyclerView?.layoutManager = LinearLayoutManager(activity)
        mAdapter = context?.let { HomePagerViewAdapter(it) }
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.setOnLoadListener(this)
        mRecyclerView?.setOnRefreshListener(this)
        mRecyclerView?.setLoadMoreBehavior(PullLoadRecyclerView.LOAD_BEHAVIOR_STYLE_NORMAL)
    }

    override fun initViewData() {
    }

    override fun fetchData() {
        mPresenter!!.loadData(mCategoryId)
    }

    override fun forceRefresh() {
        mRecyclerView?.scrollToPosition(0)
        mRecyclerView?.notifyDoRefresh()
    }

    override fun showData(bean: HomeTestBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showEmpty() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSetAdapter(list: List<MultiNewsArticleDataBean>) {
        if (isRefreshDataStarted) {
            mRecyclerView?.notifyRefreshCompleted()
            isRefreshDataStarted = false
        }
        if (isLoadDataStarted) {
            mRecyclerView?.notifyLoadComplete()
            isLoadDataStarted = false
        }
        mAdapter?.dataSet = list
    }

    override fun isLoadLatest(): Boolean  = !isLoadDataStarted

    override fun setPresenter(presenter: IHomeContrast.IHomePresenter?) {
        if (mPresenter == null) {
            mPresenter = HomeListPresenter.newInstance(this)
        }
    }

    override fun onLoadStarted() {
        isLoadDataStarted = true
        fetchData()
    }

    override fun onRefreshStarted() {
        isRefreshDataStarted = true
        fetchData()
    }
}