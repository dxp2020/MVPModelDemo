package com.base.retrofit;

import android.app.Dialog;
import android.content.Context;


public class RetrofitConfig {

    private static RetrofitConfig instance;
    private CallBack callBack;

    private RetrofitConfig() {
    }

    public static RetrofitConfig getInstance() {
        if (instance == null) {
            instance = new RetrofitConfig();
        }
        return instance;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public Dialog getLoadingDialog(Context context, String message, boolean cancelable) {
        return callBack.getLoadingDialog(context, message, cancelable);
    }

    public String getBaseUrl() {
        return callBack.getBaseUrl();
    }


    public interface CallBack {
        Dialog getLoadingDialog(Context context, String message, boolean cancelable);
        String getBaseUrl();
    }
}
