package com.makers.lemoncook.src.editRecipe.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeFragmentInterface;

import java.io.File;

public class EditRecipeFragment extends Fragment implements EditRecipeFragmentInterface {

    private Uri mUri;
    private int mNumber;
    ImageView mImageView, mIvDelete;
    TextView mTvNumber;
    EditText mEtContent;
    private EditRecipeActivityView mEditRecipeActivityView;

    public EditRecipeFragment(int number, Uri uri, EditRecipeActivityView editRecipeActivityView) {
        this.mNumber = number;
        this.mUri = uri;
        this.mEditRecipeActivityView = editRecipeActivityView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);

        mImageView = view.findViewById(R.id.fm_edit_recipe_iv);
        mTvNumber = view.findViewById(R.id.fm_edit_recipe_tv_img_num);
        mEtContent = view.findViewById(R.id.fm_edit_recipe_et_content);
        mIvDelete = view.findViewById(R.id.fm_edit_recipe_iv_delete_btn);

        Glide.with(getContext()).load(new File(mUri.getPath())).into(mImageView);
        mTvNumber.setText(String.valueOf(mNumber));

        mIvDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mEditRecipeActivityView.deleteImg();
            }
        });

        return view;
    }

    @Override
    public String getContent() {
        return mEtContent.getText().toString();
    }

    @Override
    public void changeNum (int idx) {
        mNumber = idx;
        mTvNumber.setText(String.valueOf(mNumber));
    }

    public String getNum () {
        return mTvNumber.getText().toString();
    }

    public abstract class OnSingleClickListener implements View.OnClickListener {
        //중복클릭시간차이
        private static final long MIN_CLICK_INTERVAL=1000;

        //마지막으로 클릭한 시간
        private long mLastClickTime;

        public abstract void onSingleClick(View v);

        @Override
        public final void onClick(View v) {
            //현재 클릭한 시간
            long currentClickTime= SystemClock.uptimeMillis();
            //이전에 클릭한 시간과 현재시간의 차이
            long elapsedTime=currentClickTime-mLastClickTime;
            //마지막클릭시간 업데이트
            mLastClickTime=currentClickTime;

            //내가 정한 중복클릭시간 차이를 안넘었으면 클릭이벤트 발생못하게 return
            if(elapsedTime<=MIN_CLICK_INTERVAL)
                return;
            //중복클릭시간 아니면 이벤트 발생
            onSingleClick(v);
        }
    }
}
