package com.changf.presenter;

import com.base.retrofit.ApiClient;
import com.changf.retrofit.ApiStores;
import com.mvp.presenter.MvpPresenter;

public class BasePresenter<V>  extends MvpPresenter<V> {
    public ApiStores apiStores = ApiClient.retrofit().create(ApiStores.class);
}
