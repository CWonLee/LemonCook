package com.makers.lemoncook.src.addRecipe.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.VerticalTextView;
import com.makers.lemoncook.src.addRecipe.adapters.NewRecipeImageRecyclerViewAdapter;
import com.makers.lemoncook.src.addRecipe.fragments.interfaces.NewRecipeFragmentView;
import com.opensooq.supernova.gligar.GligarPicker;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class NewRecipeFragment extends Fragment implements NewRecipeFragmentView {

    LinearLayout mLlExpandableContent, mLlDynamicArea;
    ConstraintLayout mClExpandableBtn, mClDynamicPlusBtn, mClPlusRecipeImg;
    boolean expandable = true;
    VerticalTextView mTvIngredientTitle;
    RecyclerView mRvImage;
    NewRecipeImageRecyclerViewAdapter mNewRecipeImageRecyclerViewAdapter;

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    ArrayList<Integer> mRootLayoutID = new ArrayList<>();
    ArrayList<Integer> mLayoutID = new ArrayList<>();
    ArrayList<Integer> mFirstEtID = new ArrayList<>();
    ArrayList<Integer> mSecondEtID = new ArrayList<>();
    ArrayList<Integer> mDeleteBtnID = new ArrayList<>();
    ArrayList<Uri> mUri = new ArrayList<>();
    final static int PICKER_REQUEST_CODE = 30;

    public NewRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        mRvImage = view.findViewById(R.id.new_recipe_rv_recipe_image);
        mClPlusRecipeImg = view.findViewById(R.id.new_recipe_cl_plus_recipe_image);
        mLlExpandableContent = view.findViewById(R.id.new_recipe_ll_expandable);
        mClExpandableBtn = view.findViewById(R.id.new_recipe_cl_expandable_btn);
        mTvIngredientTitle = view.findViewById(R.id.new_recipe_vt_ingredient);
        mLlDynamicArea = view.findViewById(R.id.new_recipe_ll_dynamic_area);
        mClDynamicPlusBtn = view.findViewById(R.id.new_recipe_cl_dynamic_plus_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRvImage.setLayoutManager(layoutManager);
        mNewRecipeImageRecyclerViewAdapter = new NewRecipeImageRecyclerViewAdapter(mUri, this);
        mRvImage.setAdapter(mNewRecipeImageRecyclerViewAdapter);
        //mRvImage.setNestedScrollingEnabled(false);

        mClPlusRecipeImg.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                new GligarPicker().limit(12).disableCamera(false).requestCode(PICKER_REQUEST_CODE).withFragment(NewRecipeFragment.this).show();
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

        mClDynamicPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        switch (requestCode){
            case PICKER_REQUEST_CODE : {
                String pathsList[]= data.getExtras().getStringArray(GligarPicker.IMAGES_RESULT); // return list of selected images paths.
                for (int i = pathsList.length - 1; i >= 0; i--) {
                    mUri.add(Uri.parse(pathsList[i]));
                }
                mNewRecipeImageRecyclerViewAdapter.notifyDataSetChanged();
                break;
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

        final LinearLayout rootLinearLayout = new LinearLayout(getContext());
        rootLinearLayout.setId(generateViewId());
        rootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsRoot = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                Math.round(35*dm.density)
        );
        paramsRoot.topMargin = Math.round(4*dm.density);

        final LinearLayout dynamicLinearLayout = new LinearLayout(getContext());
        dynamicLinearLayout.setId(generateViewId());
        dynamicLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsLl = new LinearLayout.LayoutParams(
                Math.round(0*dm.density),
                Math.round(35*dm.density),
                1.0f
        );

        final EditText firstEt = new EditText(getContext());
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
                0.68f
        );

        final EditText secondEt = new EditText(getContext());
        secondEt.setId(generateViewId());
        secondEt.setBackground(getResources().getDrawable(R.drawable.radius_4dp_gray));
        secondEt.setHint(getResources().getString(R.string.newRecipeQuantityHint));
        secondEt.setHintTextColor(getResources().getColor(R.color.colorLoginGray));
        secondEt.setTextColor(getResources().getColor(R.color.colorLoginBlack));
        secondEt.setTextSize(12);
        secondEt.setInputType(InputType.TYPE_CLASS_TEXT);
        secondEt.setPadding(Math.round(5*dm.density), 0, Math.round(5*dm.density), 0);
        LinearLayout.LayoutParams paramsEt2 = new LinearLayout.LayoutParams(
                Math.round(0*dm.density),
                Math.round(35*dm.density),
                0.32f
        );
        paramsEt2.leftMargin = Math.round(5*dm.density);

        dynamicLinearLayout.addView(firstEt, paramsEt1);
        dynamicLinearLayout.addView(secondEt, paramsEt2);

        final ImageView deleteBtn = new ImageView(getContext());
        deleteBtn.setImageResource(R.drawable.ic_x);
        LinearLayout.LayoutParams paramsIv = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsIv.leftMargin = Math.round(8*dm.density);

        rootLinearLayout.addView(dynamicLinearLayout, paramsLl);
        rootLinearLayout.addView(deleteBtn, paramsIv);

        mLlDynamicArea.addView(rootLinearLayout, paramsRoot);

        mRootLayoutID.add(rootLinearLayout.getId());
        mLayoutID.add(dynamicLinearLayout.getId());
        mFirstEtID.add(firstEt.getId());
        mSecondEtID.add(secondEt.getId());
        mDeleteBtnID.add(deleteBtn.getId());

        deleteBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                mRootLayoutID.remove(Integer.valueOf(rootLinearLayout.getId()));
                mLayoutID.remove(Integer.valueOf(dynamicLinearLayout.getId()));
                mFirstEtID.remove(Integer.valueOf(firstEt.getId()));
                mSecondEtID.remove(Integer.valueOf(secondEt.getId()));
                mDeleteBtnID.remove(Integer.valueOf(deleteBtn.getId()));
                dynamicLinearLayout.removeView(firstEt);
                dynamicLinearLayout.removeView(secondEt);
                rootLinearLayout.removeView(dynamicLinearLayout);
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
        mNewRecipeImageRecyclerViewAdapter.notifyDataSetChanged();
    }

    public abstract class OnSingleClickListener implements View.OnClickListener {
        //중복클릭시간차이
        private static final long MIN_CLICK_INTERVAL=600;

        //마지막으로 클릭한 시간
        private long mLastClickTime;

        public abstract void onSingleClick(View v);

        @Override
        public final void onClick(View v) {
            //현재 클릭한 시간
            long currentClickTime= SystemClock.uptimeMillis();
            //이전에 클릭한 시간과 현재시간의 차이
            long elapsedTime=currentClickTime-mLastClickTime;
            //마지막클릭시간 업데이트
            mLastClickTime=currentClickTime;

            //내가 정한 중복클릭시간 차이를 안넘었으면 클릭이벤트 발생못하게 return
            if(elapsedTime<=MIN_CLICK_INTERVAL)
                return;
            //중복클릭시간 아니면 이벤트 발생
            onSingleClick(v);
        }
    }
}
