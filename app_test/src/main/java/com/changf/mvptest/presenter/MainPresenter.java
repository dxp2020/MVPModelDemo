package com.changf.mvptest.presenter;


import com.base.retrofit.SimpleSubscriber;
import com.changf.mvptest.model.bean.MainModel;

public class MainPresenter extends BasePresenter<MainPresenter.MainView> {

    public interface MainView {
        void getDataSuccess(MainModel model);
        void getDataFail(String msg);
    }

    public MainPresenter(MainPresenter.MainView view) {
        attachView(view);
    }

    public void loadData(String cityId) {
        addSubscription(apiStores.loadDataByRetrofitRxJava(cityId),
                new SimpleSubscriber<MainModel>(mActivity) {
                    @Override
                    public void onSuccess(MainModel result) {
                        mvpView.getDataSuccess(result);
                    }
                    @Override
                    public void onFailure(Throwable e) {
                        mvpView.getDataFail("数据加载失败");
                    }
                });
    }


}
