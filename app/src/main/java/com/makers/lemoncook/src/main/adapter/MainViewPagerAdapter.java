package com.makers.lemoncook.src.main.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.main.fragment.MainFragmentMy;
import com.makers.lemoncook.src.main.fragment.MainFragmentStar;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MainFragmentMy mainFragmentMy = new MainFragmentMy();
                return mainFragmentMy;
            case 1:
                MainFragmentStar mainFragmentStar = new MainFragmentStar();
                return mainFragmentStar;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
