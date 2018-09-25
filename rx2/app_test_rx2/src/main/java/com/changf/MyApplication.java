package com.changf;

import com.base.BaseApplication;
import com.base.retrofit.RetrofitConfig;
import com.changf.retrofit.RetrofitCallback;

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitConfig.getInstance().setCallBack(new RetrofitCallback());
    }

}
