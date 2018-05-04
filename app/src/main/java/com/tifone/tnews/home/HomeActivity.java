package com.tifone.tnews.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = fm.findFragmentById(R.id.layout_container);
        if (fragment == null) {
            fragment = HomeFragment.newInstance();
            transaction.add(R.id.layout_container, fragment);
            transaction.commit();
        }
        initView();
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);

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
