package com.base.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.base.R;
import com.base.fragment.BaseFragment;
import com.base.model.jump.ActivityParams;
import com.base.model.jump.IFragmentParams;

import java.lang.reflect.InvocationTargetException;

public class CommonActivity extends BaseActivity {
    public static final String PARAMS = "params";
    
    @Override
    public int getLayoutId() {
        return R.layout.activity_common_layout;
    }

    @Override
    protected void initView() {
        ActivityParams mParams = (ActivityParams) getIntent().getSerializableExtra(PARAMS);
        try {
            Fragment fragment = null;
            Class targetClass = mParams.getFragmentClazz();
            IFragmentParams mIFragmentParams = mParams.getFragmentParams();
            if (mIFragmentParams != null) {
                //通过fragment的静态方法newInstance(IFragmentParams)构造Fragment
                fragment = (Fragment) targetClass.getMethod("newInstance", IFragmentParams.class).invoke(null, mParams.getFragmentParams());
            } else {
                //通过fragment的静态方法newInstance()构造Fragment
                fragment = (Fragment) targetClass.getMethod("newInstance").invoke(null);
            }
            if (fragment == null) {
                finish();
                return;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment_container, fragment, targetClass.getSimpleName()).commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        // 横屏转竖屏
        int state = getResources().getConfiguration().orientation;
        if (state == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        // 在Fragment中拦截返回事件
        boolean isReturn = false;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null && fragment instanceof BaseFragment) {//可能null需判断
                if ( ((BaseFragment) fragment).onBackPressed() ) {
                    isReturn = true;
                }
            }
        }
        if (isReturn) {
            return;
        }
        // 若未拦截交给Activity处理
        super.onBackPressed();
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // super中回调Fragment的onActivityResult有问题，需自己回调Fragment的onActivityResult方法
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {//可能为null需判断
                fragment.onActivityResult(requestCode, resultCode, data);
            }
            for (Fragment childFragment : fragment.getChildFragmentManager().getFragments()) {
                if (childFragment != null) {//可能为null需判断
                    childFragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // super中回调Fragment的onRequestPermissionsResult有问题，需自己回调Fragment的onRequestPermissionsResult方法
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {//可能为null需判断
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


}
