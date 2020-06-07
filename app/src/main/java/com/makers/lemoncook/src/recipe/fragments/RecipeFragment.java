package com.makers.lemoncook.src.recipe.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;

public class RecipeFragment extends Fragment {

    private String mUrl, mContent;
    private int mNo;
    TextView mTvNo, mTvContent;
    ImageView mIvBackground;

    public RecipeFragment(String url, String content, int no) {
        this.mUrl = url;
        this.mContent = content;
        this.mNo = no;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        mTvNo = view.findViewById(R.id.recipe_fm_tv_num);
        mTvContent = view.findViewById(R.id.recipe_fm_tv_content);
        mIvBackground = view.findViewById(R.id.recipe_fm_iv);

        mTvNo.setText(Integer.toString(mNo + 1));
        mTvContent.setText(mContent);
        mIvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getContext()).load(mUrl).centerCrop().into(mIvBackground);

        return view;
    }
}
