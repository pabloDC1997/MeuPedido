
package com.app.devpai.meupedido.models;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("user")
    private User user;

    @SerializedName("status")
    private boolean status;

    @SerializedName("msg")
    private String msg;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
