package com.makers.lemoncook.src.editRecipe.adapters;

import android.net.Uri;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.makers.lemoncook.src.editRecipe.fragments.EditRecipeFragment;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditRecipeViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<EditRecipeFragment> mFragments;

    public EditRecipeViewPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<EditRecipeFragment> fragments) {
        super(fm, behavior);
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
