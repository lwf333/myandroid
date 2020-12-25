package com.example.weather_forecast.DateBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.weather_forecast.DateBase.cityDbSchema.citytable;

public class CityBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "city.db";

    public CityBaseHelper(Context context){
        super(context,DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ citytable.tcNAME+"("+
                "id integer primary key autoincrement"+","+
                citytable.Cols.CITYID+","+
                citytable.Cols.CITYNAME+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
