package com.makers.lemoncook.src.myPage;

import com.makers.lemoncook.src.myPage.interfaces.MyPageActivityView;
import com.makers.lemoncook.src.myPage.interfaces.MyPageRetrofitInterface;
import com.makers.lemoncook.src.myPage.models.ResponseGetMyPage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class MyPageService {
    private final MyPageActivityView mMyPageActivityView;

    MyPageService(final MyPageActivityView myPageActivityView) {
        this.mMyPageActivityView = myPageActivityView;
    }

    void getMyPage() {
        final MyPageRetrofitInterface myPageRetrofitInterface = getRetrofit().create(MyPageRetrofitInterface.class);
        myPageRetrofitInterface.getMyPage().enqueue(new Callback<ResponseGetMyPage>() {
            @Override
            public void onResponse(Call<ResponseGetMyPage> call, Response<ResponseGetMyPage> response) {
                final ResponseGetMyPage responseGetMyPage = response.body();
                mMyPageActivityView.getMyPageSuccess(responseGetMyPage.isSuccess(), responseGetMyPage.getCode(), responseGetMyPage.getMessage(), responseGetMyPage.getResult());
            }

            @Override
            public void onFailure(Call<ResponseGetMyPage> call, Throwable t) {
                t.printStackTrace();
                mMyPageActivityView.getMyPageFailure();
            }
        });
    }
}
