package com.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.base.BaseApplication;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.mvp.presenter.MvpPresenter;
import com.mvp.view.MvpActivity;
import com.mvp.view.MvpFragment;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 根据项目需求添加相关代码
 */
public abstract class BaseFragment<P extends MvpPresenter> extends MvpFragment<P> {
    public MvpActivity mActivity;
    private Unbinder unbinder;
    private Bundle savedInstanceState;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            handleRebuild(savedInstanceState);//处理Activity被杀死重建
        }else{
            init();
        }
    }

    public void init() {
        mActivity = (MvpActivity)getActivity();
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        //注册ButterKnife
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        initData();
        initEvent();
    }

    /**
     * 反初始化
     */
    private void uninit() {
        //注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        //注销ButterKnife
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反初始化
        uninit();
        //监控fragment泄露
        if (AppUtils.isAppDebug()) {
            try {
                RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
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
    public void handleRebuild(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        init();
    }
    //返回键处理
    public boolean onBackPressed() {
        return false;
    }
    //获取用于重建的Bundle，可用于重建或者判断是否重建
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }
    //event bus 事件处理，必须重写
    @Subscribe
    public void onEventMainThread(Intent pIntent) {}


}
