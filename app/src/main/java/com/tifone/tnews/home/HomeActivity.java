package com.tifone.tnews.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.tifone.tnews.R;
import com.tifone.tnews.base.BaseActivity;
import com.tifone.tnews.utils.BottomNavigationViewHelper;

/**
 * Created by tongkao.chen on 2018/5/3.
 */

public class HomeActivity extends BaseActivity {
    private BottomNavigationView mBottomNavigationView;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    HomeFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mFragment = (HomeFragment) fm.findFragmentById(R.id.layout_container);
        if (mFragment == null) {
            mFragment = HomeFragment.newInstance();
            transaction.add(R.id.layout_container, mFragment);
            transaction.commit();
        }
        initView();

    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mFragment.forceRefresh();
                return true;
            }
        });

        mToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        // 设置快捷打开侧栏的图标键
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer_title, R.string.close_drawer_title);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
