package com.app.devpai.meupedido.services.companie;

import android.util.Log;

import com.app.devpai.meupedido.models.Companie;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.retrofit.RetrofitClient;
import com.app.devpai.meupedido.services.shared.MessageError;
import com.app.devpai.meupedido.utils.Keys;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lucas on 18/10/2017.
 */

public class CompanieClient {
    private static final String DEBUG_TAG = CompanieClient.class.getName();
    private static CompanieApi service = RetrofitClient.getClient(Keys.BASE_URL_MEUPEDIDO).create(CompanieApi.class);

    public static void getListCompanies(final ApiMpCallback.CallbackRequestListCompanies callback) {
        callback.onPreExecution();
        service.getListCompanies()
                .enqueue(new Callback<List<Companie>>() {
                    @Override
                    public void onResponse(Call<List<Companie>> call, Response<List<Companie>> response) {

                        Log.i(DEBUG_TAG, Keys.RESPONSE_CODE + response.code());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_BODY + response.body());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_MESSAGE + response.toString());

                        if (response.isSuccessful()) {
                            List<Companie> listCompanies = response.body();
                            callback.onSuccess(listCompanies);
                        } else {
                            String message = MessageError.validateResponseCode(response.code(), response.message());
                            callback.onResponseUnexpected(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Companie>> call, Throwable t) {
                        Log.e(DEBUG_TAG, Keys.ON_FAILURE_ERROR + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                });
    }
}
