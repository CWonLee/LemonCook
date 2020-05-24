package com.makers.lemoncook.src.myPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.myPage.adpaters.MyPageRecyclerViewAdapter;
import com.makers.lemoncook.src.myPage.interfaces.MyPageActivityView;
import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyPageActivity extends BaseActivity implements MyPageActivityView {

    TextView mTvUserName, mTvMyRecipeCnt, mTvGetRecipeCnt, mTvZzim;
    RecyclerView mRecyclerView;
    MyPageRecyclerViewAdapter mMyPageRecyclerViewAdapter;
    ArrayList<ResponseGetMyPage.Result.RecipeInfo> mData = new ArrayList<>();
    ImageView mIvBack;
    int mPage = 1;
    boolean mNewPage = true;
    LinearLayoutManager mRvLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        mTvUserName = findViewById(R.id.my_page_tv_user_name);
        mTvMyRecipeCnt = findViewById(R.id.my_page_tv_my_recipe_cnt);
        mTvGetRecipeCnt = findViewById(R.id.my_page_tv_get_recipe_cnt);
        mTvZzim = findViewById(R.id.my_page_tv_zzim_cnt);
        mRecyclerView = findViewById(R.id.my_page_rv);
        mIvBack = findViewById(R.id.my_page_iv_back);


        mRvLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRvLinearLayoutManager);
        mMyPageRecyclerViewAdapter = new MyPageRecyclerViewAdapter(mData, this);
        mRecyclerView.setAdapter(mMyPageRecyclerViewAdapter);

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        getMyPage();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mRvLinearLayoutManager.getItemCount();
                int lastVisible = mRvLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisible >= totalItemCount - 1) {
                    if (mNewPage) {
                        mPage++;
                        getMyPage();
                    }
                }
            }
        });
    }

    public void getMyPage() {
        showProgressDialog();
        MyPageService myPageService = new MyPageService(this);
        myPageService.getMyPage(mPage);
    }

    @Override
    public void getMyPageSuccess(boolean isSuccess, int code, String message, ResponseGetMyPage.Result result) {
        hideProgressDialog();
        if (isSuccess && code == 200) {
            for (int i = 0; i < result.getRecipeInfo().size(); i++) {
                mData.add(result.getRecipeInfo().get(i));
            }
            mMyPageRecyclerViewAdapter.notifyDataSetChanged();
            mTvUserName.setText(result.getNickname());
            System.out.println(result.getRegisterRecipe());
            mTvMyRecipeCnt.setText(Integer.toString(result.getRegisterRecipe()));
            mTvGetRecipeCnt.setText(Integer.toString(result.getSharedRecipe()));
            mTvZzim.setText("0");
        }
        else {
            showCustomToast(message);
        }
    }

    @Override
    public void getMyPageFailure() {
        hideProgressDialog();
        showCustomToast(getResources().getString(R.string.network_error));
    }
}
