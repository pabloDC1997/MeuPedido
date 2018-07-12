package com.app.devpai.meupedido.utils;

import android.util.Log;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Pablo on 26/03/2017.
 */


final public class FormatterUtil {

    public static final String DEGUB_TAG = FormatterUtil.class.getName();

    public static String getDateFromUnix(long unixTime, String format){
        try {
            Date date = new Date(unixTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("UTC -3:00")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            return formattedDate;
        }catch(Exception e) {
            Log.e(DEGUB_TAG,e.getMessage());
            return null;
        }
    }
    public static String getFormatMoneyFromDouble(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatter.format(value);
    }
}
