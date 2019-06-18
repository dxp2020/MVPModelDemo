package com.changf.mvptest.view;


import android.os.Bundle;
import androidx.annotation.Nullable;

import com.base.dialog.BaseDialog;
import com.changf.mvptest.R;

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
