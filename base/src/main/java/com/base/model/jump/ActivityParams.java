package com.base.model.jump;



import com.base.fragment.BaseFragment;

import java.io.Serializable;

public class ActivityParams implements Serializable {
    private Class<? extends BaseFragment> mFragmentClazz;
    private IFragmentParams mFragmentParams;

    public ActivityParams(Class<? extends BaseFragment> mFragmentClazz, IFragmentParams mFragmentParams) {
        this.mFragmentClazz = mFragmentClazz;
        this.mFragmentParams = mFragmentParams;
    }

    public Class<? extends BaseFragment> getFragmentClazz() {
        return mFragmentClazz;
    }

    public IFragmentParams getFragmentParams() {
        return mFragmentParams;
    }

}