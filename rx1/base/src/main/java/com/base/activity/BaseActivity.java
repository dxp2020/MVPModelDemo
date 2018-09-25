package com.base.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.base.BaseApplication;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.mvp.presenter.MvpPresenter;
import com.mvp.view.MvpActivity;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 根据项目需求添加相关代码
 */
public abstract class BaseActivity<P extends MvpPresenter> extends MvpActivity<P> {
    public Activity mActivity;
    public Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            handleRebuild(savedInstanceState);//处理Activity被杀死重建
        }else{
            init();
        }
    }

    /**
     * 初始化
     */
    public void init() {
        mActivity = this;
        //全屏的情况下，隐藏导航栏
        if(ScreenUtils.isFullScreen(mActivity)&& BarUtils.isNavBarVisible(mActivity)) {
            BarUtils.setNavBarVisibility(mActivity,false);
        }
        //注册ButterKnife
        unbinder = ButterKnife.bind(this);
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        initView();
        initData();
        initEvent();
    }

    /**
     * 反初始化
     */
    private void uninit(){
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uninit();
        //监控内存泄露
        if (AppUtils.isAppDebug()) {
            try {
                RefWatcher refWatcher = BaseApplication.getRefWatcher(mActivity);
                if(refWatcher!=null){
                    refWatcher.watch(this);
                }
            } catch (Exception e) {
                LogUtils.e(e);
            }
        }
    }

    protected void initView() {}
    protected void initData() {}
    protected void initEvent() {}
    //处理页面重建
    protected void handleRebuild(Bundle savedInstanceState) {init();}
    //event bus 事件处理，必须重写
    @Subscribe
    public void onEventMainThread(Intent pIntent) {}
}
