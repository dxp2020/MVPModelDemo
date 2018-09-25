package com.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.AppUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BaseApplication extends MultiDexApplication {
    private RefWatcher mRefWatcher;
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        MultiDex.install(this);//分包

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        if (AppUtils.isAppDebug()) {
            mRefWatcher = LeakCanary.install(this);
        }
    }

    public static Application getApplication() {
        return mApplication;
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }
}
