package com.example.weather_forecast.DateBase;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.weather_forecast.DateBase.cityDbSchema.citytable;

public class cityCursorWrapper extends CursorWrapper {
    public cityCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public String getLocation(){
        System.out.println(3);
        String location = getString(getColumnIndex(citytable.Cols.CITYID));
        return location;
    }
}
