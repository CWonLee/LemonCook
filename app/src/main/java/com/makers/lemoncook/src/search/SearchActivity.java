package com.makers.lemoncook.src.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView = findViewById(R.id.search_rv);
        mEtSearchBar = findViewById(R.id.search_et_search_bar);
        mIvBack = findViewById(R.id.search_iv_back);

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchRecyclerViewAdapter = new SearchRecyclerViewAdapter(mData);
        mRecyclerView.setAdapter(mSearchRecyclerViewAdapter);

        mEtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSearch(mEtSearchBar.getText().toString(), "My Recipe");
            }
        });

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });
    }

    public void getSearch(String search, String filter) {
        System.out.println(search + " " + filter);
        SearchService searchService = new SearchService(this);
        searchService.getSearch(search, filter);
    }

    @Override
    public void searchSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseSearch.Result> result) {
        if (isSuccess && code == 200) {
            mData.clear();
            for (int i = 0; i < result.size(); i++) {
                mData.add(result.get(i));
            }
            mSearchRecyclerViewAdapter.notifyDataSetChanged();
        }
        else {
            mData.clear();
            mSearchRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void searchFailure() {
        showCustomToast(getResources().getString(R.string.network_error));
    }
}
