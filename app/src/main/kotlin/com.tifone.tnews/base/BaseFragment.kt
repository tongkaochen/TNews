package com.tifone.tnews.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    protected abstract fun inflaterViewId(): Int
    protected abstract fun initView(view: View)
    protected abstract fun initViewData()
    protected abstract fun fetchData()
    abstract fun forceRefresh()

    companion object Constant{
        @JvmStatic
        val FRAGMENT_ID: String = "fragment_id"

        fun setupBundle(name: String): Bundle {
            var bundle: Bundle = Bundle()
            bundle.putString(FRAGMENT_ID, name)
            return bundle
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater.inflate(inflaterViewId(), container, false)
        initView(view)
        initViewData()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchData()
    }
}