package com.app.devpai.meupedido.services.address;

import com.app.devpai.meupedido.models.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pablo on 03/07/2017.
 */

public interface ViaCepApi {

    @GET("/ws/{cep}/json")
    Call<Address> getAddress(@Path("cep") int cep);

    @GET("/{uf}/{city}/{street}/json")
    Call<List<Address>> getCep(@Path("uf") String uf, @Path("city") String city, @Path("street") String street);
}
