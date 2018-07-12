package com.app.devpai.meupedido.utils;

import android.location.Location;

/**
 * Created by pablo.couto on 02/01/2018.
 */

public class LocationUtil {

    public static float getDistanceBetween(Location locA, Location locB){
        return  locA.distanceTo(locB);
    }

    public static  float getDistanceBetween(double longA, double latA, double longB, double latB){
        Location locA = new Location("pointA");
        Location locB = new Location("pointB");

        locA.setLongitude(longA);
        locA.setLatitude(latA);

        locB.setLongitude(longB);
        locB.setLatitude(latB);

        return  locA.distanceTo(locB);
    }

    public static Location getCurrentLocation(){
        //TODO - Implement this
        return null;
    }
}
