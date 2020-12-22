package com.example.weather_forecast;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;

public class GalleryItem{
    public String getIconDay() {
        return iconDay;
    }

    public String getTextDay() {
        return textDay;
    }

    public String getIconNight() {
        return iconNight;
    }

    public String getTextNight() {
        return textNight;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getFxDate() {
        return fxDate;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWinddirday() {
        return winddirday;
    }

    public String getWindspeedday() {
        return windspeedday;
    }

    public void setIconDay(String iconDay) {
        this.iconDay = iconDay;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    public void setIconNight(String iconNight) {
        this.iconNight = iconNight;
    }

    public void setTextNight(String textNight) {
        this.textNight = textNight;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setWinddirday(String winddirday) {
        this.winddirday = winddirday;
    }

    public void setWindspeedday(String windspeedday) {
        this.windspeedday = windspeedday;
    }
    //白天文字描述
    private String textDay;
    //白天图标代码
    private String iconDay;
    //夜间图标
    private String iconNight;
    //夜间文字描述
    private String textNight;
    //最高气温
    private String tempMax;
    //最低气温
    private String tempMin;
    //预报日期
    private String fxDate;
    //湿度
    private String humidity;
    //气压
    private String pressure;
    //风向
    private String winddirday;
    //风速
    private String windspeedday;

}
