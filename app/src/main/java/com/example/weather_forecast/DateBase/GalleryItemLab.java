package com.example.weather_forecast.DateBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.weather_forecast.DataItem.GalleryItem;

import java.util.ArrayList;
import java.util.List;

public class GalleryItemLab {

    private static ContentValues getContentValues(GalleryItem galleryItem, String location, int postion){
        ContentValues values = new ContentValues();
        values.put(weatherDbSchema.weatherdetailTable.Cols.KEYID,location+postion);
        values.put(weatherDbSchema.weatherdetailTable.Cols.FXDATE,galleryItem.getFxDate());
        values.put(weatherDbSchema.weatherdetailTable.Cols.ICONDAY,galleryItem.getIconDay());
        values.put(weatherDbSchema.weatherdetailTable.Cols.ICONNIGHT,galleryItem.getIconNight());
        values.put(weatherDbSchema.weatherdetailTable.Cols.TEXTDAY,galleryItem.getTextDay());
        values.put(weatherDbSchema.weatherdetailTable.Cols.TEXTNIGHT,galleryItem.getTextNight());
        values.put(weatherDbSchema.weatherdetailTable.Cols.TEMPMAX,galleryItem.getTempMax());
        values.put(weatherDbSchema.weatherdetailTable.Cols.TEMPMIN,galleryItem.getTempMin());
        values.put(weatherDbSchema.weatherdetailTable.Cols.HUMIDITY,galleryItem.getHumidity());
        values.put(weatherDbSchema.weatherdetailTable.Cols.PRESSURE,galleryItem.getPressure());
        values.put(weatherDbSchema.weatherdetailTable.Cols.WINDSPEEDDAY,galleryItem.getWindspeedday());
        values.put(weatherDbSchema.weatherdetailTable.Cols.WINDDIRDAY,galleryItem.getWinddirday());
        values.put(weatherDbSchema.weatherdetailTable.Cols.LOCATION,location);

        return values;
    }

    public static void addGalleryItem(GalleryItem galleryItem, String location, int position, SQLiteDatabase mDatabase){
        ContentValues values = getContentValues(galleryItem,location,position);
        System.out.println(position);
        mDatabase.insert(weatherDbSchema.weatherdetailTable.wdNAME,null,values);
        Log.i("插入","成功");
    }

    public static void updateGalleryItem(GalleryItem galleryItem,String location,int postion,SQLiteDatabase mDatabase){
        String key = location+postion;
        ContentValues values = getContentValues(galleryItem,location,postion);
        mDatabase.update(weatherDbSchema.weatherdetailTable.wdNAME,values, weatherDbSchema.weatherdetailTable.Cols.KEYID+" = ?",new String[]{key});
    }

    public static weatherCursorWrapper queryGalleryItem(String[]whereArgs, SQLiteDatabase mDatabase){
        Cursor cursor = mDatabase.query(
                weatherDbSchema.weatherdetailTable.wdNAME,
                null,
                weatherDbSchema.weatherdetailTable.Cols.LOCATION+"=?",
                whereArgs,
                null,
                null,
                null
        );
        return new weatherCursorWrapper(cursor);
    }

    public static boolean itemexist(String[]wereArgs,String key,SQLiteDatabase mDatabase){
        Cursor cursor = mDatabase.query(
                weatherDbSchema.weatherdetailTable.wdNAME,
                null,
                weatherDbSchema.weatherdetailTable.Cols.LOCATION+"=?",
                wereArgs,
                null,
                null,
                null
        );
        System.out.println(cursor.getCount());
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                if (key.equals(cursor.getString(cursor.getColumnIndex(weatherDbSchema.weatherdetailTable.Cols.KEYID))))
                    return true;
                cursor.moveToNext();
            }
            return false;
        }finally {
            cursor.close();
        }
    }

    public static List<GalleryItem> getmGalleryItems(String location,SQLiteDatabase mDatabase){
        List<GalleryItem> mgalleryItems = new ArrayList<>();
        weatherCursorWrapper cursorWrapper = queryGalleryItem(new String[]{location},mDatabase);
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                mgalleryItems.add(cursorWrapper.getGalleryItem());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return mgalleryItems;
    }
}
