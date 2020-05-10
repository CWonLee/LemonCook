package com.makers.lemoncook.src.main.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.main.adapter.MainRecyclerViewAdapterMy;

import java.util.ArrayList;

public class MainFragmentMy extends Fragment {

    RecyclerView mRecyclerView;
    MainRecyclerViewAdapterMy mMainRecyclerViewAdapterMy;
    TextView mTvText1;
    ArrayList<Integer> mImageList = new ArrayList<>();
    ArrayList<String> mTextList = new ArrayList<>();

    public MainFragmentMy() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_fragment_my, container, false);

        Init();

        mTvText1 = view.findViewById(R.id.my_tv_text1);
        mRecyclerView = view.findViewById(R.id.my_rv);
        mMainRecyclerViewAdapterMy = new MainRecyclerViewAdapterMy(mImageList, mTextList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 8, true));
        mRecyclerView.setAdapter(mMainRecyclerViewAdapterMy);

        mTvText1.setText(Html.fromHtml("오늘은 <b>어떤 요리</b>를<br>하셨나요?"));

        return view;
    }

    public void Init() {
        mImageList.add(R.drawable.ic_korean);
        mImageList.add(R.drawable.ic_chinese);
        mImageList.add(R.drawable.ic_japanese);
        mImageList.add(R.drawable.ic_western);
        mImageList.add(R.drawable.ic_dessert);
        mImageList.add(R.drawable.ic_etc);

        mTextList.add("한식");
        mTextList.add("중식");
        mTextList.add("일식");
        mTextList.add("양식");
        mTextList.add("디저트");
        mTextList.add("기타");
    }
}
