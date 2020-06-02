package com.makers.lemoncook.src.editRecipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeRecyclerViewAdapter;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeViewPagerAdapter;
import com.makers.lemoncook.src.editRecipe.fragments.EditRecipeFragment;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeRecyclerViewAdapterInterface;
import com.makers.lemoncook.src.editRecipe.models.RequestPostRecipe;
import com.makers.lemoncook.src.editRecipe.models.ResponseUpload;
import com.makers.lemoncook.src.main.MainActivity;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditRecipeActivity extends BaseActivity implements EditRecipeActivityView {

    ArrayList<Uri> mUri = new ArrayList<>();
    ArrayList<String> mStringUri = new ArrayList<>();
    ArrayList<EditRecipeFragment> mFragments = new ArrayList<>();
    ArrayList<String> mContent = new ArrayList<>();
    int mCategory = -1;
    String mMainUri, mTitle, mFoodName, mHashTag, mMaterial;
    RecyclerView mRecyclerView;
    ViewPager mViewPager;
    ImageView mIvBack, mIvPlus;
    int mCurNum = 0;
    Button mBtnComplete;
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
        mBtnComplete = findViewById(R.id.edit_recipe_btn_complete);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mEditRecipeRecyclerViewAdapter = new EditRecipeRecyclerViewAdapter(mUri, this, this);
        mRecyclerView.setAdapter(mEditRecipeRecyclerViewAdapter);

        mEditRecipeViewPagerAdapter = new EditRecipeViewPagerAdapter(getSupportFragmentManager(), 0, mFragments);
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
                    showCustomToast("최대 15장까지 불러올 수 있습니다");
                }
            }
        });

        mBtnComplete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(EditRecipeActivity.this);
                    mProgressDialog.setMessage(getString(R.string.loading));
                    mProgressDialog.setIndeterminate(true);
                }
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        if (mFragments.isEmpty()) {
                            showCustomToast("레시피 과정을 추가해주세요");
                        }
                        else {
                            boolean emptyContent = false;
                            for (int i = 0; i < mFragments.size(); i++) {
                                if (mFragments.get(i).getContent().equals("")) {
                                    hideProgressDialog();
                                    showCustomToast("누락된 입력값이 있습니다");
                                    emptyContent = true;
                                    break;
                                }
                            }
                            if (!emptyContent) {
                                uploadImage();
                            }
                        }
                    }
                });
                mProgressDialog.show();
            }
        });
    }

    void Init() {
        mMainUri = getIntent().getStringExtra("mMainUri");
        mStringUri = getIntent().getStringArrayListExtra("mStringUri");
        mCategory = getIntent().getIntExtra("category", -1);
        mTitle = getIntent().getStringExtra("title");
        mFoodName = getIntent().getStringExtra("foodName");
        mHashTag = getIntent().getStringExtra("hashTag");
        mMaterial = getIntent().getStringExtra("material");
        for (int i = 0; i < mStringUri.size(); i++) {
            mUri.add(Uri.parse(mStringUri.get(i)));
            EditRecipeFragment editRecipeFragment = new EditRecipeFragment(mUri.size(), mUri.get(i), this);
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
    public void postRecipeSuccess(boolean isSuccess, int code, String message) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);
            Intent intent = new Intent(EditRecipeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void postRecipeFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void uploadImage() {
        ArrayList<MultipartBody.Part> files = new ArrayList<>();
        for (int i = 0; i < mUri.size(); i++) {
            File file = null;
            try {
                file = new Compressor(this).setQuality(70).compressToFile(new File(mUri.get(i).getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            files.add(MultipartBody.Part.createFormData("cookingOrderImage", file.getName(), requestBody));
        }
        EditRecipeService editRecipeService = new EditRecipeService(this);
        File mainFile = null;
        try {
            mainFile = new Compressor(this).setQuality(70).compressToFile(new File(Uri.parse(mMainUri).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("들어간값");
        for (int i = 0; i < files.size(); i++) {
            System.out.println(files.get(i));
        }
        RequestBody mainRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mainFile);
        editRecipeService.uploadImage(MultipartBody.Part.createFormData("image", mainFile.getName(), mainRequestBody), files);
    }

    @Override
    public void uploadSuccess(boolean isSuccess, int code, String message, ResponseUpload.Result result) {
        if (isSuccess && code == 200) {
            System.out.println("나온값");
            for (int i = 0; i < result.getCookingOrderImage().size(); i++) {
                System.out.println(result.getCookingOrderImage().get(i).getImageUrl());
            }
            postRecipe(result);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void uploadFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void deleteImg() {
        int deleteIdx = mViewPager.getCurrentItem();

        if (deleteIdx == mFragments.size() - 1) {
            mUri.remove(deleteIdx);
            mFragments.remove(deleteIdx);

            for (int i = 0; i < mFragments.size(); i++) {
                mFragments.get(i).changeNum(i + 1);
            }

            mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
            mEditRecipeViewPagerAdapter.notifyDataSetChanged();
            mEditRecipeRecyclerViewAdapterInterface.changeCurNum(mFragments.size() - 1);
        }
        else {
            mUri.remove(deleteIdx);
            mFragments.remove(deleteIdx);

            for (int i = 0; i < mFragments.size(); i++) {
                mFragments.get(i).changeNum(i + 1);
            }

            mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
            mEditRecipeViewPagerAdapter.notifyDataSetChanged();
        }
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
                    EditRecipeFragment editRecipeFragment = new EditRecipeFragment(mUri.size(), mUri.get(mUri.size() - 1), this);
                    mFragments.add(editRecipeFragment);
                }

                mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
                mEditRecipeViewPagerAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public void postRecipe(ResponseUpload.Result result) {
        EditRecipeService editRecipeService = new EditRecipeService(this);
        RequestPostRecipe requestPostRecipe = new RequestPostRecipe();
        requestPostRecipe.setTitle(mTitle);
        requestPostRecipe.setCategoryNo(mCategory);
        requestPostRecipe.setName(mFoodName);
        requestPostRecipe.setHashTag(mHashTag);
        requestPostRecipe.setIngredient(mMaterial);
        System.out.println("재료 : " + mMaterial);
        requestPostRecipe.setServing("1인분");
        requestPostRecipe.setImage(result.getImage().getImageUrl());
        ArrayList<RequestPostRecipe.CookingOrder> cookingOrders = new ArrayList<>();
        for (int i = 0; i < mFragments.size(); i++) {
            RequestPostRecipe.CookingOrder cookingOrder = new RequestPostRecipe.CookingOrder();
            cookingOrder.setContent(mFragments.get(i).getContent());
            cookingOrder.setCookingOrder(i + 1);
            cookingOrder.setCookingOrderImage(result.getCookingOrderImage().get(i).getImageUrl());
            cookingOrders.add(cookingOrder);
        }
        requestPostRecipe.setCookingOrder(cookingOrders);

        editRecipeService.postRecipe(requestPostRecipe);
    }
}
