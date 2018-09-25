package com.changf.mvptest.view;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.dialog.BaseDialog;
import com.changf.mvptest.R;
import com.changf.mvptest.model.bean.MainModel;
import com.changf.mvptest.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageDialog extends BaseDialog<MainPresenter> implements MainPresenter.MainView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.bind_emailedite)
    EditText bindEmailedite;
    @BindView(R.id.ll_title)
    RelativeLayout llTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_center_line)
    View tvCenterLine;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private OnLoadDataListener mLoadDataListener;

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
    }

    public TextView getTitle() {
        return tvTitle;
    }

    public TextView getContent() {
        return tvContent;
    }

    public void setContent(String content) {
        this.tvContent.setText(content);
    }

    public TextView getCancle() {
        return tvCancle;
    }

    public void setCancle(String str) {
        this.tvCancle.setText(str);
    }

    public TextView getConfirm() {
        return tvConfirm;
    }

    public void setConfirm(String str) {
        this.tvConfirm.setText(str);
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("title",tvTitle.getText().toString());
//        outState.putString("content",tvContent.getText().toString());
//        outState.putString("confirm",tvConfirm.getText().toString());
//        outState.putString("cancle",tvCancle.getText().toString());
//    }
//
    @Override
    public void handleRebuild(Bundle savedInstanceState) {
//        String title = savedInstanceState.getString("title");
//        String content = savedInstanceState.getString("content");
//        String confirm = savedInstanceState.getString("confirm");
//        String cancle = savedInstanceState.getString("cancle");
//        setTitle(title);
//        setContent(content);
//        setConfirm(confirm);
//        setCancle(cancle);
        init();
    }

    @OnClick({R.id.tv_cancle, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                dismiss();
                break;
            case R.id.tv_confirm:
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
        if(mLoadDataListener!=null){
            mLoadDataListener.showData(showData);
            dismiss();
        }
    }

    @Override
    public void getDataFail(String msg) {
        if(mLoadDataListener!=null){
            mLoadDataListener.showData(msg);
            dismiss();
        }
    }

    public void setLoadDataListener(OnLoadDataListener onLoadDataListener) {
        mLoadDataListener = onLoadDataListener;
    }

    public interface OnLoadDataListener {
        void showData(String content);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_dialog_layout;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

}
