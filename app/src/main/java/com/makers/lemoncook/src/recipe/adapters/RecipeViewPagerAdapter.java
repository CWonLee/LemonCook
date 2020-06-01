package com.makers.lemoncook.src.recipe.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.makers.lemoncook.src.recipe.fragments.MainRecipeFragment;
import com.makers.lemoncook.src.recipe.fragments.MaterialFragment;
import com.makers.lemoncook.src.recipe.fragments.RecipeFragment;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

import java.util.ArrayList;

public class RecipeViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> mUrl = new ArrayList<>();
    private ArrayList<ArrayList<String>> mMaterialList = new ArrayList<>();
    private ArrayList<Integer> mFragmentNo = new ArrayList<>();
    private ResponseRecipe.Result mResult = new ResponseRecipe.Result();
    private int mStartRecipeIdx;

    public RecipeViewPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<String> url, ArrayList<Integer> fragmentNo, int startRecipeIdx, ResponseRecipe.Result result, ArrayList<ArrayList<String>> materialList) {
        super(fm, behavior);
        this.mUrl = url;
        this.mFragmentNo = fragmentNo;
        this.mResult = result;
        this.mMaterialList = materialList;
        this.mStartRecipeIdx = startRecipeIdx;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (mFragmentNo.get(position) == 1) {
            MainRecipeFragment mainRecipeFragment = new MainRecipeFragment(mResult);
            return mainRecipeFragment;
        }
        else if (mFragmentNo.get(position) == 2) {
            MaterialFragment materialFragment = new MaterialFragment(mResult, mMaterialList.get(position - 1));
            System.out.println("포지션 : " + position);

            return materialFragment;
        }
        else {
            RecipeFragment recipeFragment = new RecipeFragment(mResult.getCookingOrder().get(position - mStartRecipeIdx).getCookingOrderImage(), mResult.getCookingOrder().get(position - mStartRecipeIdx).getContent(), mResult.getCookingOrder().get(position - mStartRecipeIdx).getCookingOrder() - 1);
            return recipeFragment;
        }
    }

    @Override
    public int getCount() {
        return mUrl.size();
    }
}
