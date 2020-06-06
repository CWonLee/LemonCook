package com.makers.lemoncook.src.loadRecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.VerticalTextView;
import com.makers.lemoncook.src.loadRecipe.adapters.LoadRecyclerViewAdapter;
import com.makers.lemoncook.src.loadRecipe.interfaces.LoadActivityView;
import com.makers.lemoncook.src.search.adapters.SearchRecyclerViewAdapter;
import com.makers.lemoncook.src.search.models.ResponseSearch;

import java.util.ArrayList;

public class LoadRecipeActivity extends BaseActivity implements LoadActivityView {

    EditText mEtSearchBar;
    RecyclerView mRecyclerView;
    LoadRecyclerViewAdapter mLoadRecyclerViewAdapter;
    ArrayList<ResponseSearch.Result> mData = new ArrayList<>();
    ImageView mIvBack;
    String mOrder = "최신순";
    String mFilter = "All";
    int mPage = 1;
    boolean mNewPage = true;
    LinearLayoutManager mRvLinearLayoutManager;
    NestedScrollView mNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_recipe);

        mRecyclerView = findViewById(R.id.load_recipe_rv);
        mEtSearchBar = findViewById(R.id.load_recipe_et_search_bar);
        mIvBack = findViewById(R.id.load_recipe_iv_back);
        mNestedScrollView = findViewById(R.id.load_recipe_nested_scroll_view);

        mRecyclerView.setNestedScrollingEnabled(false);
        mRvLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRvLinearLayoutManager);
        mLoadRecyclerViewAdapter = new LoadRecyclerViewAdapter(mData, this, this);
        mRecyclerView.setAdapter(mLoadRecyclerViewAdapter);

        mNestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = mNestedScrollView.getChildAt(mNestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (mNestedScrollView.getHeight() + mNestedScrollView.getScrollY()));

                if (diff == 0) {
                    if (mNewPage) {
                        mPage++;
                        getSearch(mEtSearchBar.getText().toString(), false);
                    }
                }
            }
        });

        getSearch("", true);

        mEtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPage = 1;
                mNewPage = true;
                getSearch(s.toString(), true);
            }
        });

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    public void getSearch(String search, boolean clearData) {
        LoadService loadService = new LoadService(this);
        loadService.getSearch(search, mFilter, mOrder, mPage, clearData);
    }

    @Override
    public void searchSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseSearch.Result> result, boolean clearData) {
        if (isSuccess && code == 200) {
            if (clearData) {
                mData.clear();
            }
            if (result.size() < 10) {
                mNewPage = false;
            }
            for (int i = 0; i < result.size(); i++) {
                mData.add(result.get(i));
            }
            mLoadRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void searchFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void loadRecipeNo(int recipeNo) {
        Intent intent = new Intent();
        intent.putExtra("recipeNo", recipeNo);
        setResult(RESULT_OK, intent);
        finish();
    }
}
