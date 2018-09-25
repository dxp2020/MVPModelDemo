package com.changf.view;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.base.dialog.BaseDialog;
import com.blankj.utilcode.util.ScreenUtils;
import com.changf.R;
import com.changf.model.bean.MainModel;
import com.changf.presenter.MainPresenter;
import com.mvp.presenter.MvpPresenter;
import com.mvp.view.MvpDialog;

public class FullScreenDialog extends BaseDialog {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFullScreen = true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_full_screen;
    }

}
