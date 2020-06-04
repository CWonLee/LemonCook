package com.makers.lemoncook.src.editRecipe;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeRecyclerViewAdapter;
import com.makers.lemoncook.src.editRecipe.adapters.EditRecipeViewPagerAdapter;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.interfaces.EditRecipeRecyclerViewAdapterInterface;
import com.makers.lemoncook.src.editRecipe.models.EditRecipeViewpagerItem;
import com.makers.lemoncook.src.editRecipe.models.RequestModifyRecipe;
import com.makers.lemoncook.src.editRecipe.models.RequestPostRecipe;
import com.makers.lemoncook.src.editRecipe.models.ResponseUpload;
import com.makers.lemoncook.src.main.MainActivity;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditRecipeActivity extends BaseActivity implements EditRecipeActivityView {

    ArrayList<Uri> mUri = new ArrayList<>();
    ArrayList<String> mStringUri = new ArrayList<>();
    ArrayList<Boolean> mIsUri = new ArrayList<>();
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
    int mNewMainImage = -1;
    int mRecipeNo;
    int mModifyState = -1;

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

        mEditRecipeViewPagerAdapter = new EditRecipeViewPagerAdapter(this,this);
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
                        if (mEditRecipeRecyclerViewAdapter.getItemCount() <= 0) {
                            showCustomToast("레시피 과정을 추가해주세요");
                        }
                        else {
                            if(!mEditRecipeViewPagerAdapter.isContentValid()){
                                hideProgressDialog();
                                showCustomToast("누락된 입력값이 있습니다");
                            }else{
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
        if (getIntent().getIntExtra("isModify", 0) == 1) {
            mMainUri = getIntent().getStringExtra("mMainUri");
            mStringUri = getIntent().getStringArrayListExtra("mStringUri");
            mCategory = getIntent().getIntExtra("category", -1);
            mTitle = getIntent().getStringExtra("title");
            mFoodName = getIntent().getStringExtra("foodName");
            mHashTag = getIntent().getStringExtra("hashTag");
            mMaterial = getIntent().getStringExtra("material");
            mRecipeNo = getIntent().getIntExtra("recipeNo", -1);
            mNewMainImage = getIntent().getIntExtra("isNewMainImage", -1);
            HashMap<String,String> hashMap = (HashMap<String, String>)getIntent().getSerializableExtra("hashMap");
            for (int i = 0; i < mStringUri.size(); i++) {
                mUri.add(Uri.parse(mStringUri.get(i)));
                EditRecipeViewpagerItem editRecipeViewpagerItem = new EditRecipeViewpagerItem();
                editRecipeViewpagerItem.setmContent(hashMap.get(mStringUri.get(i)));
                editRecipeViewpagerItem.setmNumber(mUri.size());
                editRecipeViewpagerItem.setmUri(mUri.get(i));
                mEditRecipeViewPagerAdapter.addItem(editRecipeViewpagerItem);
            }
            for (int i = 0; i < getIntent().getIntegerArrayListExtra("isUri").size(); i++) {
                if (getIntent().getIntegerArrayListExtra("isUri").get(i) == 0) {
                    mIsUri.add(false);
                }
                else {
                    mIsUri.add(true);
                }
            }
            mEditRecipeViewPagerAdapter.notifyDataSetChanged();
            mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
        }
        else {
            mMainUri = getIntent().getStringExtra("mMainUri");
            mStringUri = getIntent().getStringArrayListExtra("mStringUri");
            mCategory = getIntent().getIntExtra("category", -1);
            mTitle = getIntent().getStringExtra("title");
            mFoodName = getIntent().getStringExtra("foodName");
            mHashTag = getIntent().getStringExtra("hashTag");
            mMaterial = getIntent().getStringExtra("material");
            mNewMainImage = 1;
            for (int i = 0; i < mStringUri.size(); i++) {
                mUri.add(Uri.parse(mStringUri.get(i)));
                mIsUri.add(true);
                EditRecipeViewpagerItem editRecipeViewpagerItem = new EditRecipeViewpagerItem();
                editRecipeViewpagerItem.setmContent("");
                editRecipeViewpagerItem.setmNumber(mUri.size());
                editRecipeViewpagerItem.setmUri(mUri.get(i));
                mEditRecipeViewPagerAdapter.addItem(editRecipeViewpagerItem);
            }
            mEditRecipeViewPagerAdapter.notifyDataSetChanged();
            mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
        }
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
        boolean recipeImg = false;
        for (int i = 0; i < mIsUri.size(); i++) {
            if (mIsUri.get(i) == true) {
                recipeImg = true;
                break;
            }
        }
        if (mNewMainImage == 1 && recipeImg) {  // state = 1
            mModifyState = 1;
            File mainFile = null;
            try {
                mainFile = new Compressor(this).setQuality(70).compressToFile(new File(Uri.parse(mMainUri).getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody mainRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mainFile);
            MultipartBody.Part mainMultiPart = MultipartBody.Part.createFormData("image", mainFile.getName(), mainRequestBody);

            ArrayList<MultipartBody.Part> files = new ArrayList<>();
            for (int i = 0; i < mUri.size(); i++) {
                if (mIsUri.get(i) == false) continue;
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
            editRecipeService.uploadImage(mainMultiPart, files);
        }
        else if (mNewMainImage == 1 && !recipeImg) { // state = 2
            mModifyState = 2;
            File mainFile = null;
            try {
                mainFile = new Compressor(this).setQuality(70).compressToFile(new File(Uri.parse(mMainUri).getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody mainRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mainFile);
            MultipartBody.Part mainMultiPart = MultipartBody.Part.createFormData("image", mainFile.getName(), mainRequestBody);

            EditRecipeService editRecipeService = new EditRecipeService(this);
            editRecipeService.uploadImage(mainMultiPart, null);
        }
        else if (mNewMainImage == 0 && recipeImg) { // state = 3
            mModifyState = 3;
            ArrayList<MultipartBody.Part> files = new ArrayList<>();
            for (int i = 0; i < mUri.size(); i++) {
                if (mIsUri.get(i) == false) continue;
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
            editRecipeService.uploadImage(null, files);
        }
        else { // state = 4
            mModifyState = 4;
            ResponseUpload.Result result = new ResponseUpload.Result();
            modifyRecipe(result);
        }
    }

    @Override
    public void uploadSuccess(boolean isSuccess, int code, String message, ResponseUpload.Result result) {
        if (isSuccess && code == 200) {
            if (getIntent().getIntExtra("isModify", 0) == 1) {
                modifyRecipe(result);
            }
            else {
                postRecipe(result);
            }
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
        Log.e("deleteImg","deleteIdx : " + deleteIdx);
        mEditRecipeViewPagerAdapter.removeItem(deleteIdx);
        mUri.remove(deleteIdx);
        mIsUri.remove(deleteIdx);
        for (int i = deleteIdx; i < mEditRecipeViewPagerAdapter.getCount(); i++) {
            EditRecipeViewpagerItem editRecipeViewpagerItem = mEditRecipeViewPagerAdapter.getEditRecipeViewpagerItemByIdx(i);
            editRecipeViewpagerItem.setmNumber(i+1);
        }
        mEditRecipeRecyclerViewAdapter.notifyDataSetChanged();
        mEditRecipeViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void modifyRecipeSuccess(boolean isSuccess, int code, String message) {
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
    public void modifyRecipeFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
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
                    mIsUri.add(true);
                    EditRecipeViewpagerItem editRecipeViewpagerItem = new EditRecipeViewpagerItem();
                    editRecipeViewpagerItem.setmContent("");
                    editRecipeViewpagerItem.setmNumber(mUri.size());
                    editRecipeViewpagerItem.setmUri(mUri.get(mUri.size() - 1));
                    mEditRecipeViewPagerAdapter.addItem(editRecipeViewpagerItem);
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
        requestPostRecipe.setServing("1인분");
        requestPostRecipe.setImage(result.getImage().getImageUrl());
        ArrayList<RequestPostRecipe.CookingOrder> cookingOrders = new ArrayList<>();

        for (int i = 0; i < mEditRecipeViewPagerAdapter.getCount(); i++) {
            RequestPostRecipe.CookingOrder cookingOrder = new RequestPostRecipe.CookingOrder();
            EditRecipeViewpagerItem editRecipeViewpagerItem = mEditRecipeViewPagerAdapter.getEditRecipeViewpagerItemByIdx(i);
            cookingOrder.setContent(editRecipeViewpagerItem.getmContent());
            cookingOrder.setCookingOrder(i + 1);
            cookingOrder.setCookingOrderImage(result.getCookingOrderImage().get(i).getImageUrl());
            cookingOrders.add(cookingOrder);
        }
        requestPostRecipe.setCookingOrder(cookingOrders);
        editRecipeService.postRecipe(requestPostRecipe);
    }

    public void modifyRecipe(ResponseUpload.Result result) {
        EditRecipeService editRecipeService = new EditRecipeService(this);
        RequestModifyRecipe requestModifyRecipe = new RequestModifyRecipe();
        requestModifyRecipe.setTitle(mTitle);
        requestModifyRecipe.setCategoryNo(mCategory);
        requestModifyRecipe.setName(mFoodName);
        requestModifyRecipe.setHashTag(mHashTag);
        requestModifyRecipe.setIngredient(mMaterial);
        requestModifyRecipe.setServing("1인분");
        if (mModifyState == 1) {
            requestModifyRecipe.setImage(result.getImage().getImageUrl());
        }
        else if (mModifyState == 2) {
            requestModifyRecipe.setImage(result.getImage().getImageUrl());
        }
        else if (mModifyState == 3) {
            requestModifyRecipe.setImage(mMainUri);
        }
        else {
            requestModifyRecipe.setImage(mMainUri);
        }
        ArrayList<RequestModifyRecipe.CookingOrder> cookingOrders = new ArrayList<>();
        int uriCnt = 0;
        for (int i = 0; i < mIsUri.size(); i++) {
            RequestModifyRecipe.CookingOrder cookingOrder = new RequestModifyRecipe.CookingOrder();
            cookingOrder.setContent(mEditRecipeViewPagerAdapter.getEditRecipeViewpagerItemByIdx(i).getmContent());
            cookingOrder.setCookingOrder(i + 1);
            if (mIsUri.get(i)) {
                cookingOrder.setCookingOrderImage(result.getCookingOrderImage().get(uriCnt).getImageUrl());
                uriCnt++;
            }
            else {
                cookingOrder.setCookingOrderImage(mStringUri.get(i));
            }
            cookingOrders.add(cookingOrder);
        }
        requestModifyRecipe.setCookingOrder(cookingOrders);

        editRecipeService.modifyRecipe(mRecipeNo, requestModifyRecipe);
    }
}
