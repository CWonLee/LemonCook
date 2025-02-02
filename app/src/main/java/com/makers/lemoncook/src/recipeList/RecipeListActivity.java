package com.makers.lemoncook.src.recipeList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.recipeList.adapter.RecipeListRecyclerViewAdapter;
import com.makers.lemoncook.src.recipeList.interfaces.RecipeListActivityView;
import com.makers.lemoncook.src.recipeList.models.RequestPostZZim;
import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;
import com.makers.lemoncook.src.search.SearchActivity;

import java.util.ArrayList;

public class RecipeListActivity extends BaseActivity implements RecipeListActivityView {

    RecyclerView mRecyclerView;
    ArrayList<ResponseGetRecipe.Result> mData = new ArrayList<>();
    ArrayList<Integer> mZZim = new ArrayList<>();
    TextView mTvTitle, mTvOrderNew, mTvOrderName;
    ImageView mIvBack, mIvSearch;
    RecipeListRecyclerViewAdapter mRecipeListRecyclerViewAdapter;
    LinearLayoutManager mRvLinearLayoutManager;
    int mOrder = 1;
    int mPage = 1;
    boolean mNewPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.recipe_list_rv);
        mTvTitle = findViewById(R.id.recipe_list_tv_title);
        mIvBack = findViewById(R.id.recipe_list_iv_back);
        mTvOrderNew = findViewById(R.id.recipe_list_tv_new_order);
        mTvOrderName = findViewById(R.id.recipe_list_tv_popular_order);
        mIvSearch = findViewById(R.id.recipe_list_iv_search);

        if (getIntent().getStringExtra("filter").equals("My Recipe")) {
            mTvTitle.setText("내 " + getIntent().getStringExtra("category") + " 레시피");
        }
        else {
            mTvTitle.setText("스타 " + getIntent().getStringExtra("category") + " 레시피");
        }
        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        mRvLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRvLinearLayoutManager);
        mRecipeListRecyclerViewAdapter = new RecipeListRecyclerViewAdapter(mData, this, this, mZZim);
        mRecyclerView.setAdapter(mRecipeListRecyclerViewAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mRvLinearLayoutManager.getItemCount();
                int lastVisible = mRvLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisible >= totalItemCount - 1) {
                    if (mNewPage) {
                        mPage++;
                        if (mOrder == 1) {
                            getRecipe("최신순", false);
                        }
                        else {
                            getRecipe("인기순", false);
                        }
                    }
                }
            }
        });

        getRecipe("최신순", true);

        mTvOrderNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrder == 2) {
                    mOrder = 1;
                    mPage = 1;
                    mNewPage = true;
                    mTvOrderNew.setTextColor(getResources().getColor(R.color.colorLoginEtBlack));
                    mTvOrderName.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    getRecipe("최신순", true);
                }
            }
        });
        mTvOrderName.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mOrder == 1) {
                    mOrder = 2;
                    mPage = 1;
                    mNewPage = true;
                    mTvOrderNew.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvOrderName.setTextColor(getResources().getColor(R.color.colorLoginEtBlack));
                    getRecipe("인기순", true);
                }
            }
        });

        mIvSearch.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(RecipeListActivity.this, SearchActivity.class);
                intent.putExtra("filter", getIntent().getStringExtra("filter"));
                startActivity(intent);
            }
        });
    }

    public void getRecipe(String order, boolean clearData) {
        RecipeListService recipeListService = new RecipeListService(this);
        recipeListService.getRecipe(getIntent().getStringExtra("category"), getIntent().getStringExtra("filter"), order, mPage, clearData);
    }

    @Override
    public void getRecipeSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseGetRecipe.Result> result, boolean clearData) {
        if (clearData) {
            mData.clear();
        }
        if (isSuccess && code == 200) {
            if (result.size() < 10) {
                mNewPage = false;
            }
            for (int i = 0; i < result.size(); i++) {
                mData.add(result.get(i));
                mZZim.add(result.get(i).getIsSave());
            }
            mRecipeListRecyclerViewAdapter.notifyDataSetChanged();
        }
        else if (code == 301) {
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

    @Override
    public void deleteRecipeSuccess(boolean isSuccess, int code, String message, int idx) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            mData.remove(idx);
            mZZim.remove(idx);
            mRecipeListRecyclerViewAdapter.notifyDataSetChanged();
            showCustomToast(message);
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void deleteRecipeFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void deleteRecipe(int recipeNo, int idx) {
        if (!getIntent().getStringExtra("filter").equals("My Recipe")) {
            showCustomToast("스타레시피는 삭제하실 수 없습니다");
        }
        else {
            showProgressDialog();
            RecipeListService recipeListService = new RecipeListService(this);
            recipeListService.deleteRecipe(Integer.toString(recipeNo), idx);
        }
    }

    @Override
    public void postZZim(int recipeNo, int idx) {
        showProgressDialog();
        RecipeListService recipeListService = new RecipeListService(this);
        RequestPostZZim requestPostZZim = new RequestPostZZim();
        requestPostZZim.setRecipeNo(recipeNo);
        recipeListService.postZZim(requestPostZZim, idx);
    }

    @Override
    public void deleteZZim(int recipeNo, int idx) {
        showProgressDialog();
        RecipeListService recipeListService = new RecipeListService(this);
        recipeListService.deleteZZim(recipeNo, idx);
    }

    @Override
    public void postZZimSuccess(boolean isSuccess, int code, String message, int idx) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);
            mZZim.set(idx, 1);

            mRecipeListRecyclerViewAdapter.notifyDataSetChanged();
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void postZZimFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }

    @Override
    public void deleteZZimSuccess(boolean isSuccess, int code, String message, int idx) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            showCustomToast(message);
            mZZim.set(idx, 0);

            mRecipeListRecyclerViewAdapter.notifyDataSetChanged();
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void deleteZZimFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }
}
