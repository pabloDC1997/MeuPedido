package com.app.devpai.meupedido.services.authenticate;

import com.app.devpai.meupedido.models.Login;
import com.app.devpai.meupedido.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Pablo on 03/07/2017.
 */



public interface AuthenticateApi {

        @POST("api-meupedido/usuario/autenticar")
        Call<Login> autenticar(@Body User user);
}
