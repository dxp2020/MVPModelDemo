package com.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.R;


public class LoadingDialog extends Dialog {

    private Context context;
    private String message;
    private boolean cancelable=true;
    private ImageView tipImage;

    public LoadingDialog(Context context) {
        this(context, "", true);
    }

    public LoadingDialog(Context context, String message) {
        this(context, message, true);
    }

    public LoadingDialog(Context context, String message, boolean cancelable) {
        super(context, R.style.common_dialog_no_frame);
        this.context = context;
        this.cancelable = cancelable;
        this.message = message;
        init();
    }

    private void init() {
        View v = View.inflate(context, R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = v.findViewById(R.id.dialog_view);// 加载布局
        tipImage = v.findViewById(R.id.tipImage);
        TextView tipTextView = v.findViewById(R.id.tipTextView);// 提示文字
        if (TextUtils.isEmpty(message)) {
            tipTextView.setVisibility(View.GONE);
        }else{
            tipTextView.setText(message);// 设置加载信息
        }
        setCancelable(cancelable);// 可以用“返回键”取消
        setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局
    }

    @Override
    public void show() {
        super.show();
        if (tipImage != null) {
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                    context, R.anim.loading_animation);
            tipImage.startAnimation(hyperspaceJumpAnimation);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (tipImage != null) {
            tipImage.clearAnimation();
        }
    }

}