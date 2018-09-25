package com.base.retrofit;


import android.content.Context;

import com.base.dialog.LoadingDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class SimpleSubscriber<T> implements Observer<T> {

    private LoadingDialog mLoading;
    private Disposable mDisposable;//相当于RxJava1.x中的Subscription,用于解除订阅

    public SimpleSubscriber() {
        super();
    }

    public SimpleSubscriber(Context context) {
        super();
        mLoading = new LoadingDialog(context);
        mLoading.show();
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onNext(T t) {
        if(mLoading!=null){
            mLoading.dismiss();
        }
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if(mLoading!=null){
            mLoading.dismiss();
        }
        onFailure(e);
        onComplete();
    }

    /**
     * 解除订阅
     */
    public void unSubscription(){
        if (mDisposable!=null&&!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public abstract void onSuccess(T result);

    public void onFailure(Throwable e) {}
    @Override
    public void onComplete() {
        unSubscription();
    }

}
