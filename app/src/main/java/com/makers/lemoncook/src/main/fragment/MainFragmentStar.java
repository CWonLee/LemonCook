package com.makers.lemoncook.src.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.main.adapter.MainRecyclerViewAdapterStar;

public class MainFragmentStar extends Fragment {

    RecyclerView mRecyclerView;
    MainRecyclerViewAdapterStar mMainRecyclerViewAdapterStar;

    public MainFragmentStar() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_fragment_star, container, false);

        mRecyclerView = view.findViewById(R.id.rv_main_my);
        mMainRecyclerViewAdapterStar = new MainRecyclerViewAdapterStar();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMainRecyclerViewAdapterStar);

        return view;
    }
}
