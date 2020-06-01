package com.makers.lemoncook.src.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.recipe.adapters.RecipeRecyclerViewAdapter;
import com.makers.lemoncook.src.recipe.adapters.RecipeViewPagerAdapter;
import com.makers.lemoncook.src.recipe.interfaces.RecipeActivityView;
import com.makers.lemoncook.src.recipe.interfaces.RecipeRecyclerViewInterface;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeActivity extends BaseActivity implements RecipeActivityView {

    ImageView mIvBackBtn, mIvZZim;
    RecyclerView mRecyclerView;
    RecipeRecyclerViewAdapter mRecipeRecyclerViewAdapter;
    RecipeRecyclerViewInterface mRecipeRecyclerViewInterface;
    ViewPager mViewPager;
    ResponseRecipe.Result mResult = new ResponseRecipe.Result();
    RecipeViewPagerAdapter mRecipeViewPagerAdapter;
    ArrayList<String> mUrl = new ArrayList<>();
    ArrayList<Integer> mFragmentNo = new ArrayList<>();
    ArrayList<ArrayList<String>> mMaterial = new ArrayList<>();
    int mStartRecipeIdx = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mIvBackBtn = findViewById(R.id.recipe_iv_back_btn);
        mIvZZim = findViewById(R.id.recipe_iv_lemon);
        mRecyclerView = findViewById(R.id.recipe_rv);
        mViewPager = findViewById(R.id.recipe_vp);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        Init();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mRecipeRecyclerViewInterface.changeCurNum(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIvBackBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    void Init() {
        showProgressDialog();
        int recipeNo = getIntent().getIntExtra("recipeNo", -1);
        RecipeService recipeService = new RecipeService(this);
        System.out.println("recipeNo : " + recipeNo);
        recipeService.getRecipe(recipeNo);
    }

    @Override
    public void getRecipeSuccess(boolean isSuccess, int code, String message, ResponseRecipe.Result result) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            mResult = result;
            if (result.getIsSave() == 1) {
                mIvZZim.setImageResource(R.drawable.ic_lemon_fill);
            }
            else {
                mIvZZim.setImageResource(R.drawable.ic_lemon_emty);
            }
            mUrl.add(result.getImage());
            mFragmentNo.add(1);
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < result.getIngredient().size(); i++) {
                System.out.println("들어감 : " + result.getIngredient().get(i).getIngredient());
                temp.add(result.getIngredient().get(i).getIngredient());
                if (i % 6 == 5) {
                    ArrayList<String> temp2 = new ArrayList<>();
                    temp2.addAll(temp);
                    mStartRecipeIdx++;
                    mMaterial.add(temp2);
                    temp.clear();
                }
                else if (i % 6 != 5 && i == result.getIngredient().size() - 1) {
                    ArrayList<String> temp2 = new ArrayList<>();
                    temp2.addAll(temp);
                    mStartRecipeIdx++;
                    mMaterial.add(temp2);
                }
            }
            for (int i = 0; i < mMaterial.size(); i++) {
                mUrl.add(result.getImage());
                mFragmentNo.add(2);
            }
            for (int i = 0; i < result.getCookingOrder().size(); i++) {
                mUrl.add(result.getCookingOrder().get(i).getCookingOrderImage());
                mFragmentNo.add(3);
            }

            mRecipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(mUrl, this, this);
            mRecyclerView.setAdapter(mRecipeRecyclerViewAdapter);

            mRecipeViewPagerAdapter = new RecipeViewPagerAdapter(getSupportFragmentManager(), 0, mUrl, mFragmentNo, mStartRecipeIdx, mResult, mMaterial);
            mViewPager.setAdapter(mRecipeViewPagerAdapter);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void getRecipeFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void getInterface(RecipeRecyclerViewInterface recipeRecyclerViewInterface) {
        mRecipeRecyclerViewInterface = recipeRecyclerViewInterface;
    }

    @Override
    public void change(int idx) {
        mViewPager.setCurrentItem(idx);
    }
}
