package com.makers.lemoncook.src.addRecipe.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.makers.lemoncook.src.addRecipe.fragments.LoadRecipeFragment;
import com.makers.lemoncook.src.addRecipe.fragments.NewRecipeFragment;

public class AddRecipeViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

    public AddRecipeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewRecipeFragment recipeFragment = new NewRecipeFragment();
                return recipeFragment;
            case 1:
                LoadRecipeFragment loadRecipeFragment = new LoadRecipeFragment();
                return loadRecipeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
