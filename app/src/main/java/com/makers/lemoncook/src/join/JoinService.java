package com.makers.lemoncook.src.join;

import com.makers.lemoncook.src.join.interfaces.JoinActivityView;
import com.makers.lemoncook.src.join.interfaces.JoinRetrofitInterface;
import com.makers.lemoncook.src.join.models.JoinRequest;
import com.makers.lemoncook.src.join.models.JoinResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makers.lemoncook.src.ApplicationClass.getRetrofit;

public class JoinService {
    private final JoinActivityView mJoinActivityView;

    JoinService(final JoinActivityView joinActivityView) {
        this.mJoinActivityView = joinActivityView;
    }

    void postJoin(JoinRequest joinRequest) {
        final JoinRetrofitInterface joinRetrofitInterface = getRetrofit().create(JoinRetrofitInterface.class);
        joinRetrofitInterface.postJoin(joinRequest).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                final JoinResponse joinResponse = response.body();
                mJoinActivityView.joinSuccess(joinResponse.isSuccess(), joinResponse.getCode(), joinResponse.getMessage());
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                mJoinActivityView.joinFailure();
            }
        });
    }
}
