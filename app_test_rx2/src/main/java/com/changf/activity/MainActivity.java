package com.changf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.base.activity.BaseActivity;
import com.base.model.jump.Static;
import com.changf.R;
import com.changf.fragment.TestDialogFragment;
import com.changf.fragment.TestFragment;
import com.changf.model.bean.MainModel;
import com.changf.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainPresenter.MainView {

    @BindView(R.id.tv_weather)
    TextView tvWeather;

    @Override
    protected void handleRebuild(Bundle savedInstanceState) {
        init();
    }

    @Override
    public void getDataSuccess(MainModel model) {
        MainModel.WeatherinfoBean weatherinfo = model.getWeatherinfo();
        String showData = getResources().getString(R.string.city) + weatherinfo.getCity()
                + getResources().getString(R.string.wd) + weatherinfo.getWD()
                + getResources().getString(R.string.ws) + weatherinfo.getWS()
                + getResources().getString(R.string.time) + weatherinfo.getTime();
        tvWeather.setText(showData);
    }

    @Override
    public void getDataFail(String msg) {
        tvWeather.setText(msg);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    @OnClick({R.id.btn_mvp_activity, R.id.btn_mvp_fragment, R.id.btn_mvp_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_mvp_activity:
                mvpPresenter.loadData("101310222");
                break;
            case R.id.btn_mvp_fragment:
                Static.jumpToFragment(mActivity, TestFragment.class, null);
                break;
            case R.id.btn_mvp_dialog:
                Static.jumpToFragment(mActivity, TestDialogFragment.class, null);
                break;
        }
    }
}
