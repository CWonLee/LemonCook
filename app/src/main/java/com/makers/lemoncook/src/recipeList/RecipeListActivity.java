package com.makers.lemoncook.src.recipeList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.recipeList.adapter.RecipeListRecyclerViewAdapter;
import com.makers.lemoncook.src.recipeList.interfaces.RecipeListActivityView;
import com.makers.lemoncook.src.recipeList.models.ResponseGetRecipe;

import java.util.ArrayList;

public class RecipeListActivity extends BaseActivity implements RecipeListActivityView {

    RecyclerView mRecyclerView;
    ArrayList<ResponseGetRecipe.Result> mData = new ArrayList<>();
    TextView mTvTitle, mTvOrderNew, mTvOrderName;
    ImageView mIvBack;
    RecipeListRecyclerViewAdapter mRecipeListRecyclerViewAdapter;
    int mOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = findViewById(R.id.recipe_list_rv);
        mTvTitle = findViewById(R.id.recipe_list_tv_title);
        mIvBack = findViewById(R.id.recipe_list_iv_back);
        mTvOrderNew = findViewById(R.id.recipe_list_tv_new_order);
        mTvOrderName = findViewById(R.id.recipe_list_tv_name_order);
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecipeListRecyclerViewAdapter = new RecipeListRecyclerViewAdapter(mData);
        mRecyclerView.setAdapter(mRecipeListRecyclerViewAdapter);

        getRecipe("최신순");

        mTvOrderNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrder == 2) {
                    mOrder = 1;
                    mTvOrderNew.setTextColor(getResources().getColor(R.color.colorLoginEtBlack));
                    mTvOrderName.setTextColor(getResources().getColor(R.color.colorLoginGray));

                    getRecipe("최신순");
                }
            }
        });
        mTvOrderName.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (mOrder == 1) {
                    mOrder = 2;
                    mTvOrderNew.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvOrderName.setTextColor(getResources().getColor(R.color.colorLoginEtBlack));

                    getRecipe("이름순");
                }
            }
        });
    }

    public void getRecipe(String order) {
        showProgressDialog();
        RecipeListService recipeListService = new RecipeListService(this);
        recipeListService.getRecipe(getIntent().getStringExtra("category"), getIntent().getStringExtra("filter"), order);
    }

    @Override
    public void getRecipeSuccess(boolean isSuccess, int code, String message, ArrayList<ResponseGetRecipe.Result> result) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            mData.clear();
            for (int i = 0; i < result.size(); i++) {
                mData.add(result.get(i));
                System.out.println(result.get(i).getRecipeTilte());
                System.out.println(result.get(i).getRecipeName());
                System.out.println(result.get(i).getRecipeHashTag());
            }

            mRecipeListRecyclerViewAdapter.notifyDataSetChanged();
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void getRecipeFailure() {

    }
}
