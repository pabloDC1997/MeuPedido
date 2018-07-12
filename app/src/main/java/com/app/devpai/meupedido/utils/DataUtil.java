package com.app.devpai.meupedido.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.InputMismatchException;

/**
 * Created by Pablo on 23/08/2017.
 */

public class DataUtil {
    public static boolean isCPF(String CPF) {
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public static Gson getGson(){
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Double.class, new TypeAdapter<Double>() {
            @Override
            public void write(JsonWriter writer, Double value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            }

            @Override
            public Double read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                String stringValue = reader.nextString().replace(",",".");
                try {
                    return Double.parseDouble(stringValue);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });
        gb.registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
            @Override
            public void write(JsonWriter writer, Integer value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            }

            @Override
            public Integer read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                String stringValue = reader.nextString().replace(",",".");
                try {
                    return Integer.parseInt(stringValue);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        });

        gb.registerTypeAdapter(Long.class, new TypeAdapter<Long>() {
            @Override
            public void write(JsonWriter writer, Long value) throws IOException {
                if (value == null) {
                    writer.nullValue();
                    return;
                }
                writer.value(value);
            }

            @Override
            public Long read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                String stringValue = reader.nextString();
                if(stringValue.contains(".")){
                    Double d = Double.parseDouble(stringValue);
                    Long value = d.longValue();
                    return value;
                }else{
                    try {
                        return Long.parseLong(stringValue);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }

            }
        });
        return gb.create();
    }

    public static boolean validateIfEmailIsValid(String email){
        if (email.length() < 5 || !email.contains("@")){
            return false;
        }

        int i = email.indexOf('@');
        String localPart = email.substring(0,i);
        String domain = email.substring(i+1, email.length());

        if (domain.length() < 4 || !domain.contains(".") || localPart.length() < 1){
            return false;
        }

        int ii = domain.indexOf('.');
        String domainBeforeDot = domain.substring(0,ii);
        String domainAfterDot = domain.substring(ii+1, domain.length());

        if(domainBeforeDot.length() <= 0 || domainAfterDot.length() < 2 || domainBeforeDot.length() < 2 ){
            return false;
        } else{
            return true;
        }
    }
}
