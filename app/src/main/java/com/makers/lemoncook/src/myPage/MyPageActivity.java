package com.makers.lemoncook.src.myPage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.makers.lemoncook.R;
import com.makers.lemoncook.src.BaseActivity;
import com.makers.lemoncook.src.login.LoginActivity;
import com.makers.lemoncook.src.myPage.adpaters.MyPageRecyclerViewAdapter;
import com.makers.lemoncook.src.myPage.interfaces.MyPageActivityView;
import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;
import com.makers.lemoncook.src.setting.SettingActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.makers.lemoncook.src.ApplicationClass.sSharedPreferences;

public class MyPageActivity extends BaseActivity implements MyPageActivityView {

    TextView mTvUserName, mTvMyRecipeCnt, mTvGetRecipeCnt, mTvZzimCnt, mTvMyRecipe, mTvGetRecipe, mTvZzim;
    RecyclerView mRecyclerView;
    ImageView mIvSetting;
    MyPageRecyclerViewAdapter mMyPageRecyclerViewAdapter;
    ArrayList<ResponseGetMyPage.Result.RecipeInfo> mData = new ArrayList<>();
    ImageView mIvBack;
    int mPage = 1;
    boolean mNewPage = true;
    LinearLayoutManager mRvLinearLayoutManager;
    ConstraintLayout mClMyRecipe, mClGetRecipe, mClZzim;
    String mTab = "register", mUserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        mTvUserName = findViewById(R.id.my_page_tv_user_name);
        mTvMyRecipeCnt = findViewById(R.id.my_page_tv_my_recipe_cnt);
        mTvGetRecipeCnt = findViewById(R.id.my_page_tv_get_recipe_cnt);
        mTvZzimCnt = findViewById(R.id.my_page_tv_zzim_cnt);
        mRecyclerView = findViewById(R.id.my_page_rv);
        mIvBack = findViewById(R.id.my_page_iv_back);
        mTvMyRecipe = findViewById(R.id.my_page_tv_my_recipe_text);
        mTvGetRecipe = findViewById(R.id.my_page_tv_get_recipe_text);
        mTvZzim = findViewById(R.id.my_page_tv_zzim_text);
        mClMyRecipe = findViewById(R.id.my_page_cl_my_recipe);
        mClGetRecipe = findViewById(R.id.my_page_cl_get_recipe);
        mClZzim = findViewById(R.id.my_page_cl_zzim);
        mIvSetting = findViewById(R.id.my_page_iv_setting);


        mRvLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRvLinearLayoutManager);
        mMyPageRecyclerViewAdapter = new MyPageRecyclerViewAdapter(mData, this);
        mMyPageRecyclerViewAdapter.setTab(mTab);
        mRecyclerView.setAdapter(mMyPageRecyclerViewAdapter);

        mIvBack.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                finish();
            }
        });

        getMyPage(false);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mRvLinearLayoutManager.getItemCount();
                int lastVisible = mRvLinearLayoutManager.findLastCompletelyVisibleItemPosition();

                if (lastVisible >= totalItemCount - 1) {
                    if (mNewPage) {
                        mPage++;
                        getMyPage(false);
                    }
                }
            }
        });

        mClMyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTab.equals("register")) {
                    mTab = "register";
                    mPage = 1;
                    mMyPageRecyclerViewAdapter.setTab(mTab);
                    mNewPage = true;
                    mTvMyRecipe.setTextColor(getResources().getColor(R.color.colorSplashTitleBlack));
                    mTvMyRecipeCnt.setTextColor(getResources().getColor(R.color.colorSplashTitleBlack));
                    mTvGetRecipe.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvGetRecipeCnt.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvZzim.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvZzimCnt.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    getMyPage(true);
                }
            }
        });
        mClGetRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTab.equals("share")) {
                    mTab = "share";
                    mPage = 1;
                    mMyPageRecyclerViewAdapter.setTab(mTab);
                    mNewPage = true;
                    mTvMyRecipe.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvMyRecipeCnt.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvGetRecipe.setTextColor(getResources().getColor(R.color.colorSplashTitleBlack));
                    mTvGetRecipeCnt.setTextColor(getResources().getColor(R.color.colorSplashTitleBlack));
                    mTvZzim.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvZzimCnt.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    getMyPage(true);
                }
            }
        });
        mClZzim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTab.equals("savelist")) {
                    mTab = "savelist";
                    mPage = 1;
                    mMyPageRecyclerViewAdapter.setTab(mTab);
                    mNewPage = true;
                    mTvMyRecipe.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvMyRecipeCnt.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvGetRecipe.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvGetRecipeCnt.setTextColor(getResources().getColor(R.color.colorLoginGray));
                    mTvZzim.setTextColor(getResources().getColor(R.color.colorSplashTitleBlack));
                    mTvZzimCnt.setTextColor(getResources().getColor(R.color.colorSplashTitleBlack));
                    getMyPage(true);
                }
            }
        });

        mIvSetting.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(MyPageActivity.this, SettingActivity.class);
                intent.putExtra("name", mUserName);
                startActivity(intent);
            }
        });
    }

    public void getMyPage(boolean clearData) {
        MyPageService myPageService = new MyPageService(this);
        myPageService.getMyPage(mPage, mTab, clearData);
    }

    @Override
    public void getMyPageSuccess(boolean isSuccess, int code, String message, ResponseGetMyPage.Result result, boolean clearData) {
        if (clearData) {
            mData.clear();
        }
        if (isSuccess && code == 200) {
            for (int i = 0; i < result.getRecipeInfo().size(); i++) {
                mData.add(result.getRecipeInfo().get(i));
            }
            mMyPageRecyclerViewAdapter.notifyDataSetChanged();
            if (mUserName.equals("")) {
                mUserName = result.getNickname();
            }
            mTvUserName.setText(result.getNickname());
            mTvMyRecipeCnt.setText(Integer.toString(result.getRegisterRecipe()));
            mTvGetRecipeCnt.setText(Integer.toString(result.getSharedRecipe()));
            mTvZzimCnt.setText(Integer.toString(result.getSaveListCnt()));
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
