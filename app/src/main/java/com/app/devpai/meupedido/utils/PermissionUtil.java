package com.app.devpai.meupedido.utils;

import android.content.Context;
import com.app.devpai.meupedido.callbacks.CallbackPermission;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by Pablo on 24/01/2017.
 */


/**
 * Classe com bug, precisa ser corrigida
 */
//TODO - arrumar essa classe
public class PermissionUtil {
    private Context mContext;
    CallbackPermission mCallback;
    String typePermission;

    public PermissionUtil(Context mContext, String typePermission, CallbackPermission callback) {

        this.mContext = mContext;
        this.mCallback = callback;
        this.typePermission = typePermission;
    }
//
//    public void getPermission() {
//        PermissionListener permissionlistener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                mCallback.permissionResponse(true);
//            }
//
//            @Override
//            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                mCallback.permissionResponse(false);
//            }
//        };
//        new TedPermission(mContext)
//                .setPermissionListener(permissionlistener)
//                .setRationaleMessage("Easy contacts precissa de permissão para acessar recursos do seu telefone.")
//                .setPermissions(typePermission)
//                .check();
//    }
}
