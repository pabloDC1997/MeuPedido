package com.app.devpai.meupedido.services.product;

import com.app.devpai.meupedido.models.Address;
import com.app.devpai.meupedido.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Pablo on 03/07/2017.
 */

public interface ProductApi {

    @GET("/api-meupedido/produtos/listar/{id_empresa}")
    Call<List<Product>> getListProduct(@Path("id_empresa") int idEmpresa);

    @GET("/api-meupedido/produtos/listar/{id_empresa}/{id_produto}")
    Call<Product> getProduct(@Path("id_empresa") int idEmpresa, @Path("id_produto") int idProduto);
}
