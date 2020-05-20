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
import com.makers.lemoncook.src.main.adapter.MainRecyclerViewAdapterStar;

import java.util.ArrayList;

public class MainFragmentStar extends Fragment {

    RecyclerView mRecyclerView;
    MainRecyclerViewAdapterStar mMainRecyclerViewAdapterStar;
    TextView mTvText1;
    ArrayList<Integer> mImageList = new ArrayList<>();
    ArrayList<String> mTextList = new ArrayList<>();

    public MainFragmentStar() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_fragment_star, container, false);

        Init();

        mTvText1 = view.findViewById(R.id.star_tv_text1);
        mRecyclerView = view.findViewById(R.id.star_rv);

        mTvText1.setText(Html.fromHtml("오늘은 <b>백종원</b>의<br><b>차돌된장찌개</b><br>어떠세요?"));

        mMainRecyclerViewAdapterStar = new MainRecyclerViewAdapterStar(mImageList, mTextList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 8, true));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mMainRecyclerViewAdapterStar);

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
