package com.base.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.base.BaseApplication;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.mvp.presenter.MvpPresenter;
import com.mvp.view.MvpDialog;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialog<P extends MvpPresenter> extends MvpDialog<P> {
    public boolean isFullScreen;//是否全屏
    private Unbinder unbinder;
    private OnInitViewListener mOnInitViewListener;
    private Bundle savedInstanceState;
    private boolean isInitView;//是否初始化dialog的view

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(isFullScreen){
            getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        isInitView = mRootView==null;
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            //处理Activity被杀死重建
            handleRebuild(savedInstanceState);
        } else {
            init();
        }
    }

    /**
     * 初始化
     */
    public void init() {
        //注册EventBus
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        //注册ButterKnife
        unbinder = ButterKnife.bind(this, mRootView);
        //此种情况，无需重新初始化，避免dismiss之后，重新初始化的问题
        if(!isInitView&&savedInstanceState==null){
            return;
        }
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
    public void onStart() {
        super.onStart();
        if (isFullScreen) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(params);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uninit();

        //监控内存泄露
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

    @Override
    public P createPresenter() {
        return null;
    }

    protected void initData() {}
    protected void initEvent() {}
    protected void initView() {
        if(mOnInitViewListener!=null){
            mOnInitViewListener.initView(mRootView);
        }
    }

    public interface OnInitViewListener {
        void initView(View view);
    }

    public void onInitViewListener(OnInitViewListener mOnInitView) {
        this.mOnInitViewListener = mOnInitView;
    }

    //处理页面重建
    public void handleRebuild(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        init();
    }
    //获取用于重建的Bundle，可用于重建或者判断是否重建
    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }
    //event bus 事件处理，必须重写
    @Subscribe
    public void onEventMainThread(Intent pIntent) {}

}
