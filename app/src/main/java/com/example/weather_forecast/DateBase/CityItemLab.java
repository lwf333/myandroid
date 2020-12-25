package com.example.weather_forecast.DateBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.weather_forecast.DataItem.GalleryItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.weather_forecast.DateBase.cityDbSchema.*;

public class CityItemLab {
    private static ContentValues getcityContentValues(String location,String name){
        ContentValues values = new ContentValues();
        values.put(citytable.Cols.CITYID,location);
        values.put(citytable.Cols.CITYNAME,name);
        return values;
    }

    public static void addcityItem(String location,String name,SQLiteDatabase mDatabase){
        ContentValues values = getcityContentValues(location,name);
        mDatabase.insert(citytable.tcNAME,null,values);
    }

    public static void updatecityItem(String location,String name,SQLiteDatabase mDatabase){
        ContentValues values = getcityContentValues(location,name);
        mDatabase.update(citytable.tcNAME,values, citytable.Cols.CITYNAME+" = ?",new String[]{name});
    }

    public static cityCursorWrapper querycityyItem(String[]whereArgs, SQLiteDatabase mDatabase){
        Cursor cursor = mDatabase.query(
                citytable.tcNAME,
                null,
                citytable.Cols.CITYNAME+"=?",
                whereArgs,
                null,
                null,
                null
        );
        return new cityCursorWrapper(cursor);
    }

    public static boolean itemexist(String[]wereArgs,String key,SQLiteDatabase mDatabase){
        Cursor cursor = mDatabase.query(
                citytable.tcNAME,
                null,
                citytable.Cols.CITYID+"=?",
                wereArgs,
                null,
                null,
                null
        );
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                if (key.equals(cursor.getString(cursor.getColumnIndex(citytable.Cols.CITYID))))
                    return true;
                cursor.moveToNext();
            }
            return false;
        }finally {
            cursor.close();
        }
    }
    public static boolean cityexist(String[]wereArgs,String key,SQLiteDatabase mDatabase){
        Cursor cursor = mDatabase.query(
                citytable.tcNAME,
                null,
                citytable.Cols.CITYNAME+"=?",
                wereArgs,
                null,
                null,
                null
        );
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                if (key.equals(cursor.getString(cursor.getColumnIndex(citytable.Cols.CITYNAME))))
                    return true;
                cursor.moveToNext();
            }
            return false;
        }finally {
            cursor.close();
        }
    }

    public static String getmlocation(String name, SQLiteDatabase mDatabase){
        cityCursorWrapper cursorWrapper = querycityyItem(new String[]{name},mDatabase);
        cursorWrapper.moveToFirst();
        return cursorWrapper.getLocation();
    }
}
