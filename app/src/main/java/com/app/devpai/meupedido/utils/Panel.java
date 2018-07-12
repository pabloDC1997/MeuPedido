package com.app.devpai.meupedido.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.app.devpai.meupedido.callbacks.CallbackAlertDialog;
import com.app.devpai.meupedido.callbacks.CallbackAlertDialogWithED;

/**
 * Created by Pablo on 20/02/2017.
 */

/**
 * Essa classe gera panel(telas) dinamicas
 */
public class Panel {

    /**
     *
     * @param mContext
     * @param title
     * @param message
     * @param btnPositive
     * @param btnNegative
     * @param callback
     * @return PANEL COM DOIS BOTÕES
     */
        public static AlertDialog alertPanelSingleButton(Context mContext, String title,
                                                         String message, String btnPositive,
                                                         String btnNegative, CallbackAlertDialog callback) {

            final CallbackAlertDialog mCallback = callback;
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle(title)
                    .setMessage(message)

                    .setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mCallback.onPositiveButtonPressed();
                        }
                    })

                    .setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mCallback.onNegativeButtonPressed();
                        }
                    });

            AlertDialog dialog = builder.create();
            return dialog;

        }

    /**
     *
     * @param mContext
     * @param title
     * @param message
     * @param btnPositive
     * @param callback
     * @return PANEL COM UM  BOTÃO
     */
    public static AlertDialog alertPanelSingleButton(Context mContext, String title,
                                                     String message, String btnPositive,
                                                     CallbackAlertDialog callback) {

        final CallbackAlertDialog mCallback = callback;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)

                .setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCallback.onPositiveButtonPressed();
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;

    }

    /**
     *
     * @param mContext
     * @param title
     * @param message
     * @param btnPositive
     * @param btnNegative
     * @param callback
     * @return PANEL COM DOIS BOTÕES E UM CAMPO DE TESTO (EDITTEXT)
     */
    public static AlertDialog alertPanelWithED(Context mContext, String title,
                                         String message, String btnPositive,
                                         String btnNegative, CallbackAlertDialogWithED callback) {

        final CallbackAlertDialogWithED mCallback = callback;

        final EditText editText = new EditText(mContext);
        editText.setMaxLines(5);
        editText.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setView(editText)

                .setPositiveButton(btnPositive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String response =  editText.getText().toString();
                        mCallback.onPositiveButtonPressed(response);

                    }
                })

                .setNegativeButton(btnNegative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mCallback.onNegativeButtonPressed();
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }

}
