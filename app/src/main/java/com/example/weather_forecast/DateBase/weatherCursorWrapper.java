package com.example.weather_forecast.DateBase;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.weather_forecast.DateBase.weatherDbSchema.weatherdetailTable;
import com.example.weather_forecast.DataItem.GalleryItem;

public class weatherCursorWrapper extends CursorWrapper {
    public weatherCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public GalleryItem getGalleryItem(){
        //键
        String key = getString(getColumnIndex(weatherdetailTable.Cols.KEYID));
        //白天文字描述
        String textDay = getString(getColumnIndex(weatherdetailTable.Cols.TEXTDAY));
        //白天图标代码
        String iconDay = getString(getColumnIndex(weatherdetailTable.Cols.ICONDAY));
        //夜间图标
        String iconNight = getString(getColumnIndex(weatherdetailTable.Cols.ICONNIGHT));
        //夜间文字描述
        String textNight = getString(getColumnIndex(weatherdetailTable.Cols.TEXTNIGHT));
        //最高气温
        String tempMax = getString(getColumnIndex(weatherdetailTable.Cols.TEMPMAX));
        //最低气温
        String tempMin = getString(getColumnIndex(weatherdetailTable.Cols.TEMPMIN));
        //预报日期
        String fxDate = getString(getColumnIndex(weatherdetailTable.Cols.FXDATE));
        //湿度
        String humidity = getString(getColumnIndex(weatherdetailTable.Cols.HUMIDITY));
        //气压
        String pressure = getString(getColumnIndex(weatherdetailTable.Cols.PRESSURE));
        //风向
        String winddirday = getString(getColumnIndex(weatherdetailTable.Cols.WINDDIRDAY));
        //风速
        String windspeedday = getString(getColumnIndex(weatherdetailTable.Cols.WINDSPEEDDAY));

        GalleryItem galleryItem = new GalleryItem();
        galleryItem.setTextDay(textDay);
        galleryItem.setIconDay(iconDay);
        galleryItem.setIconNight(iconNight);
        galleryItem.setTextNight(textNight);
        galleryItem.setTempMax(tempMax);
        galleryItem.setTempMin(tempMin);
        galleryItem.setFxDate(fxDate);
        galleryItem.setHumidity(humidity);
        galleryItem.setPressure(pressure);
        galleryItem.setWinddirday(winddirday);
        galleryItem.setWindspeedday(windspeedday);

        return galleryItem;
    }

}
