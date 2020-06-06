package com.makers.lemoncook.src.addRecipe;

import androidx.annotation.Nullable;
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
import com.makers.lemoncook.src.addRecipe.interfaces.AddRecipeActivityView;
import com.makers.lemoncook.src.editRecipe.EditRecipeActivity;
import com.makers.lemoncook.src.loadRecipe.LoadRecipeActivity;
import com.makers.lemoncook.src.recipe.models.ResponseRecipe;
import com.makers.lemoncook.src.search.models.ResponseSearch;
import com.nex3z.flowlayout.FlowLayout;
import com.opensooq.supernova.gligar.GligarPicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AddRecipeActivity extends BaseActivity implements AddRecipeActivityView {

    TextView mTvNewRecipe, mTvLoadRecipe;
    LinearLayout mLlExpandableContent, mLlDynamicArea;
    ConstraintLayout mClExpandableBtn, mClDynamicPlusBtn, mClPlusRecipeImg, mClMainImage;
    boolean expandable = true;
    VerticalTextView mTvIngredientTitle;
    RecyclerView mRvImage;
    NewRecipeImageRecyclerViewAdapter mNewRecipeImageRecyclerViewAdapter;
    Button mBtnStart, mBtnAddHashTag, mBtnCategory1, mBtnCategory2, mBtnCategory3, mBtnCategory4, mBtnCategory5, mBtnCategory6;
    TextView mTvMainImage;
    ImageView mIvMainPlusImage, mIvMainImage, mIvBackBtn;
    String mMainUri = "";
    EditText mEtHashTag, mEtTitle, mEtFoodName;
    FlowLayout mFlowLayout;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    ArrayList<Integer> mRootLayoutID = new ArrayList<>();
    ArrayList<Integer> mFirstEtID = new ArrayList<>();
    ArrayList<Integer> mDeleteBtnID = new ArrayList<>();
    ArrayList<Uri> mUri = new ArrayList<>();
    ArrayList<String> mStringUri = new ArrayList<>();
    ArrayList<Integer> mHashTagId = new ArrayList<>();
    final static int PICKER_REQUEST_CODE = 30;
    final static int PICKER_MAIN_REQUEST_CODE = 31;
    private static final int LOAD_RECIPE = 32;
    int mCategory = -1;
    boolean mLoad = false;
    HashMap<String,String> mHashMap = new HashMap<>();
    ArrayList<Integer> mIsUri = new ArrayList<>();
    int mNewMainImage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        mTvNewRecipe = findViewById(R.id.add_recipe_tv_new);
        mTvLoadRecipe = findViewById(R.id.add_recipe_tv_load);
        mClMainImage = findViewById(R.id.new_recipe_cl_main_image);
        mRvImage = findViewById(R.id.new_recipe_rv_recipe_image);
        mClPlusRecipeImg = findViewById(R.id.new_recipe_cl_plus_recipe_image);
        mLlExpandableContent = findViewById(R.id.new_recipe_ll_expandable);
        mClExpandableBtn = findViewById(R.id.new_recipe_cl_expandable_btn);
        mTvIngredientTitle = findViewById(R.id.new_recipe_vt_ingredient);
        mLlDynamicArea = findViewById(R.id.new_recipe_ll_dynamic_area);
        mClDynamicPlusBtn = findViewById(R.id.new_recipe_cl_dynamic_plus_btn);
        mBtnStart = findViewById(R.id.new_recipe_btn_start);
        mTvMainImage = findViewById(R.id.new_recipe_tv_plus_main_image);
        mIvMainPlusImage = findViewById(R.id.new_recipe_iv_plus_main_image);
        mIvMainImage = findViewById(R.id.new_recipe_iv_main_img);
        mBtnAddHashTag = findViewById(R.id.new_recipe_btn_add_hash_tag);
        mEtHashTag = findViewById(R.id.new_recipe_et_hash_tag);
        mFlowLayout = findViewById(R.id.new_recipe_flowLayout);
        mBtnCategory1 = findViewById(R.id.new_recipe_btn_category1);
        mBtnCategory2 = findViewById(R.id.new_recipe_btn_category2);
        mBtnCategory3 = findViewById(R.id.new_recipe_btn_category3);
        mBtnCategory4 = findViewById(R.id.new_recipe_btn_category4);
        mBtnCategory5 = findViewById(R.id.new_recipe_btn_category5);
        mBtnCategory6 = findViewById(R.id.new_recipe_btn_category6);
        mEtTitle = findViewById(R.id.new_recipe_et_little_title);
        mEtFoodName = findViewById(R.id.new_recipe_et_food_name);
        mIvBackBtn = findViewById(R.id.add_recipe_ic_back);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRvImage.setLayoutManager(layoutManager);
        mNewRecipeImageRecyclerViewAdapter = new NewRecipeImageRecyclerViewAdapter(mUri, this, this);
        mRvImage.setAdapter(mNewRecipeImageRecyclerViewAdapter);

        mClPlusRecipeImg.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mUri.size() < 15) {
                    new GligarPicker().limit(15 - mUri.size()).disableCamera(false).requestCode(PICKER_REQUEST_CODE).withActivity(AddRecipeActivity.this).show();
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

        mClExpandableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandable){
                    expandable = false;
                    mClExpandableBtn.setBackground(getResources().getDrawable(R.drawable.round_recommend_tap_gray));
                    mTvIngredientTitle.setVisibility(View.GONE);
                    collapse(mLlExpandableContent);
                }
                else{
                    expandable = true;
                    mClExpandableBtn.setBackground(getResources().getDrawable(R.drawable.round_recommend_tap));
                    mTvIngredientTitle.setVisibility(View.VISIBLE);
                    expand(mLlExpandableContent);
                }
            }
        });

        mClMainImage.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                new GligarPicker().limit(1).disableCamera(false).requestCode(PICKER_MAIN_REQUEST_CODE).withActivity(AddRecipeActivity.this).show();
            }
        });

        mClDynamicPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
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
                                        Intent intent = new Intent(AddRecipeActivity.this, EditRecipeActivity.class);
                                        intent.putExtra("category", mCategory);
                                        intent.putExtra("title", mEtTitle.getText().toString());
                                        intent.putExtra("foodName", mEtFoodName.getText().toString());
                                        intent.putExtra("hashTag", hashTag);
                                        System.out.println("인텐트 전" + material);
                                        intent.putExtra("material", material);
                                        intent.putExtra("mStringUri", mStringUri);
                                        intent.putExtra("mMainUri", mMainUri);
                                        if (mLoad) {
                                            intent.putExtra("isUri", mIsUri);
                                            intent.putExtra("isNewMainImage", mNewMainImage);
                                            intent.putExtra("hashMap", mHashMap);
                                            intent.putExtra("isModify", 2);
                                        }
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

        mTvLoadRecipe.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                hideKeyboard(v);
                Intent intent = new Intent(AddRecipeActivity.this, LoadRecipeActivity.class);
                startActivityForResult(intent, LOAD_RECIPE);
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
                                    final TextView textView = new TextView(AddRecipeActivity.this);
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
                mNewRecipeImageRecyclerViewAdapter.notifyDataSetChanged();
                break;
            }
            case PICKER_MAIN_REQUEST_CODE : {
                mNewMainImage = 1;
                String pathsList[]= data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT);
                mTvMainImage.setVisibility(View.GONE);
                mIvMainPlusImage.setVisibility(View.GONE);
                mIvMainImage.setVisibility(View.VISIBLE);
                mMainUri = pathsList[0];
                Glide.with(this).load(new File(Uri.parse(pathsList[0]).getPath())).into(mIvMainImage);
                break;
            }
            case LOAD_RECIPE: {
                int loadRecipeNo = data.getIntExtra("recipeNo", -1);
                AddRecipeService addRecipeService = new AddRecipeService(this);
                addRecipeService.getRecipe(loadRecipeNo);
            }
        }
    }

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    void addView() {
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
        mNewRecipeImageRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getRecipeSuccess(boolean isSuccess, int code, String message, ResponseRecipe.Result result) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            mLoad = true;
            mCategory = result.getCategoryNo();
            if (mCategory == 1) {
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
            }
            else if (mCategory == 2) {
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
            }
            else if (mCategory == 3) {
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
            }
            else if (mCategory == 4) {
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
            }
            else if (mCategory == 5) {
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
            }
            else {
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
            }

            mEtTitle.setText(result.getTitle());
            mEtFoodName.setText(result.getName());

            for (int i = 0; i < mHashTagId.size(); i++) {
                TextView textView = findViewById(mHashTagId.get(i));
                mFlowLayout.removeView(textView);
            }
            mHashTagId.clear();

            String hashTagArr[] = result.getHashTag().split(" ");
            for (int i = 0; i < hashTagArr.length; i++) {
                final TextView textView = new TextView(AddRecipeActivity.this);
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

            mNewMainImage = 0;
            mTvMainImage.setVisibility(View.GONE);
            mIvMainPlusImage.setVisibility(View.GONE);
            mIvMainImage.setVisibility(View.VISIBLE);
            mMainUri = result.getImage();
            Glide.with(this).load(mMainUri).into(mIvMainImage);

            for (int i = 0; i < result.getCookingOrder().size(); i++) {
                mUri.add(Uri.parse(result.getCookingOrder().get(i).getCookingOrderImage()));
                mStringUri.add(result.getCookingOrder().get(i).getCookingOrderImage());
                mIsUri.add(0);
                mHashMap.put(result.getCookingOrder().get(i).getCookingOrderImage(), result.getCookingOrder().get(i).getContent());
            }

            mNewRecipeImageRecyclerViewAdapter.notifyDataSetChanged();

            for (int i = 0; i < result.getIngredient().size(); i++) {
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
                firstEt.setText(result.getIngredient().get(i).getIngredient());
                firstEt.setHint(getResources().getString(R.string.newRecipeMaterialHint));
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
            }
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
}
