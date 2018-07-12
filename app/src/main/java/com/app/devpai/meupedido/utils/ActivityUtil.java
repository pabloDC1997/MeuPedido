package com.app.devpai.meupedido.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.InputFilter;
import android.text.Spanned;

import java.io.Serializable;
import java.util.List;

import com.app.devpai.meupedido.models.Product;


/**
 * Created by Pablo on 24/01/2017.
 */

public class ActivityUtil {

    private Context mContext;
    private static ProgressDialog progressDialog;

    /**
     *
     * @param destination
     */

    public static void runActivity(Context mContext, Class destination){
        Intent i = new Intent(mContext,destination);
        mContext.startActivity(i);
    }
    /**
     * this method start a new activity parsing the extra with a bundle serializable object
     * @param destination
     * @param product
     * @Obs: Adaptar este metodo de acordo com a necessidade
     */
    public void runActivity(Class destination, Product product) {
        Intent intent = new Intent(mContext,destination);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }


    /**
     * this method start a new activity parsing the extra with a bundle serializable object
     * @param destination
     * @param listProducts
     * @Obs: Adaptar este metodo de acordo com a necessidade
     */
    public void runActivity(Class destination, List<Product> listProducts) {
        Intent intent = new Intent(mContext,destination);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listProducts", (Serializable) listProducts);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public static void showSnackbarMessage(Activity activity, String message){
        Snackbar.make(activity.findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG).show();
    }

    public static void showProgressDialog(Context mContext, String title, String message) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void stopProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    public static InputFilter removeWhiteSpaceFilterET(){
        return new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
    }
}
