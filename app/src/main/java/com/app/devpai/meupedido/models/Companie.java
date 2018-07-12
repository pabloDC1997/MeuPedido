package com.app.devpai.meupedido.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lucas on 17/10/2017.
 */

public class Companie implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("cnpj")
    private String cnpj;

    @SerializedName("nome")
    private String nome;

    @SerializedName("e_mail")
    private String eMail;

    @SerializedName("site")
    private String site;

    @SerializedName("abertura")
    private String abertura;

    @SerializedName("fechamento")
    private String fechamento;

    @SerializedName("foto")
    private String foto;

    @SerializedName("rua")
    private String rua;

    @SerializedName("cep")
    private int cep;

    @SerializedName("bairro")
    private String bairro;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("numero")
    private int numero;

    @SerializedName("complemento")
    private String complemento;

    @SerializedName("cidade")
    private String cidade;

    @SerializedName("estado")
    private String estado;

    @SerializedName("uf")
    private String uf;

    @SerializedName("numero_tel_fixo")
    private String numeroTelFixo;

    @SerializedName("numero_celular")
    private String numeroCelular;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAbertura() {
        return abertura;
    }

    public void setAbertura(String abertura) {
        this.abertura = abertura;
    }

    public String getFechamento() {
        return fechamento;
    }

    public void setFechamento(String fechamento) {
        this.fechamento = fechamento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNumeroTelFixo() {
        return numeroTelFixo;
    }

    public void setNumeroTelFixo(String numeroTelFixo) {
        this.numeroTelFixo = numeroTelFixo;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

}
