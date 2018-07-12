package com.app.devpai.meupedido.utils;

import android.util.Log;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by pablo.couto on 01/08/2017.
 */

public class DateUtil {
    public static final String DEGUB_TAG = DateUtil.class.getName();

    /**
     * Remove dias de uma data
     * @param date data de entrada
     * @param days dias a ser romovido da data de entrada
     * @return date - days
     */
    public static Date removeDaysFromDates(LocalDate date, Integer days) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date.toDate());
        gc.set(Calendar.DATE, gc.get(Calendar.DATE) - days);
        return (Date) gc.getTime();
    }

    /**
     *
     * @param unixTime
     * @param format ex: "YYYY-MM-dd HH::mm:ss"
     * @return String with the date formatted
     */
    public static String getDateFromUnix(long unixTime, String format){
        try {
            Date date = new Date(unixTime);
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC -3:00"));
            String formattedDate = sdf.format(date);
            return formattedDate;
        }catch(Exception e) {
            Log.e(DEGUB_TAG,e.getMessage());
            return null;
        }
    }

    public static String getDate(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

    public static int getDayBetweenTowDatesInFormatString(String dateInitial, String dateFinal){
        DateTimeFormatter dateFomatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        final LocalDate dinicial = LocalDate.parse(dateInitial, dateFomatter);
        LocalDate dfinal  = LocalDate.parse(dateFinal, dateFomatter);
        return Days.daysBetween(dinicial, dfinal).getDays();
    }

    public static boolean validateDateIsBiggerThanNow(String dataSt) {

        String daySt = dataSt.substring(0,2);
        String monthSt = dataSt.substring(3,5);

        if (Integer.parseInt(daySt) <= 0 || Integer.parseInt(daySt) > 31) {
            return false;
        }

        if (Integer.parseInt(monthSt) <= 0 || Integer.parseInt(daySt) > 12) {
            return false;
        }

        LocalDate dateNow = LocalDate.now();
        SimpleDateFormat sf = new SimpleDateFormat("dd/mm/yyyy",new Locale("pt", "BR"));
        String dateNowSt = sf.format(dateNow.toDate());
        int day = DateUtil.getDayBetweenTowDatesInFormatString(dataSt,dateNowSt);
        if (day <= 0){
            return false;
        }
        return true;
    }
}
