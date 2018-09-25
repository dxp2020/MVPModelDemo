package com.base.retrofit;


import android.content.Context;

import com.base.dialog.LoadingDialog;

import rx.Subscriber;

public abstract class SimpleSubscriber<T> extends Subscriber<T> {

    private LoadingDialog loading;

    public SimpleSubscriber() {
        super();
    }

    public SimpleSubscriber(Context context) {
        super();
        loading = new LoadingDialog(context);
        loading.show();
    }

    @Override
    public void onError(Throwable e) {
        if(loading!=null){
            loading.dismiss();
        }
        onFailure(e);
        onCompleted();
    }

    @Override
    public void onNext(T t) {
        if(loading!=null){
            loading.dismiss();
        }
        onSuccess(t);
    }

    @Override
    public void onCompleted() {}

    public abstract void onSuccess(T result);

    public void onFailure(Throwable e) {}

}
