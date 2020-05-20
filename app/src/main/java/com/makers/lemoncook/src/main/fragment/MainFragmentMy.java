package com.makers.lemoncook.src.main.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.addRecipe.AddRecipeActivity;
import com.makers.lemoncook.src.main.adapter.MainRecyclerViewAdapterMy;

import java.util.ArrayList;

public class MainFragmentMy extends Fragment {

    RecyclerView mRecyclerView;
    MainRecyclerViewAdapterMy mMainRecyclerViewAdapterMy;
    TextView mTvText1;
    ArrayList<Integer> mImageList = new ArrayList<>();
    ArrayList<String> mTextList = new ArrayList<>();
    Button mBtnAddRecipe;

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
        mBtnAddRecipe = view.findViewById(R.id.my_btn_plus_recipe);

        mMainRecyclerViewAdapterMy = new MainRecyclerViewAdapterMy(mImageList, mTextList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 8, true));
        mRecyclerView.setAdapter(mMainRecyclerViewAdapterMy);

        mTvText1.setText(Html.fromHtml("오늘은 <b>어떤 요리</b>를<br>하셨나요?"));

        mBtnAddRecipe.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getContext(), AddRecipeActivity.class);
                getActivity().startActivity(intent);
            }
        });

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

    public abstract class OnSingleClickListener implements View.OnClickListener {
        //중복클릭시간차이
        private static final long MIN_CLICK_INTERVAL=600;

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
