package com.app.devpai.meupedido.services.companie;

import com.app.devpai.meupedido.models.Companie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Lucas on 18/10/2017.
 */

public interface CompanieApi {

    @GET("/api-meupedido/empresas/listar")
    Call<List<Companie>> getListCompanies();
}
