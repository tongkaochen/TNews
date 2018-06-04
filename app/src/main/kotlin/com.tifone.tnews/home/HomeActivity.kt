package com.tifone.tnews.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.tifone.tnews.R
import com.tifone.tnews.base.BaseActivity
import com.tifone.tnews.utils.BottomNavigationViewHelper

class HomeActivity : BaseActivity() {
    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private var mFragment: HomeFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity_layout)
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        mFragment = fm.findFragmentById(R.id.layout_container) as HomeFragment?
        if (mFragment == null) {
            mFragment = HomeFragment.newInstance()
            transaction.add(R.id.layout_container, mFragment)
            transaction.commit()
        }
        initView()
    }

    private fun initView() {
        mBottomNavigationView = findViewById(R.id.bottom_nav)
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView)
        mBottomNavigationView.setOnNavigationItemSelectedListener {
            mFragment?.forceRefresh()
            true
        }

        mToolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(mToolbar)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        var toggle = ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open_drawer_title, R.string.close_drawer_title)
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}