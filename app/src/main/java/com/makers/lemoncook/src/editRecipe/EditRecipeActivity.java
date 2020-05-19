package com.makers.lemoncook.src.editRecipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeRecyclerViewAdapter;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeViewPagerAdapter;
import com.makers.lemoncook.src.editRecipe.fragments.EditRecipeFragment;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeRecyclerViewAdapterInterface;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.util.ArrayList;

public class EditRecipeActivity extends BaseActivity implements EditRecipeActivityView {

    ArrayList<Uri> mUri = new ArrayList<>();
    ArrayList<String> mStringUri = new ArrayList<>();
    ArrayList<EditRecipeFragment> mFragments = new ArrayList<>();
    String mMainUri;
    RecyclerView mRecyclerView;
    ViewPager mViewPager;
    ImageView mIvBack, mIvPlus;
    int mCurNum = 0;
    EditRecipeRecyclerViewAdapter mEditRecipeRecyclerViewAdapter;
    EditRecipeViewPagerAdapter mEditRecipeViewPagerAdapter;
    EditRecipeRecyclerViewAdapterInterface mEditRecipeRecyclerViewAdapterInterface;
    final static int PICKER_REQUEST_CODE = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        mIvBack = findViewById(R.id.edit_recipe_iv_back_btn);
        mViewPager = findViewById(R.id.edit_recipe_vp);
        mRecyclerView = findViewById(R.id.edit_recipe_rv);
        mIvPlus = findViewById(R.id.edit_recipe_iv_add_image);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mEditRecipeRecyclerViewAdapter = new EditRecipeRecyclerViewAdapter(mUri, this, this);
        mRecyclerView.setAdapter(mEditRecipeRecyclerViewAdapter);

        mEditRecipeViewPagerAdapter = new EditRecipeViewPagerAdapter(getSupportFragmentManager(), 0, this, mUri, mFragments);
        mViewPager.setAdapter(mEditRecipeViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(15);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mEditRecipeRecyclerViewAdapterInterface.changeCurNum(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Init();

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mIvPlus.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mUri.size() < 15) {
                    new GligarPicker().limit(15 - mUri.size()).disableCamera(false).requestCode(PICKER_REQUEST_CODE).withActivity(EditRecipeActivity.this).show();
                }
                else {
                    showCustomToast("최대 15장만 불러올 수 있습니다");
                }
            }
        });
    }

    void Init() {
        mMainUri = getIntent().getStringExtra("mMainUri");
        mStringUri = getIntent().getStringArrayListExtra("mStringUri");
        for (int i = 0; i < mStringUri.size(); i++) {
            mUri.add(Uri.parse(mStringUri.get(i)));
            EditRecipeFragment editRecipeFragment = new EditRecipeFragment(mUri.size(), mUri.get(i));
            mFragments.add(editRecipeFragment);
        }

        mEditRecipeViewPagerAdapter.notifyDataSetChanged();
        mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void change(int idx) {
        mCurNum = idx;

        mViewPager.setCurrentItem(idx);
    }

    @Override
    public void getInterface(EditRecipeRecyclerViewAdapterInterface editRecipeRecyclerViewAdapterInterface) {
        mEditRecipeRecyclerViewAdapterInterface = editRecipeRecyclerViewAdapterInterface;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != this.RESULT_OK) {
            return;
        }
        switch (requestCode){
            case PICKER_REQUEST_CODE : {
                String pathsList[]= data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.
                for (int i = pathsList.length - 1; i >= 0; i--) {
                    mUri.add(Uri.parse(pathsList[i]));
                    EditRecipeFragment editRecipeFragment = new EditRecipeFragment(mUri.size(), mUri.get(i));
                    mFragments.add(editRecipeFragment);
                }

                mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
                mEditRecipeViewPagerAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
