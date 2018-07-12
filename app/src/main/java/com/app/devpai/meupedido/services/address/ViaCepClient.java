package com.app.devpai.meupedido.services.address;

import android.util.Log;

import com.app.devpai.meupedido.models.Address;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.retrofit.RetrofitClient;
import com.app.devpai.meupedido.services.shared.MessageError;
import com.app.devpai.meupedido.utils.Keys;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pablo on 14/05/2017.
 */

public class ViaCepClient {

    private static final String DEBUG_TAG = ViaCepClient.class.getName();
    private static final ViaCepApi service = RetrofitClient.getClient(Keys.BASE_URL_VIACEP).create(ViaCepApi.class);

    public static void getAddressService(  int cep, final ApiMpCallback.CallbackRequestAddress callback ){
        callback.onPreExecution();
        service.getAddress(cep)
                .enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {

                        Log.i(DEBUG_TAG, Keys.RESPONSE_CODE + response.code());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_BODY + response.body().toString());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_MESSAGE + response.toString());

                        if (response.isSuccessful()){
                            Address address = response.body();
                            callback.onSuccess(address);
                        } else {
                            String message = MessageError.validateResponseCode(response.code(), response.message());
                            callback.onResponseUnexpected(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        Log.e(DEBUG_TAG, Keys.ON_FAILURE_ERROR + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                });
    }
}
