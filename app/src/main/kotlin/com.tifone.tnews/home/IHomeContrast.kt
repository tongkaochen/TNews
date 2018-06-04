package com.tifone.tnews.home

import com.tifone.tnews.base.IPresenter
import com.tifone.tnews.base.IView
import com.tifone.tnews.bean.home.HomeTestBean
import com.tifone.tnews.bean.news.MultiNewsArticleDataBean

interface IHomeContrast {
    interface IHomeView : IView<IHomePresenter> {
        fun showData(bean: HomeTestBean)
        fun showEmpty()
        fun onSetAdapter(list: List<MultiNewsArticleDataBean>)
        fun isLoadLatest(): Boolean
    }
    interface IHomePresenter : IPresenter {
        fun loadData(vararg category: String)
        fun doSetAdapter(list: MutableList<MultiNewsArticleDataBean>)
    }
}