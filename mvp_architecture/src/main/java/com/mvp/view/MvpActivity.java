package com.mvp.view;

import android.os.Bundle;

import com.mvp.model.callback.MvpCallBack;
import com.mvp.presenter.MvpPresenter;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

public abstract class MvpActivity<P extends MvpPresenter> extends RxFragmentActivity implements MvpCallBack<P> {
    public final String TAG = getClass().getSimpleName();
    public P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mvpPresenter = createPresenter();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

}

