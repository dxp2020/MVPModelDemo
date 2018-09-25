package com.changf.mvptest.presenter;

import com.base.retrofit.ApiClient;
import com.changf.mvptest.retrofit.ApiStores;
import com.mvp.presenter.MvpPresenter;

public class BasePresenter<V>  extends MvpPresenter<V> {
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);
}
