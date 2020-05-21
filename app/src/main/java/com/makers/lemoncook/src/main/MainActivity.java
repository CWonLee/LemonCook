package com.makers.lemoncook.src.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.main.adapter.MainViewPagerAdapter;
import com.makers.lemoncook.src.myPage.MyPageActivity;
import com.makers.lemoncook.src.search.SearchActivity;

public class MainActivity extends BaseActivity {

    FragmentPagerAdapter mFragmentPagerAdapter;
    ViewPager mViewPager;
    TextView mTvMyRecipe, mTvStarRecipe;
    ImageView mIvLemon, mIvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.vp_main);
        mTvMyRecipe = findViewById(R.id.main_tv_my_recipe);
        mTvStarRecipe = findViewById(R.id.main_tv_star_recipe);
        mIvLemon = findViewById(R.id.main_iv_lemon);
        mIvSearch = findViewById(R.id.main_iv_search);

        mTvMyRecipe.setOnClickListener(movePageListener);
        mTvMyRecipe.setTag(0);
        mTvStarRecipe.setOnClickListener(movePageListener);
        mTvStarRecipe.setTag(1);

        mFragmentPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), 0);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTvMyRecipe.setTextColor(getResources().getColor(R.color.colorBlack));
                        mTvStarRecipe.setTextColor(getResources().getColor(R.color.colorMainGray));
                        break;
                    case 1:
                        mTvMyRecipe.setTextColor(getResources().getColor(R.color.colorMainGray));
                        mTvStarRecipe.setTextColor(getResources().getColor(R.color.colorBlack));
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIvLemon.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });
        mIvSearch.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            int tag = (int)v.getTag();
            mViewPager.setCurrentItem(tag);
        }
    };
}
