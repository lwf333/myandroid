package com.example.weather_forecast.DataItem;

import android.app.Application;

public class Day1{
    private static String textDay="";
    private static String iconDay="";
    private static String tempMax="";

    public static String getTextDay() {
        return textDay;
    }

    public static void setTextDay(String mtextDay) {
        textDay = mtextDay;
    }

    public static String getIconDay() {
        return iconDay;
    }

    public static void setIconDay(String miconDay) {
        iconDay = miconDay;
    }

    public static String getTempMax() {
        return tempMax;
    }

    public static void setTempMax(String mtempMax) {
        tempMax = mtempMax;
    }
}
