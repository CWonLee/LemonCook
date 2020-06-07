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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.main.adapter.MainRecyclerViewAdapterStar;
import com.makers.lemoncook.src.recipeList.RecipeListActivity;

import java.util.ArrayList;

public class MainFragmentStar extends Fragment {

    TextView mTvText1;
    LinearLayout mLlKorean, mLlChinese, mLlJapanese, mLlWestern, mLlDesert, mLlEtc;

    public MainFragmentStar() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_fragment_star, container, false);

        mTvText1 = view.findViewById(R.id.star_tv_text1);
        mLlKorean = view.findViewById(R.id.fm_star_ll_korean);
        mLlChinese = view.findViewById(R.id.fm_star_ll_chinese);
        mLlJapanese = view.findViewById(R.id.fm_star_ll_japanese);
        mLlWestern = view.findViewById(R.id.fm_star_ll_western);
        mLlDesert = view.findViewById(R.id.fm_star_ll_desert);
        mLlEtc = view.findViewById(R.id.fm_star_ll_etc);

        mTvText1.setText(Html.fromHtml("오늘은 <b>백종원</b>의<br><b>차돌된장찌개</b><br>어떠세요?"));

        mLlKorean.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getContext(), RecipeListActivity.class);
                intent.putExtra("filter", "Star Recipe");
                intent.putExtra("category", "한식");
                startActivity(intent);
            }
        });
        mLlChinese.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getContext(), RecipeListActivity.class);
                intent.putExtra("filter", "Star Recipe");
                intent.putExtra("category", "중식");
                startActivity(intent);
            }
        });
        mLlJapanese.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getContext(), RecipeListActivity.class);
                intent.putExtra("filter", "Star Recipe");
                intent.putExtra("category", "일식");
                startActivity(intent);
            }
        });
        mLlWestern.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getContext(), RecipeListActivity.class);
                intent.putExtra("filter", "Star Recipe");
                intent.putExtra("category", "양식");
                startActivity(intent);
            }
        });
        mLlDesert.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getContext(), RecipeListActivity.class);
                intent.putExtra("filter", "Star Recipe");
                intent.putExtra("category", "디저트");
                startActivity(intent);
            }
        });
        mLlEtc.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(getContext(), RecipeListActivity.class);
                intent.putExtra("filter", "Star Recipe");
                intent.putExtra("category", "기타");
                startActivity(intent);
            }
        });

        return view;
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
