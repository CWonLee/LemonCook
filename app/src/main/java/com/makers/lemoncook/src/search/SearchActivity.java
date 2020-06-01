package com.makers.lemoncook.src.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.makers.lemoncook.src.search.adapters.SearchRecyclerViewAdapter;
import com.makers.lemoncook.src.search.interfaces.SearchActivityView;
import com.makers.lemoncook.src.search.models.ResponseSearch;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements SearchActivityView {

    EditText mEtSearchBar;
    RecyclerView mRecyclerView;
    SearchRecyclerViewAdapter mSearchRecyclerViewAdapter;
    ArrayList<ResponseSearch.Result> mData = new ArrayList<>();
    ImageView mIvBack;
    String mOrder = "인기순";
    String mFilter;
    int mPage = 1;
    boolean mNewPage = true;
    VerticalTextView mVtPopularOrder, mVtNewOrder;
    LinearLayoutManager mRvLinearLayoutManager;
    NestedScrollView mNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView = findViewById(R.id.search_rv);
        mEtSearchBar = findViewById(R.id.search_et_search_bar);
        mIvBack = findViewById(R.id.search_iv_back);
        mVtPopularOrder = findViewById(R.id.search_vt_popular_order);
        mVtNewOrder = findViewById(R.id.search_vt_new_order);
        mNestedScrollView = findViewById(R.id.search_nested_scroll_view);

        mRecyclerView.setNestedScrollingEnabled(false);
        mRvLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRvLinearLayoutManager);
        mSearchRecyclerViewAdapter = new SearchRecyclerViewAdapter(mData);
        mRecyclerView.setAdapter(mSearchRecyclerViewAdapter);

        mNestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                View view = mNestedScrollView.getChildAt(mNestedScrollView.getChildCount() - 1);

                int diff = (view.getBottom() - (mNestedScrollView.getHeight() + mNestedScrollView.getScrollY()));

                if (diff == 0) {
                    System.out.println("마지막페이지");
                    if (mNewPage) {
                        mPage++;
                        getSearch(mEtSearchBar.getText().toString(), false);
                    }
                }
            }
        });

        mFilter = getIntent().getStringExtra("filter");

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

        mVtPopularOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrder == "최신순") {
                    mVtPopularOrder.setTextColor(getResources().getColor(R.color.colorLoginEtBlack));
                    mVtNewOrder.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mOrder = "인기순";
                    mPage = 1;
                    mNewPage = true;
                    getSearch(mEtSearchBar.getText().toString(), true);
                }
            }
        });
        mVtNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrder == "인기순") {
                    mVtPopularOrder.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mVtNewOrder.setTextColor(getResources().getColor(R.color.colorLoginEtBlack));
                    mOrder = "최신순";
                    mPage = 1;
                    mNewPage = true;
                    getSearch(mEtSearchBar.getText().toString(), true);
                }
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
        SearchService searchService = new SearchService(this);
        searchService.getSearch(search, mFilter, mOrder, mPage, clearData);
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
            mSearchRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void searchFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }
}
