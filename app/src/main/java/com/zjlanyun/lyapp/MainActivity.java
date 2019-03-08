package com.zjlanyun.lyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.zjlanyun.lyapp.activity.BaseActivity;
import com.zjlanyun.lyapp.adapter.MainViewPageAdapter;
import com.zjlanyun.lyapp.config.SPConfig;
import com.zjlanyun.lyapp.factory.MainFragmentFactory;
import com.zjlanyun.lyapp.utils.SPData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    //测试提交
    private final String TAG = "MainActivity";
    @BindView(R.id.fragment)
    FrameLayout fragment;

    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    private Context mContext = this;
    private int[] tabColors;
    private Handler handler = new Handler();
    private ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
    private AHBottomNavigationAdapter navigationAdapter;
    private MainViewPageAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initUI();
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }

        navView.setCheckedItem(R.id.nav_call);
        View headerLayout = navView.inflateHeaderView(R.layout.nav_header);
        TextView txUid = headerLayout.findViewById(R.id.uid);
        TextView txPwd = headerLayout.findViewById(R.id.pwd);
        txUid.setText(SPData.getUserinfo().getString(SPConfig.SP_UID));
        txPwd.setText(SPData.getUserinfo().getString(SPConfig.SP_PWD));

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initUI() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.bottom_navigation_menu);
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);

        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setColored(false);
        replaceFragment(MainFragmentFactory.getFragment(0));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @SuppressLint("RestrictedApi")
            public boolean onTabSelected(int position, boolean wasSelected) {
                replaceFragment(MainFragmentFactory.getFragment(position));
                if (position == 0) {
                    floatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    if (floatingActionButton.getVisibility() == View.VISIBLE) {
                        floatingActionButton.setVisibility(View.GONE);
                    }
                }


                return true;
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setting custom colors for notification
                AHNotification notification = new AHNotification.Builder()
                        .setText("12")
                        .setBackgroundColor(ContextCompat.getColor(mContext, R.color.red))
                        .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                        .build();
                bottomNavigation.setNotification(notification, 1);
                bottomNavigation.setNotification(notification, 0);
                bottomNavigation.setNotification(notification, 2);

//                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
//                        Snackbar.LENGTH_SHORT).show();

            }
        }, 3000);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }


    /**
     * Fragment
     *
     * @param fragment 需要跳转的fragment
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commitAllowingStateLoss();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
