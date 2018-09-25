package com.base.model.jump;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.base.R;
import com.base.fragment.BaseFragment;
import com.base.activity.CommonActivity;

public class Static {

    /**
     * 根据tag找到fragment，并清空栈中fragment之上的所有元素
     * @param manager
     * @param name  TAG
     */
    public static void jumpToFragment(FragmentManager manager, String name) {
        manager.popBackStack(name,0);//通过name能找到回退栈的特定元素，0表示只弹出该元素以上的所有元素
    }

    /**
     * 替换FrameLayout中的Fragment
     * 当前Activity中只有to这一个Fragment，其余的Fragment由FragmentManager管理，并未添加到Activity中
     * 通过tag将Fragment添加到返回栈
     * @param manager
     * @param to
     */
    public static void jumpToFragment(FragmentManager manager, BaseFragment to) {
        String tag = to.getClass().getSimpleName();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_fragment_container, to,tag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 显示Fragment-->"to"
     * @param from
     * @param to
     */
    public static void jumpToFragment(BaseFragment from, BaseFragment to) {
        String tag = to.getClass().getSimpleName();
        FragmentTransaction transaction = from.getFragmentManager().beginTransaction();
        if (to.isAdded()) {
            transaction.hide(from).show(to);
        } else {
            transaction.hide(from).add(R.id.fl_fragment_container, to,tag);
        }
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 启动一个Activity并显示Fragment
     * @param context
     * @param fragmentClass
     * @param params
     */
    public static void jumpToFragment(Activity context, Class<? extends BaseFragment> fragmentClass, IFragmentParams params) {
        ActivityParams activityParams = new ActivityParams(fragmentClass, params);
        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtra(CommonActivity.PARAMS, activityParams);
        context.startActivity(intent);
    }

    /**
     * 启动一个Activity并显示Fragment
     * @param from
     * @param fragmentClass
     * @param requestCode
     */
    public static void jumpToFragmentForResult(BaseFragment from, Class<? extends BaseFragment> fragmentClass, int requestCode) {
        ActivityParams activityParams = new ActivityParams(fragmentClass, null);
        Intent intent = new Intent(from.getActivity(), CommonActivity.class);
        intent.putExtra(CommonActivity.PARAMS, activityParams);
        from.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动一个Activity并显示Fragment
     * @param context
     * @param fragmentClass
     * @param mFragmentParams
     * @param requestCode
     */
    public static void jumpToFragmentForResult(Activity context, Class<? extends BaseFragment> fragmentClass, IFragmentParams mFragmentParams, int requestCode) {
        ActivityParams activityParams = new ActivityParams(fragmentClass, mFragmentParams);
        Intent intent = new Intent(context, CommonActivity.class);
        intent.putExtra(CommonActivity.PARAMS, activityParams);
        context.startActivityForResult(intent, requestCode);
    }

}
