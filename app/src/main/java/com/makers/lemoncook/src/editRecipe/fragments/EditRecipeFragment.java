package com.makers.lemoncook.src.editRecipe.fragments;

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

import java.io.File;

public class EditRecipeFragment extends Fragment {

    private Uri mUri;
    private int mNumber;
    ImageView mImageView;
    TextView mTvNumber;

    public EditRecipeFragment(int number, Uri uri) {
        this.mNumber = number;
        this.mUri = uri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_recipe, container, false);

        mImageView = view.findViewById(R.id.fm_edit_recipe_iv);
        mTvNumber = view.findViewById(R.id.fm_edit_recipe_tv_img_num);
        Glide.with(getContext()).load(new File(mUri.getPath())).into(mImageView);
        mTvNumber.setText(String.valueOf(mNumber));

        return view;
    }
}
