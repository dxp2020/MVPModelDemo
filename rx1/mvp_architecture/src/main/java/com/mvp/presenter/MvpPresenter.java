package com.mvp.presenter;


import com.mvp.view.MvpActivity;
import com.mvp.view.MvpDialog;
import com.mvp.view.MvpFragment;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.FragmentEvent;

import java.io.Serializable;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class MvpPresenter<V> implements Presenter<V>,Serializable {
    public MvpDialog mDialog;
    public MvpFragment mFragment;
    public MvpActivity mActivity;
    public V mvpView;
    private CompositeSubscription mCompositeSubscription;

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
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
        }
        mvpView = null;
    }


    public <T> Subscription addSubscription(Observable<T> observable, Subscriber<T> subscriber) {
        Observable.Transformer<T, T> transformer;
        if (mDialog != null) {
            transformer = mDialog.bindUntilEvent(FragmentEvent.DESTROY);
        }else if (mFragment != null) {
            transformer = mFragment.bindUntilEvent(FragmentEvent.DESTROY);
        } else {
            transformer = mActivity.bindUntilEvent(ActivityEvent.DESTROY);
        }
        Subscription subscription =  observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        return subscription;
    }

}
