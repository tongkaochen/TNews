package com.tifone.tnews.adapter

import android.support.v4.app.FragmentManager

import android.support.v4.app.FragmentStatePagerAdapter
import com.tifone.tnews.home.BaseHomeFragment

class ViewPagerFragmentAdapter(
        fragmentManager: FragmentManager?
) : FragmentStatePagerAdapter(fragmentManager) {
    var mDataSet: List<BaseHomeFragment>? = null

    constructor(fragmentManager: FragmentManager?, dataSet: List<BaseHomeFragment>) : this(fragmentManager) {
        mDataSet = dataSet
    }

    override fun getItem(position: Int): BaseHomeFragment? = mDataSet?.get(position)

    override fun getCount(): Int = mDataSet?.size ?: 0

    override fun getPageTitle(position: Int): CharSequence? {
        return mDataSet?.get(position)?.fragmentTitle() ?: ""
    }
}