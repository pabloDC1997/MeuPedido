package com.app.devpai.meupedido.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pablo on 16/09/2017.
 */

public class UserAuthResponse {
    @SerializedName("response")
    int hasU;

    private int getHasU() {
        return hasU;
    }

    private void setHasU(int hasU) {
        this.hasU = hasU;
    }

    public boolean hasUser(){
        return (this.hasU == 1);
    }
}
