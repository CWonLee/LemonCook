package com.makers.lemoncook.src.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.main.adapter.MainViewPagerAdapter;

public class MainActivity extends BaseActivity {

    FragmentPagerAdapter mFragmentPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.vp_main);
        mFragmentPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), 0);
        mViewPager.setAdapter(mFragmentPagerAdapter);
    }
}
