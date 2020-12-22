package com.example.weather_forecast;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collection;

public class Intent_GalleryItem implements Parcelable {
    public Intent_GalleryItem(GalleryItem galleryItem){
        textDay = galleryItem.getTextDay();
        iconDay = galleryItem.getIconDay();
        textNight = galleryItem.getTextNight();
        iconNight = galleryItem.getIconNight();
        tempMax = galleryItem.getTempMax();
        tempMin = galleryItem.getTempMin();
        fxDate = galleryItem.getFxDate();
        humidity = galleryItem.getHumidity();
        pressure = galleryItem.getPressure();
        winddirday = galleryItem.getWinddirday();
        windspeedday =  galleryItem.getWindspeedday();
    }

    public Intent_GalleryItem() {
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(textDay);
        dest.writeString(iconDay);
        dest.writeString(iconNight);
        dest.writeString(textNight);
        dest.writeString(tempMax);
        dest.writeString(tempMin);
        dest.writeString(fxDate);
        dest.writeString(humidity);
        dest.writeString(pressure);
        dest.writeString(winddirday);
        dest.writeString(windspeedday);
        //必须按变量声明的顺序打包
    }

    public static final Parcelable.Creator<Intent_GalleryItem>CREATOR = new Parcelable.Creator<Intent_GalleryItem>(){
        @Override
        public Intent_GalleryItem createFromParcel(Parcel source) {
            Intent_GalleryItem intent_galleryItem = new Intent_GalleryItem();
            intent_galleryItem.setTextDay(source.readString());
            intent_galleryItem.setIconDay(source.readString());
            intent_galleryItem.setIconNight(source.readString());
            intent_galleryItem.setTextNight(source.readString());
            intent_galleryItem.setTempMax(source.readString());
            intent_galleryItem.setTempMin(source.readString());
            intent_galleryItem.setFxDate(source.readString());
            intent_galleryItem.setHumidity(source.readString());
            intent_galleryItem.setPressure(source.readString());
            intent_galleryItem.setWinddirday(source.readString());
            intent_galleryItem.setWindspeedday(source.readString());
            return intent_galleryItem;
        }

        @Override
        public Intent_GalleryItem[] newArray(int size) {
            return new Intent_GalleryItem[size];
        }
    };
}
