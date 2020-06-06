package com.makers.lemoncook.src.modifyRecipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.VerticalTextView;
import com.makers.lemoncook.src.addRecipe.adapters.NewRecipeImageRecyclerViewAdapter;
import com.makers.lemoncook.src.editRecipe.EditRecipeActivity;
import com.makers.lemoncook.src.modifyRecipe.adapters.ModifyRecyclerViewAdapter;
import com.makers.lemoncook.src.modifyRecipe.interfaces.ModifyActivityView;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;
import com.nex3z.flowlayout.FlowLayout;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ModifyActivity extends BaseActivity implements ModifyActivityView {

    LinearLayout mLlDynamicArea;
    ConstraintLayout mClDynamicPlusBtn, mClPlusRecipeImg, mClMainImage;
    VerticalTextView mTvIngredientTitle;
    RecyclerView mRvImage;
    ModifyRecyclerViewAdapter mModifyRecyclerViewAdapter;
    Button mBtnStart, mBtnAddHashTag, mBtnCategory1, mBtnCategory2, mBtnCategory3, mBtnCategory4, mBtnCategory5, mBtnCategory6, mBtnGetMaterial;
    TextView mTvMainImage;
    ImageView mIvMainPlusImage, mIvMainImage, mIvBackBtn;
    String mMainUri = "";
    EditText mEtHashTag, mEtTitle, mEtFoodName;
    FlowLayout mFlowLayout, mFlowLayoutMaterial;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    ArrayList<Integer> mRootLayoutID = new ArrayList<>();
    ArrayList<Integer> mFirstEtID = new ArrayList<>();
    ArrayList<Integer> mDeleteBtnID = new ArrayList<>();
    ArrayList<Uri> mUri = new ArrayList<>();
    ArrayList<Integer> mIsUri = new ArrayList<>();
    ArrayList<String> mStringUri = new ArrayList<>();
    ArrayList<Integer> mHashTagId = new ArrayList<>();
    final static int PICKER_REQUEST_CODE = 30;
    final static int PICKER_MAIN_REQUEST_CODE = 31;
    int mCategory = -1;
    int mNewMainImage = 0;
    ArrayList<Integer> mMaterial = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        mClMainImage = findViewById(R.id.modify_cl_main_image);
        mRvImage = findViewById(R.id.modify_rv_recipe_image);
        mClPlusRecipeImg = findViewById(R.id.modify_cl_plus_recipe_image);
        mTvIngredientTitle = findViewById(R.id.modify_vt_ingredient);
        mLlDynamicArea = findViewById(R.id.modify_ll_dynamic_area);
        mClDynamicPlusBtn = findViewById(R.id.modify_cl_dynamic_plus_btn);
        mBtnStart = findViewById(R.id.modify_btn_start);
        mTvMainImage = findViewById(R.id.modify_tv_plus_main_image);
        mIvMainPlusImage = findViewById(R.id.modify_iv_plus_main_image);
        mIvMainImage = findViewById(R.id.modify_iv_main_img);
        mBtnAddHashTag = findViewById(R.id.modify_btn_add_hash_tag);
        mEtHashTag = findViewById(R.id.modify_et_hash_tag);
        mFlowLayout = findViewById(R.id.modify_flowLayout);
        mBtnCategory1 = findViewById(R.id.modify_btn_category1);
        mBtnCategory2 = findViewById(R.id.modify_btn_category2);
        mBtnCategory3 = findViewById(R.id.modify_btn_category3);
        mBtnCategory4 = findViewById(R.id.modify_btn_category4);
        mBtnCategory5 = findViewById(R.id.modify_btn_category5);
        mBtnCategory6 = findViewById(R.id.modify_btn_category6);
        mEtTitle = findViewById(R.id.modify_et_little_title);
        mEtFoodName = findViewById(R.id.modify_et_food_name);
        mIvBackBtn = findViewById(R.id.modify_ic_back);
        mBtnGetMaterial = findViewById(R.id.modify_btn_get_material);
        mFlowLayoutMaterial = findViewById(R.id.modify_flowLayout_material);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRvImage.setLayoutManager(layoutManager);
        mModifyRecyclerViewAdapter = new ModifyRecyclerViewAdapter(mUri, this, this);
        mRvImage.setAdapter(mModifyRecyclerViewAdapter);

        init();

        mClPlusRecipeImg.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mUri.size() < 15) {
                    new GligarPicker().limit(15 - mUri.size()).disableCamera(false).requestCode(PICKER_REQUEST_CODE).withActivity(ModifyActivity.this).show();
                }
                else {
                    showCustomToast("최대 15장만 불러올 수 있습니다");
                }
            }
        });

        mIvBackBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mClMainImage.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                new GligarPicker().limit(1).disableCamera(false).requestCode(PICKER_MAIN_REQUEST_CODE).withActivity(ModifyActivity.this).show();
            }
        });

        mClDynamicPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView("");
            }
        });

        mBtnGetMaterial.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mEtFoodName.getText().toString().equals("")) {
                    showCustomToast("음식명을 입력해주세요");
                }
                else {
                    getMaterial();
                }
            }
        });

        mBtnStart.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mCategory == -1) {
                    showCustomToast("카테고리를 선택해주세요");
                }
                else {
                    if (mEtTitle.getText().toString().equals("")) {
                        showCustomToast("소제목을 입력해주세요");
                    }
                    else {
                        if (mEtFoodName.getText().toString().equals("")) {
                            showCustomToast("음식명을 입력해주세요");
                        }
                        else {
                            if (mHashTagId.size() == 0) {
                                showCustomToast("해시태그를 추가해주세요");
                            }
                            else {
                                if (mMainUri.equals("")) {
                                    showCustomToast("메인사진을 선택해주세요");
                                }
                                else {
                                    boolean boolComma = true;
                                    boolean boolExist = false;
                                    for (int i = 0; i < mFirstEtID.size(); i++) {
                                        EditText editText = findViewById(Integer.valueOf(mFirstEtID.get(i)));
                                        String str = editText.getText().toString();
                                        if (!str.equals("")) {
                                            boolExist = true;
                                            for (int j = 0; j < str.length(); j++) {
                                                if (str.charAt(j) == ',') {
                                                    boolComma = false;
                                                    showCustomToast("재료에 ','는 입력할 수 없습니다");
                                                    break;
                                                }
                                            }
                                        }
                                        if (boolComma == false) break;
                                    }
                                    if (boolComma == true && boolExist == true) {
                                        String hashTag = "";
                                        String material = "";
                                        boolean hashFirst = true;
                                        boolean mateFirst = true;
                                        for (int i = 0; i < mHashTagId.size(); i++) {
                                            TextView textView = findViewById(mHashTagId.get(i));
                                            if (hashFirst) {
                                                hashTag = hashTag + textView.getText().toString();
                                                hashFirst = false;
                                            }
                                            else {
                                                hashTag = hashTag + " " + textView.getText().toString();
                                            }
                                        }
                                        for (int i = 0; i < mFirstEtID.size(); i++) {
                                            EditText editText = findViewById(mFirstEtID.get(i));
                                            if (!editText.getText().toString().equals("")) {
                                                if (mateFirst) {
                                                    material = material + editText.getText().toString();
                                                    mateFirst = false;
                                                }
                                                else {
                                                    material = material + ", " + editText.getText().toString();
                                                }
                                            }
                                        }
                                        Intent intent = new Intent(ModifyActivity.this, EditRecipeActivity.class);

                                        intent.putExtra("category", mCategory);
                                        intent.putExtra("title", mEtTitle.getText().toString());
                                        intent.putExtra("foodName", mEtFoodName.getText().toString());
                                        intent.putExtra("hashTag", hashTag);
                                        intent.putExtra("material", material);
                                        intent.putExtra("mStringUri", mStringUri);
                                        intent.putExtra("mMainUri", mMainUri);
                                        HashMap<String,String> hashMap = (HashMap<String, String>)getIntent().getSerializableExtra("cookingOrder");
                                        System.out.println(hashMap.size());
                                        intent.putExtra("hashMap", hashMap);
                                        intent.putExtra("isModify", 1);
                                        intent.putExtra("recipeNo", getIntent().getIntExtra("recipeNo", -1));
                                        intent.putExtra("isUri", mIsUri);
                                        intent.putExtra("isNewMainImage", mNewMainImage);
                                        startActivity(intent);
                                    }
                                    else if (boolExist == false) {
                                        showCustomToast("재료를 입력해주세요");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        mBtnAddHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHashTagId.size() == 6) {
                    showCustomToast("해시태그를 6개 이상 입력할 수 없습니다");
                }
                else {
                    if (mEtHashTag.getText().toString().equals("")) {
                        showCustomToast("해시태그를 입력해주세요");
                    }
                    else {
                        if (mEtHashTag.getText().toString().charAt(0) != '#') {
                            showCustomToast("해시태그는 #로 시작해야합니다");
                        }
                        else {
                            if (mEtHashTag.getText().toString().length() == 1) {
                                showCustomToast("해시태그를 입력해주세요");
                            }
                            else {
                                boolean b = true;
                                for (int i = 1; i < mEtHashTag.getText().toString().length(); i++) {
                                    if (mEtHashTag.getText().toString().charAt(i) == '#' || mEtHashTag.getText().toString().charAt(i) == ' ') {
                                        showCustomToast("잘못된 해시태그 형식입니다");
                                        b = false;
                                        break;
                                    }
                                }
                                if (b) {
                                    final TextView textView = new TextView(ModifyActivity.this);
                                    textView.setId(generateViewId());
                                    textView.setText(mEtHashTag.getText().toString());
                                    textView.setBackgroundResource(R.drawable.radius_8dp_lemon);
                                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
                                    textView.setTextSize(11);
                                    LinearLayout.LayoutParams paramsHashTag = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT
                                    );
                                    textView.setLayoutParams(paramsHashTag);
                                    DisplayMetrics dm = getResources().getDisplayMetrics();
                                    textView.setPadding(Math.round(9*dm.density), Math.round(5*dm.density), Math.round(9*dm.density), Math.round(5*dm.density));
                                    mHashTagId.add(textView.getId());
                                    textView.setOnClickListener(new OnSingleClickListener() {
                                        @Override
                                        public void onSingleClick(View v) {
                                            mHashTagId.remove(Integer.valueOf(textView.getId()));
                                            mFlowLayout.removeView(textView);
                                        }
                                    });
                                    mFlowLayout.addView(textView);
                                    mEtHashTag.setText("");
                                }
                            }
                        }
                    }
                }
            }
        });

        mBtnCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnCategory1.setBackgroundResource(R.drawable.round_category_empty);
                mBtnCategory2.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory3.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory4.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory5.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory6.setBackgroundResource(R.drawable.round_category_fill);

                mBtnCategory1.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtnCategory2.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory3.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory4.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory5.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory6.setTextColor(getResources().getColor(R.color.colorLemon));

                mCategory = 1;
            }
        });
        mBtnCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnCategory1.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory2.setBackgroundResource(R.drawable.round_category_empty);
                mBtnCategory3.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory4.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory5.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory6.setBackgroundResource(R.drawable.round_category_fill);

                mBtnCategory1.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory2.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtnCategory3.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory4.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory5.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory6.setTextColor(getResources().getColor(R.color.colorLemon));

                mCategory = 2;
            }
        });
        mBtnCategory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnCategory1.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory2.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory3.setBackgroundResource(R.drawable.round_category_empty);
                mBtnCategory4.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory5.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory6.setBackgroundResource(R.drawable.round_category_fill);

                mBtnCategory1.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory2.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory3.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtnCategory4.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory5.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory6.setTextColor(getResources().getColor(R.color.colorLemon));

                mCategory = 3;
            }
        });
        mBtnCategory4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnCategory1.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory2.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory3.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory4.setBackgroundResource(R.drawable.round_category_empty);
                mBtnCategory5.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory6.setBackgroundResource(R.drawable.round_category_fill);

                mBtnCategory1.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory2.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory3.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory4.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtnCategory5.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory6.setTextColor(getResources().getColor(R.color.colorLemon));

                mCategory = 4;
            }
        });
        mBtnCategory5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnCategory1.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory2.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory3.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory4.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory5.setBackgroundResource(R.drawable.round_category_empty);
                mBtnCategory6.setBackgroundResource(R.drawable.round_category_fill);

                mBtnCategory1.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory2.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory3.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory4.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory5.setTextColor(getResources().getColor(R.color.colorWhite));
                mBtnCategory6.setTextColor(getResources().getColor(R.color.colorLemon));

                mCategory = 5;
            }
        });
        mBtnCategory6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnCategory1.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory2.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory3.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory4.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory5.setBackgroundResource(R.drawable.round_category_fill);
                mBtnCategory6.setBackgroundResource(R.drawable.round_category_empty);

                mBtnCategory1.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory2.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory3.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory4.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory5.setTextColor(getResources().getColor(R.color.colorLemon));
                mBtnCategory6.setTextColor(getResources().getColor(R.color.colorWhite));

                mCategory = 6;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode){
            case PICKER_REQUEST_CODE : {
                String pathsList[]= data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.
                for (int i = pathsList.length - 1; i >= 0; i--) {
                    mUri.add(Uri.parse(pathsList[i]));
                    mStringUri.add(pathsList[i]);
                    mIsUri.add(1);
                }
                mModifyRecyclerViewAdapter.notifyDataSetChanged();
                break;
            }
            case PICKER_MAIN_REQUEST_CODE : {
                String pathsList[]= data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
                mTvMainImage.setVisibility(View.GONE);
                mIvMainPlusImage.setVisibility(View.GONE);
                mIvMainImage.setVisibility(View.VISIBLE);
                mMainUri = pathsList[0];
                mNewMainImage = 1;
                Glide.with(this).load(new File(Uri.parse(pathsList[0]).getPath())).into(mIvMainImage);
                break;
            }
        }
    }

    void addView(String content) {
        DisplayMetrics dm = getResources().getDisplayMetrics();

        final LinearLayout rootLinearLayout = new LinearLayout(this);
        rootLinearLayout.setId(generateViewId());
        rootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsRoot = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                Math.round(35*dm.density)
        );
        paramsRoot.topMargin = Math.round(4*dm.density);

        final EditText firstEt = new EditText(this);
        firstEt.setId(generateViewId());
        firstEt.setBackground(getResources().getDrawable(R.drawable.radius_4dp_gray));
        firstEt.setHint(getResources().getString(R.string.newRecipeMaterialHint));
        firstEt.setText(content);
        firstEt.setHintTextColor(getResources().getColor(R.color.colorLoginGray));
        firstEt.setTextColor(getResources().getColor(R.color.colorLoginBlack));
        firstEt.setTextSize(12);
        firstEt.setInputType(InputType.TYPE_CLASS_TEXT);
        firstEt.setPadding(Math.round(5*dm.density), 0, Math.round(5*dm.density), 0);
        LinearLayout.LayoutParams paramsEt1 = new LinearLayout.LayoutParams(
                Math.round(0*dm.density),
                Math.round(35*dm.density),
                1.0f
        );

        final ImageView deleteBtn = new ImageView(this);
        deleteBtn.setImageResource(R.drawable.ic_x);
        LinearLayout.LayoutParams paramsIv = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsIv.leftMargin = Math.round(8*dm.density);

        rootLinearLayout.addView(firstEt, paramsEt1);
        rootLinearLayout.addView(deleteBtn, paramsIv);

        mLlDynamicArea.addView(rootLinearLayout, paramsRoot);

        mRootLayoutID.add(rootLinearLayout.getId());
        mFirstEtID.add(firstEt.getId());
        mDeleteBtnID.add(deleteBtn.getId());

        deleteBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mRootLayoutID.remove(Integer.valueOf(rootLinearLayout.getId()));
                mFirstEtID.remove(Integer.valueOf(firstEt.getId()));
                mDeleteBtnID.remove(Integer.valueOf(deleteBtn.getId()));
                rootLinearLayout.removeView(firstEt);
                rootLinearLayout.removeView(deleteBtn);
                mLlDynamicArea.removeView(rootLinearLayout);
            }
        });

        // mLayoutID mFirstEtID mSecondEtID mDeleteBtnID
    }

    private static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1;
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    @Override
    public void removeImage(int idx) {
        mUri.remove(idx);
        mStringUri.remove(idx);
        mIsUri.remove(idx);
        mModifyRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMaterialSuccess(boolean isSuccess, int code, String message, String result) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            if (result == null) {
                showCustomToast("추천재료가 없습니다");
            }
            else {
                String material[] = result.split(",");
                for (int i = 0; i < mMaterial.size(); i++) {
                    TextView textView = findViewById(Integer.valueOf(mMaterial.get(i)));
                    mFlowLayoutMaterial.removeView(textView);
                }
                mMaterial.clear();
                for (int i = 0; i < material.length; i++) {
                    final TextView textView = new TextView(ModifyActivity.this);
                    textView.setId(generateViewId());
                    textView.setText(material[i]);
                    textView.setBackgroundResource(R.drawable.radius_8dp_lemon);
                    textView.setTextColor(getResources().getColor(R.color.colorWhite));
                    textView.setTextSize(11);
                    LinearLayout.LayoutParams paramsHashTag = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    textView.setLayoutParams(paramsHashTag);
                    DisplayMetrics dm = getResources().getDisplayMetrics();
                    textView.setPadding(Math.round(9*dm.density), Math.round(5*dm.density), Math.round(9*dm.density), Math.round(5*dm.density));
                    mMaterial.add(textView.getId());
                    textView.setOnClickListener(new OnSingleClickListener() {
                        @Override
                        public void onSingleClick(View v) {
                            addView(textView.getText().toString());
                            mMaterial.remove(Integer.valueOf(textView.getId()));
                            mFlowLayoutMaterial.removeView(textView);
                        }
                    });
                    mFlowLayoutMaterial.addView(textView);
                }
            }
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void getMaterialFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    public void init() {
        mCategory = getIntent().getIntExtra("category", -1);
        if (mCategory == 1) {
            mBtnCategory1.setBackgroundResource(R.drawable.round_category_empty);
            mBtnCategory1.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if (mCategory == 2) {
            mBtnCategory2.setBackgroundResource(R.drawable.round_category_empty);
            mBtnCategory2.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if (mCategory == 3) {
            mBtnCategory3.setBackgroundResource(R.drawable.round_category_empty);
            mBtnCategory3.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if (mCategory == 4) {
            mBtnCategory4.setBackgroundResource(R.drawable.round_category_empty);
            mBtnCategory4.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if (mCategory == 5) {
            mBtnCategory5.setBackgroundResource(R.drawable.round_category_empty);
            mBtnCategory5.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else if (mCategory == 6) {
            mBtnCategory6.setBackgroundResource(R.drawable.round_category_empty);
            mBtnCategory6.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        mEtTitle.setText(getIntent().getStringExtra("title"));
        mEtFoodName.setText(getIntent().getStringExtra("name"));

        String hashTagArr[] = getIntent().getStringExtra("hashTag").split(" ");
        for (int i = 0; i < hashTagArr.length; i++) {
            final TextView textView = new TextView(ModifyActivity.this);
            textView.setId(generateViewId());
            textView.setText(hashTagArr[i]);
            textView.setBackgroundResource(R.drawable.radius_8dp_lemon);
            textView.setTextColor(getResources().getColor(R.color.colorWhite));
            textView.setTextSize(11);
            LinearLayout.LayoutParams paramsHashTag = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(paramsHashTag);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            textView.setPadding(Math.round(9*dm.density), Math.round(5*dm.density), Math.round(9*dm.density), Math.round(5*dm.density));
            mHashTagId.add(textView.getId());
            textView.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    mHashTagId.remove(Integer.valueOf(textView.getId()));
                    mFlowLayout.removeView(textView);
                }
            });
            mFlowLayout.addView(textView);
            mEtHashTag.setText("");
        }

        mTvMainImage.setVisibility(View.GONE);
        mIvMainPlusImage.setVisibility(View.GONE);
        mIvMainImage.setVisibility(View.VISIBLE);
        mMainUri = getIntent().getStringExtra("mainImg");
        Glide.with(this).load(mMainUri).into(mIvMainImage);

        for (int i = 0; i < getIntent().getStringArrayListExtra("material").size(); i++) {
            DisplayMetrics dm = getResources().getDisplayMetrics();

            final LinearLayout rootLinearLayout = new LinearLayout(this);
            rootLinearLayout.setId(generateViewId());
            rootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams paramsRoot = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    Math.round(35*dm.density)
            );
            paramsRoot.topMargin = Math.round(4*dm.density);

            final EditText firstEt = new EditText(this);
            firstEt.setId(generateViewId());
            firstEt.setBackground(getResources().getDrawable(R.drawable.radius_4dp_gray));
            firstEt.setHint(getResources().getString(R.string.newRecipeMaterialHint));
            firstEt.setHintTextColor(getResources().getColor(R.color.colorLoginGray));
            firstEt.setTextColor(getResources().getColor(R.color.colorLoginBlack));
            firstEt.setTextSize(12);
            ArrayList<ResponseRecipe.Result.Ingredient> intentIngredient = (ArrayList<ResponseRecipe.Result.Ingredient>)getIntent().getSerializableExtra("material");
            firstEt.setText(intentIngredient.get(i).getIngredient());
            firstEt.setInputType(InputType.TYPE_CLASS_TEXT);
            firstEt.setPadding(Math.round(5*dm.density), 0, Math.round(5*dm.density), 0);
            LinearLayout.LayoutParams paramsEt1 = new LinearLayout.LayoutParams(
                    Math.round(0*dm.density),
                    Math.round(35*dm.density),
                    1.0f
            );

            final ImageView deleteBtn = new ImageView(this);
            deleteBtn.setImageResource(R.drawable.ic_x);
            LinearLayout.LayoutParams paramsIv = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            paramsIv.leftMargin = Math.round(8*dm.density);

            rootLinearLayout.addView(firstEt, paramsEt1);
            rootLinearLayout.addView(deleteBtn, paramsIv);

            mLlDynamicArea.addView(rootLinearLayout, paramsRoot);

            mRootLayoutID.add(rootLinearLayout.getId());
            mFirstEtID.add(firstEt.getId());
            mDeleteBtnID.add(deleteBtn.getId());

            deleteBtn.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    mRootLayoutID.remove(Integer.valueOf(rootLinearLayout.getId()));
                    mFirstEtID.remove(Integer.valueOf(firstEt.getId()));
                    mDeleteBtnID.remove(Integer.valueOf(deleteBtn.getId()));
                    rootLinearLayout.removeView(firstEt);
                    rootLinearLayout.removeView(deleteBtn);
                    mLlDynamicArea.removeView(rootLinearLayout);
                }
            });
        }
        for (int i = 0; i < getIntent().getStringArrayListExtra("img").size(); i++) {
            mUri.add(Uri.parse(getIntent().getStringArrayListExtra("img").get(i)));
            mStringUri.add(getIntent().getStringArrayListExtra("img").get(i));
            mIsUri.add(0);
        }
        mModifyRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void getMaterial() {
        showProgressDialog();
        ModifyService modifyService = new ModifyService(this);
        modifyService.getMaterial(mEtFoodName.getText().toString());
    }
}
