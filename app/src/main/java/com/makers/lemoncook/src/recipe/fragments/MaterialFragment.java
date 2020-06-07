package com.makers.lemoncook.src.recipe.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.recipe.adapters.MaterialRecyclerViewAdapter;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

import java.util.ArrayList;

public class MaterialFragment extends Fragment {

    private ResponseRecipe.Result mResult = new ResponseRecipe.Result();
    RecyclerView mRecyclerView;
    ImageView mIvBackground;
    MaterialRecyclerViewAdapter mMaterialRecyclerViewAdapter;
    private ArrayList<String> mData = new ArrayList<>();

    public MaterialFragment(ResponseRecipe.Result result, ArrayList<String> data) {
        this.mResult = result;
        this.mData = data;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material, container, false);

        mRecyclerView = view.findViewById(R.id.material_fm_rv);
        mIvBackground = view.findViewById(R.id.material_fm_iv_background);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMaterialRecyclerViewAdapter = new MaterialRecyclerViewAdapter(mData);
        mRecyclerView.setAdapter(mMaterialRecyclerViewAdapter);

        mIvBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(getContext()).load(mResult.getImage()).centerCrop().into(mIvBackground);
        
        return view;
    }
}
