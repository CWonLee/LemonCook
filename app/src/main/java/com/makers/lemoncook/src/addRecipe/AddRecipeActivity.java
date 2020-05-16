package com.makers.lemoncook.src.addRecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.addRecipe.adapters.AddRecipeViewPagerAdapter;

public class AddRecipeActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TextView mTvNewRecipe, mTvLoadRecipe;
    FragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        mViewPager = findViewById(R.id.add_recipe_vp);
        mTvNewRecipe = findViewById(R.id.add_recipe_tv_new);
        mTvLoadRecipe = findViewById(R.id.add_recipe_tv_load);

        mTvNewRecipe.setOnClickListener(movePageListener);
        mTvNewRecipe.setTag(0);
        mTvLoadRecipe.setOnClickListener(movePageListener);
        mTvLoadRecipe.setTag(1);

        mFragmentPagerAdapter = new AddRecipeViewPagerAdapter(getSupportFragmentManager(), 0);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTvNewRecipe.setTextColor(getResources().getColor(R.color.colorLoginBlack));
                        mTvLoadRecipe.setTextColor(getResources().getColor(R.color.colorLoginGray));
                        break;
                    case 1:
                        mTvNewRecipe.setTextColor(getResources().getColor(R.color.colorLoginGray));
                        mTvLoadRecipe.setTextColor(getResources().getColor(R.color.colorLoginBlack));
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
