package com.app.devpai.meupedido.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Pablo on 13/05/2017.
 */

public abstract class MaskEditable {
    private static final String PHONE_8 = "####-####";
    private static final String PHONE_9 = "# ####-####";

    private static String unMask(String s) {
        return s.replaceAll("[^0-9]*", "");
    }

    public static TextWatcher insert(final EditText editText, final String typeMaskParam) {
        final String maskParam = typeMaskParam;

        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = MaskEditable.unMask(s.toString());
                String mask;
                mask = maskParam;

                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                        mascara += m;
                        continue;
                    }

                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                editText.setText(mascara);
                editText.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {}
        };
    }

//    private static String typeMask(String maskParam) {
//        if (!maskParam.equals(Mask.PHONE)){
//            switch (maskParam.length()){
//                case 8:
//                    return PHONE_8;
//                case 9:
//                    return PHONE_9;
//                case 10:
//                    return PHONE_10;
//                case 11:
//                    return PHONE_11;
//                default:
//                    return maskParam;
//            }
//
//        } else {
//            return maskParam;
//        }
//    }
}
