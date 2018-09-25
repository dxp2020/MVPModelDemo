package com.changf.mvptest.model.transformer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.base.dialog.LoadingDialog;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/***
 * 用于RxJava的LoadingDialog进度提示弹窗
 * 这里封装了doOnSubscribe时show弹窗，doFinally时dismiss弹窗
 */
public class LoadingTransformer implements ObservableTransformer,DialogInterface.OnCancelListener {

    private final WeakReference<Context> mContextRef;
    private String mInfoText;
    private LoadingDialog mTaskDialog;
    private Disposable mDisposable;

    public LoadingTransformer(Context context, String text) {
        mContextRef = new WeakReference<>(context);
        mInfoText = text;
    }

    @Override
    public ObservableSource apply(Observable upstream) {
        return upstream.subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                mDisposable = disposable;
                if(mContextRef.get() == null){
                    disposable.dispose();
                    return;
                }
                if((mContextRef.get() instanceof Activity) && !TextUtils.isEmpty(mInfoText)){
                    showTaskDialog(mContextRef.get(),mInfoText);
                }
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                dismissTaskDialog();
            }
        });

    }

    private void showTaskDialog(Context context, String infoText) {
        mTaskDialog = onCreateTaskDialog(context, infoText);
        if (mTaskDialog != null) {
            try {
                mTaskDialog.show();
            } catch (Throwable tr) {
                tr.printStackTrace();
            }
        }
    }

    private LoadingDialog onCreateTaskDialog(Context context, String infoText) {
        if (context instanceof Activity) {
            return new LoadingDialog(context,infoText);
        }
        return null;
    }

    private void dismissTaskDialog() {
        if (mTaskDialog != null) {
            try {
                mTaskDialog.dismiss();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        try {
            if (cancel(true)) {
                dialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 创建弹窗
     * @param context
     * @param loadingTip
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyLoading(Context context, String loadingTip) {
        ObservableTransformer loadingTransformer = new LoadingTransformer(context, loadingTip);
        return (ObservableTransformer<T, T>)loadingTransformer;
    }

    /**
     * 是否被取消
     * @param isCancel
     * @return
     */
    public boolean cancel(boolean isCancel) {
        if (isCancel) {
            dismissTaskDialog();
            //解除订阅关系
            if (mDisposable != null) mDisposable.dispose();
            return true;
        }
        return false;
    }
}