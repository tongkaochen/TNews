package com.tifone.tnews.home

import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.TableLayout
import com.tifone.tnews.R
import com.tifone.tnews.adapter.ViewPagerFragmentAdapter
import com.tifone.tnews.base.BaseFragment

class HomeFragment : BaseFragment() {
    var mTabLayout: TabLayout? = null
    var mAddItemIv: ImageView? = null
    var mViewPager: ViewPager? = null
    var mFragments: MutableList<BaseHomeFragment> = mutableListOf()

    companion object STATIC {

        fun newInstance(): HomeFragment {
            val bundle = Bundle()
            bundle.putString(FRAGMENT_ID, HomeFragment::class.java.name)
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun inflaterViewId(): Int  = R.layout.home_fragment_layout

    override fun initView(view: View) {
        mTabLayout = view.findViewById(R.id.tab_layout)
        mAddItemIv = view.findViewById(R.id.add_item_iv)
        mViewPager = view.findViewById(R.id.view_pager)

        mTabLayout?.tabMode = TabLayout.MODE_SCROLLABLE
        mTabLayout?.setupWithViewPager(mViewPager)
    }

    override fun initViewData() {
        initFragment()
        mViewPager?.adapter = ViewPagerFragmentAdapter(fragmentManager, mFragments)
    }

    private fun initFragment() {
        val res = activity?.resources
        val channelTitle = res?.getStringArray(R.array.channel_info)
        val channelId = res?.getStringArray(R.array.channel_info_id)
        if (channelId == null || channelTitle == null) {
            return
        }
        for (i in channelTitle.indices) {
            mFragments.add(HomeListFragment.newInstance(channelTitle[i], channelId[i]))
        }
    }

    override fun fetchData() {
    }

    override fun forceRefresh() {
        mFragments[mTabLayout!!.selectedTabPosition].forceRefresh()
    }

}