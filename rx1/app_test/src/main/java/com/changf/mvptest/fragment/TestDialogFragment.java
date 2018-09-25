package com.changf.mvptest.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.base.dialog.BaseDialog;
import com.base.fragment.BaseFragment;
import com.changf.mvptest.R;
import com.changf.mvptest.model.bean.MainModel;
import com.changf.mvptest.presenter.MainPresenter;
import com.changf.mvptest.view.FullScreenDialog;
import com.changf.mvptest.view.MessageDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class TestDialogFragment extends BaseFragment<MainPresenter> implements MainPresenter.MainView {

    @BindView(R.id.tv_weather)
    TextView tvWeather;
    private MessageDialog mMessageDialog;
    private FullScreenDialog mFullScreenDialog;

    @Override
    public int getLayoutId() {
        return R.layout.layout_test_dialog_fragment;
    }

    @Override
    public void handleRebuild(Bundle savedInstanceState) {
        super.handleRebuild(savedInstanceState);
        mMessageDialog = (MessageDialog) getFragmentManager().findFragmentByTag("MessageDialog");
        mFullScreenDialog = (FullScreenDialog) getFragmentManager().findFragmentByTag("FullScreenDialog");
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

    private void setMessageDialogListener(MessageDialog dialog) {
        if (dialog == null) {
            return;
        }
        dialog.onInitViewListener(new BaseDialog.OnInitViewListener() {
            @Override
            public void initView(View view) {
                dialog.setContent("通过dialog获取天气信息");
                dialog.setConfirm("确定");
                dialog.setCancle("取消");
            }
        });
        dialog.setLoadDataListener(new MessageDialog.OnLoadDataListener() {
            @Override
            public void showData(String content) {
                tvWeather.setText(content);
            }
        });
    }

    public static TestDialogFragment newInstance() {
        return new TestDialogFragment();
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @OnClick({R.id.btn_show_dialog, R.id.btn_show_full_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_dialog:
                if (mMessageDialog==null) {
                    mMessageDialog= new MessageDialog();
                    setMessageDialogListener(mMessageDialog);
                }
                mMessageDialog.show(getActivity().getSupportFragmentManager(), "MessageDialog");
                break;
            case R.id.btn_show_full_dialog:
                if (mFullScreenDialog == null ) {
                    mFullScreenDialog = new FullScreenDialog();
                }
                mFullScreenDialog.show(getActivity().getSupportFragmentManager(), "FullScreenDialog");
                break;
        }
    }
}
