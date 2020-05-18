package com.makers.lemoncook.src.editRecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeRecyclerViewAdapter;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeViewPagerAdapter;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;

import java.util.ArrayList;

public class EditRecipeActivity extends BaseActivity implements EditRecipeActivityView {

    ArrayList<Uri> mUri = new ArrayList<>();
    ArrayList<String> mStringUri = new ArrayList<>();
    String mMainUri;
    RecyclerView mRecyclerView;
    ViewPager mViewPager;
    int mCurNum = 0;
    EditRecipeRecyclerViewAdapter mEditRecipeRecyclerViewAdapter;
    EditRecipeViewPagerAdapter mEditRecipeViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        mViewPager = findViewById(R.id.edit_recipe_vp);
        mRecyclerView = findViewById(R.id.edit_recipe_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mEditRecipeRecyclerViewAdapter = new EditRecipeRecyclerViewAdapter(mUri, this, mCurNum);
        mRecyclerView.setAdapter(mEditRecipeRecyclerViewAdapter);

        mViewPager.setOffscreenPageLimit(5);
        mEditRecipeViewPagerAdapter = new EditRecipeViewPagerAdapter(getSupportFragmentManager(), 0, this, mUri);
        mViewPager.setAdapter(mEditRecipeViewPagerAdapter);

        Init();

    }

    void Init() {
        mMainUri = getIntent().getStringExtra("mMainUri");
        mStringUri = getIntent().getStringArrayListExtra("mStringUri");
        for (int i = 0; i < mStringUri.size(); i++) {
            mUri.add(Uri.parse(mStringUri.get(i)));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEditRecipeViewPagerAdapter.notifyDataSetChanged();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void change(final int idx) {
        mCurNum = idx;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(idx);
            }
        });
    }
}
