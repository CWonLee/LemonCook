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
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

public class MainRecipeFragment extends Fragment {

    private ResponseRecipe.Result mResult = new ResponseRecipe.Result();
    TextView mTvTitle, mTvFoodName, mTvHashTag;
    ImageView mIvBackground;

    public MainRecipeFragment(ResponseRecipe.Result result) {
        System.out.println(result.getName());
        this.mResult = result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_recipe, container, false);

        mTvTitle = view.findViewById(R.id.main_recipe_fm_tv_title);
        mTvFoodName = view.findViewById(R.id.main_recipe_fm_tv_food_name);
        mTvHashTag = view.findViewById(R.id.main_recipe_fm_tv_hash_tag);
        mIvBackground = view.findViewById(R.id.main_recipe_fm_iv_background);

        mTvTitle.setText(mResult.getTitle());
        mTvFoodName.setText(mResult.getName());
        mTvHashTag.setText(mResult.getHashTag());

        mIvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getContext()).load(mResult.getImage()).centerCrop().into(mIvBackground);
        
        return view;
    }
}
