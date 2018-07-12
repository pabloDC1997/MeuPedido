package com.app.devpai.meupedido.services.authenticate;

import android.util.Log;

import com.app.devpai.meupedido.models.Login;
import com.app.devpai.meupedido.models.User;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.retrofit.RetrofitClient;
import com.app.devpai.meupedido.services.shared.MessageError;
import com.app.devpai.meupedido.utils.Keys;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pablo on 03/07/2017.
 */

public class AuthenticateClient {

    private static final String DEBUG_TAG = AuthenticateClient.class.getName();
    private static final AuthenticateApi service = RetrofitClient.getClient(Keys.BASE_URL_MEUPEDIDO).create(AuthenticateApi.class);

    public static void autenticar(User user, final ApiMpCallback.CallbackRequestAuthenticate callback ){
        callback.onPreExecution();

        service.autenticar(user)
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {

                        Log.i(DEBUG_TAG, Keys.RESPONSE_CODE + response.code());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_BODY + response.body().toString());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_MESSAGE + response.toString());

                        if (response.isSuccessful()){
                            Login login = response.body();
                            callback.onSuccess(login);
                        } else {
                            String message = MessageError.validateResponseCode(response.code(), response.message());
                            callback.onResponseUnexpected(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Log.e(DEBUG_TAG, Keys.ON_FAILURE_ERROR + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                });
    }
}
