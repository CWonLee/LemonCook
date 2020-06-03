package com.makers.lemoncook.src.recipe;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.makers.lemoncook.R;

public class CustomDialogDelete extends Dialog {
    private ConstraintLayout mPositiveButton, mNegativeButton;
    private View.OnClickListener mPositiveListener, mNegativeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.88f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.custom_dialog_delete);

        //셋팅
        mPositiveButton=findViewById(R.id.custom_dialog_logout_cl_positive);
        mNegativeButton=findViewById(R.id.custom_dialog_logout_cl_negative);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mPositiveButton.setOnClickListener(mPositiveListener);
        mNegativeButton.setOnClickListener(mNegativeListener);
    }

    public CustomDialogDelete(@NonNull Context context, View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        super(context);
        this.mPositiveListener = positiveListener;
        this.mNegativeListener = negativeListener;
    }
}
