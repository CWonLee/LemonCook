package com.makers.lemoncook.src.addRecipe.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

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
import com.makers.lemoncook.src.VerticalTextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class NewRecipeFragment extends Fragment {

    LinearLayout mLlExpandableContent, mLlDynamicArea;
    ConstraintLayout mClExpandableBtn, mClDynamicPlusBtn;
    boolean expandable = true;
    VerticalTextView mTvIngredientTitle;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    ArrayList<Integer> mLayoutID, mFirstEtID, mSecondEtID, mDeleteBtnID;

    public NewRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);

        mLlExpandableContent = view.findViewById(R.id.new_recipe_ll_expandable);
        mClExpandableBtn = view.findViewById(R.id.new_recipe_cl_expandable_btn);
        mTvIngredientTitle = view.findViewById(R.id.new_recipe_vt_ingredient);
        mLlDynamicArea = view.findViewById(R.id.new_recipe_ll_dynamic_area);
        mClDynamicPlusBtn = view.findViewById(R.id.new_recipe_cl_dynamic_plus_btn);

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
}
