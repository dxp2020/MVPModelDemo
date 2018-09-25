package com.changf.retrofit;

import android.app.Dialog;
import android.content.Context;

import com.base.retrofit.RetrofitConfig;
import com.base.dialog.LoadingDialog;
import com.changf.model.bean.CommonValues;

public class RetrofitCallback implements RetrofitConfig.CallBack {

    @Override
    public Dialog getLoadingDialog(Context context, String message, boolean cancelable) {
        return new LoadingDialog(context,message,cancelable);
    }

    @Override
    public String getBaseUrl() {
        return CommonValues.API_SERVER_URL;
    }
}
