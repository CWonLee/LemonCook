package com.makers.lemoncook.src.editRecipe.adapters;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.makers.lemoncook.src.editRecipe.fragments.EditRecipeFragment;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;

import java.util.ArrayList;

public class EditRecipeViewPagerAdapter extends FragmentPagerAdapter {
    private EditRecipeActivityView mEditRecipeActivityView;
    private ArrayList<Uri> mUri;

    public EditRecipeViewPagerAdapter(@NonNull FragmentManager fm, int behavior, EditRecipeActivityView editRecipeActivityView, ArrayList<Uri> arrayList) {
        super(fm, behavior);
        this.mEditRecipeActivityView = editRecipeActivityView;
        this.mUri = arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        EditRecipeFragment editRecipeFragment = new EditRecipeFragment(position + 1, mUri.get(position));

        return editRecipeFragment;
    }

    @Override
    public int getCount() {
        return mUri.size();
    }
}
