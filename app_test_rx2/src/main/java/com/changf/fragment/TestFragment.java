package com.changf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.fragment.BaseFragment;
import com.changf.R;
import com.changf.model.bean.MainModel;
import com.changf.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TestFragment extends BaseFragment<MainPresenter> implements MainPresenter.MainView {

    @BindView(R.id.tv_weather)
    TextView tvWeather;

    @Override
    public int getLayoutId() {
        return R.layout.layout_test_fragment;
    }

    @OnClick({R.id.btn_obtain_weather})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_obtain_weather:
                mvpPresenter.loadData("101310222");
                break;
        }
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

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }
}
