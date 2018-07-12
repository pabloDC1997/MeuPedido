package com.app.devpai.meupedido.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pablo on 13/05/2017.
 */

public class Address {

    @SerializedName("cep")
    private String cep;

    @SerializedName("logradouro")
    private String logradouro;

    @SerializedName("complemento")
    private String complement;

    @SerializedName("bairro")
    private String neighbor;

    @SerializedName("localidade")
    private String city;

    @SerializedName("uf")
    private String uf;

    @SerializedName("unidade")
    private String unidade;

    @SerializedName("ibge")
    private String ibge;

    @SerializedName("gia")
    private String gia;



    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(String neighbor) {
        this.neighbor = neighbor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public static Address parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Address addressResponse = gson.fromJson(response, Address.class);
        return addressResponse;
    }

    @Override
    public String toString() {
        return      "\nCEP:\t" + this.cep
                +   "\nRUA:\t" + this.logradouro
                +   "\nComplemento:\t" + this.complement
                +   "\nCidade:\t" + this.neighbor
                +   "\nEstado:\t" + this.city;
    }
}
