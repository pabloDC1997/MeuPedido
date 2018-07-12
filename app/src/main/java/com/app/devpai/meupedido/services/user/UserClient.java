package com.app.devpai.meupedido.services.user;

import android.util.Log;

import com.app.devpai.meupedido.models.UserAuthResponse;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.retrofit.RetrofitClient;
import com.app.devpai.meupedido.services.shared.MessageError;
import com.app.devpai.meupedido.utils.Keys;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pablo on 03/07/2017.
 */

public class UserClient {

    private static final String DEBUG_TAG = UserClient.class.getName();
    private static final UserApi service = RetrofitClient.getClient(Keys.BASE_URL_MEUPEDIDO).create(UserApi.class);

    public static void hasUser(String userName, final ApiMpCallback.CallbackRequestHasUser callback ){
        callback.onPreExecution();
        service.hasUser(userName)
                .enqueue(new Callback<List<UserAuthResponse>>() {
                    @Override
                    public void onResponse(Call<List<UserAuthResponse>> call, Response<List<UserAuthResponse>> response) {

                        Log.i(DEBUG_TAG, Keys.RESPONSE_CODE + response.code());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_BODY + response.body().toString());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_MESSAGE + response.toString());

                        if (response.isSuccessful()){
                            List<UserAuthResponse> r = response.body();
                            callback.onSuccess(r.get(0).hasUser());
                        } else {
                            String message = MessageError.validateResponseCode(response.code(), response.message());
                            callback.onResponseUnexpected(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserAuthResponse>> call, Throwable t) {
                        Log.e(DEBUG_TAG, Keys.ON_FAILURE_ERROR + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                });
    }
}
