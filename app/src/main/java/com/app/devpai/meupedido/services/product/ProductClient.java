package com.app.devpai.meupedido.services.product;

import android.util.Log;

import com.app.devpai.meupedido.models.Product;
import com.app.devpai.meupedido.services.callbacks.ApiMpCallback;
import com.app.devpai.meupedido.services.retrofit.RetrofitClient;
import com.app.devpai.meupedido.services.shared.MessageError;
import com.app.devpai.meupedido.utils.Keys;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pablo on 14/05/2017.
 */

public class ProductClient {

    private static final String DEBUG_TAG = ProductClient.class.getName();
    private static ProductApi service = RetrofitClient.getClient(Keys.BASE_URL_MEUPEDIDO).create(ProductApi.class);

    public static void getProduct(int idEmpresa, int idProduto, final ApiMpCallback.CallbackRequestProducts callback) {

        callback.onPreExecution();
        service.getProduct(idEmpresa, idProduto)
                .enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {

                        Log.i(DEBUG_TAG, Keys.RESPONSE_CODE + response.code());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_BODY + response.body().toString());
                        Log.i(DEBUG_TAG, Keys.RESPONSE_MESSAGE + response.toString());

                        if (response.isSuccessful()){
                            Product product = response.body();
                            callback.onSuccess(product);
                        } else {
                            callback.onResponseUnexpected(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.e(DEBUG_TAG, Keys.ON_FAILURE_ERROR + t.getMessage());
                        callback.onFailure(t.getMessage());
                    }
                });

    }

    public static void getListProduct( int idEmpresa, final ApiMpCallback.CallbackRequestListProducts callback ) {
        callback.onPreExecution();
        service.getListProduct(idEmpresa)
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                        Log.i(DEBUG_TAG, "Resoponse code: " + response.code());
                        Log.i(DEBUG_TAG, "Resoponse body: " + response.body());
                        Log.i(DEBUG_TAG, "Resoponse message: " + response);

                        if (response.isSuccessful()) {
                            List<Product> listProduct = response.body();
                            callback.onSuccess(listProduct);
                        } else {
                            String message = MessageError.validateResponseCode(response.code(), response.message());
                            callback.onResponseUnexpected(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Log.e(DEBUG_TAG, "ERROR: " + t.getMessage() );
                        callback.onFailure(t.getMessage());
                    }
                });
    }

}
