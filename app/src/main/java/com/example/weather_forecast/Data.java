package com.example.weather_forecast;

import android.app.Application;

import androidx.fragment.app.FragmentActivity;

public class Data extends Application {

    private String location="";

    private String nlocation="";

    private String Temperature_unit="";

    private boolean Notification = false;

    public boolean isNotification() {
        return Notification;
    }

    public void setNotification(boolean notification) {
        Notification = notification;
    }


    public String getTemperature_unit() {
        return Temperature_unit;
    }

    public void setTemperature_unit(String temperature_unit) {
        Temperature_unit = temperature_unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNlocation() {
        return nlocation;
    }

    public void setNlocation(String nlocation) {
        this.nlocation = nlocation;
    }
}
