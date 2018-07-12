package com.app.devpai.meupedido.services.user;

import com.app.devpai.meupedido.models.UserAuthResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pablo on 03/07/2017.
 */


public interface UserApi {

        @GET("api-meupedido/usuario/existe/{user_name}")
        Call<List<UserAuthResponse>> hasUser(@Path("user_name") String  user);
}
