package com.app.devpai.meupedido.utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Pablo on 08/07/2017.
 */


public class EncryptUtil {
    public static final String DEBUG_TAG = EncryptUtil.class.getName();

    public static String encrypt(String param) {
        try {

            String salt = "fd2d3ef92a727d01a5e12e10692d9a2d";
            String s = salt + param;
            MessageDigest m= MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            return new BigInteger(1,m.digest()).toString(16);

        } catch(Exception e ) {
            Log.e(DEBUG_TAG, e.getMessage());
            return null;
        }
    }
}
