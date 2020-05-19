package com.makers.lemoncook.src.editRecipe.adapters;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.makers.lemoncook.src.editRecipe.fragments.EditRecipeFragment;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EditRecipeViewPagerAdapter extends FragmentPagerAdapter {
    private EditRecipeActivityView mEditRecipeActivityView;
    private ArrayList<Uri> mUri;
    private ArrayList<EditRecipeFragment> mFragments;

    public EditRecipeViewPagerAdapter(@NonNull FragmentManager fm, int behavior, EditRecipeActivityView editRecipeActivityView, ArrayList<Uri> arrayList, ArrayList<EditRecipeFragment> fragments) {
        super(fm, behavior);
        this.mEditRecipeActivityView = editRecipeActivityView;
        this.mUri = arrayList;
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mUri.size();
    }
}
