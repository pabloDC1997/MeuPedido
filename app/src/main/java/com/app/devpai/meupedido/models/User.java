
package com.app.devpai.meupedido.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("id_cliente")
    private String idCliente;

    @SerializedName("nome")
    private String name;

    @SerializedName("login")
    private String login;

    @SerializedName("e-mail")
    private String eMail;

    @SerializedName("senha")
    private String password;

    @SerializedName("telefone")
    private String phone;

    public User() {
        this(null,null,null,null,null,null);
    }

    public User(String idCliente, String name, String login, String eMail, String password, String phone) {
        this.idCliente = idCliente;
        this.name = name;
        this.eMail = eMail;
        this.password = password;
        this.phone = phone;
        this.login = login;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
