package ru.practicum.ewmmainservice.utils;

import ru.practicum.ewmmainservice.models.location.Coordinates;

import static ru.practicum.ewmmainservice.utils.Constants.EARTH_RADIUS;

public class PointsOperations {

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static long getDistance(double lat1, double lon1, double lat2, double lon2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return Math.round(s * 10000) / 10000;
    }

    public static long getDistance(Coordinates location1, Coordinates location2) {
        return getDistance(location1.getLat(), location1.getLon(), location2.getLat(), location2.getLon());
    }

    private static float computeDelta(double degrees) {
        return (float) ((float) Math.PI / 180 * EARTH_RADIUS * Math.cos(deg2rad(degrees)));
    }

    private static double deg2rad(double degrees) {
        return degrees * Math.PI / 180;
    }

    public static float deltaLatLon(float latLon, long distance) {
        return distance / computeDelta(latLon);
    }
}

