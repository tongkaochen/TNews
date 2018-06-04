package com.tifone.tnews.home

import android.os.Bundle
import com.tifone.tnews.base.BaseFragment

abstract class BaseHomeFragment : BaseFragment(), IHomeContrast.IHomeView {
    var mPresenter: IHomeContrast.IHomePresenter? = null
    abstract fun fragmentTitle(): String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenter(mPresenter)
    }
}