package com.example.weather_forecast.DateBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.weather_forecast.DateBase.weatherDbSchema.*;

public class SQLBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "weather.db";

    public SQLBaseHelper(Context context){
        super(context,DATABASE_NAME,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ weatherdetailTable.wdNAME+"("+
                "id integer primary key autoincrement"+","+
                weatherdetailTable.Cols.KEYID+","+
                weatherdetailTable.Cols.FXDATE+","+
                weatherdetailTable.Cols.ICONDAY+","+
                weatherdetailTable.Cols.ICONNIGHT+","+
                weatherdetailTable.Cols.TEXTDAY+","+
                weatherdetailTable.Cols.TEXTNIGHT+","+
                weatherdetailTable.Cols.TEMPMAX+","+
                weatherdetailTable.Cols.TEMPMIN+","+
                weatherdetailTable.Cols.HUMIDITY+","+
                weatherdetailTable.Cols.PRESSURE+","+
                weatherdetailTable.Cols.WINDSPEEDDAY+","+
                weatherdetailTable.Cols.WINDDIRDAY+","+
                weatherdetailTable.Cols.LOCATION+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
