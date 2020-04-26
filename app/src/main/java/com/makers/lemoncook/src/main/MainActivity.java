package com.makers.lemoncook.src.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.main.adapter.MainViewPagerAdapter;

public class MainActivity extends BaseActivity {

    FragmentPagerAdapter mFragmentPagerAdapter;
    ViewPager mViewPager;
    TextView mTvMyRecipe, mTvStarRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.vp_main);
        mTvMyRecipe = findViewById(R.id.main_tv_my_recipe);
        mTvStarRecipe = findViewById(R.id.main_tv_star_recipe);

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
    }
}
