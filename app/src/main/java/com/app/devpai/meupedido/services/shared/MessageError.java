package com.app.devpai.meupedido.services.shared;

import android.support.annotation.Nullable;

/**
 * Created by pablo.couto on 25/01/2018.
 */

public class MessageError {

    public static String validateResponseCode(int code, @Nullable String msg) {

        switch (code){
            case 404:
                return code + " - Ops! O servidor está temporariamente indisponivel. Por favor, tente acessar de novo mais tarde.";

            case 500:
                return code + " - Ops! O ocorreu um erro interno no servidor. Por favor, tente acessar de novo mais tarde.";

            default:
                return code + " - " +msg;
        }
    }
}
