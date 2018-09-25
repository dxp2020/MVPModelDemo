package com.mvp.model.callback;

public interface MvpCallBack<P> {
    int getLayoutId();
    P createPresenter();
}
