package com.mvp.presenter;


import com.mvp.view.MvpActivity;
import com.mvp.view.MvpDialog;
import com.mvp.view.MvpFragment;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MvpPresenter<V> implements Presenter<V>,Serializable {
    public MvpDialog mDialog;
    public MvpFragment mFragment;
    public MvpActivity mActivity;
    public V mvpView;

    @Override
    public void attachView(V view) {
        mvpView = view;
        if(mvpView instanceof MvpFragment){
            mFragment = (MvpFragment) mvpView;
            mActivity = (MvpActivity) mFragment.getActivity();
        }else if(mvpView instanceof MvpActivity){
            mActivity = (MvpActivity) mvpView;
        }else if(mvpView instanceof MvpDialog){
            mDialog = ((MvpDialog) mvpView);
            mActivity = (MvpActivity) mDialog.getActivity();
        }
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public <T> void addSubscription(Observable<T> observable, Observer<T> subscriber) {
        LifecycleTransformer<T> transformer;
        if (mDialog != null) {
            transformer = mDialog.bindUntilEvent(FragmentEvent.DESTROY);
        }else if (mFragment != null) {
            transformer = mFragment.bindUntilEvent(FragmentEvent.DESTROY);
        } else {
            transformer = mActivity.bindUntilEvent(ActivityEvent.DESTROY);
        }
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(subscriber);
    }

}
