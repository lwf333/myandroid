package com.example.weather_forecast;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Collection;

public class detail_fragment extends Fragment {
    detail_fragment(){

    }
    private String mTemperature_unit;
    private GalleryItem mGalleryItem;

    private TextView mweek;
    private TextView mdate;
    private TextView mmax;
    private TextView mmin;
    private TextView mweather;
    private TextView mhuidity;
    private TextView mpressure;
    private TextView mwind;
    private ImageView mImageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_item_view,container,false);
        mweek = (TextView)view.findViewById(R.id.detail_week_large);
        mdate = (TextView)view.findViewById(R.id.detail_date_large);
        mmax = (TextView)view.findViewById(R.id.detail_max_large);
        mmin = (TextView)view.findViewById(R.id.detail_min_large);
        mweather = (TextView)view.findViewById(R.id.detail_weather_large);
        mhuidity = (TextView)view.findViewById(R.id.detail_humidity_large);
        mpressure = (TextView)view.findViewById(R.id.detail_pressure_large);
        mwind = (TextView)view.findViewById(R.id.detail_wind_large);
        mImageView = (ImageView)view.findViewById(R.id.detail_image_large);
        String date = null;
        String weekday = null;
        String time= null;
        date = mGalleryItem.getFxDate();
        try {
            if (Time.IsToday(date)){
                weekday = "Today";
            } else if (Time.IsTomorrow(date)){
                weekday = "Tomorrow";
            } else {
                weekday = Time.StringToWeek(date);
            }
            time = Time.DateChange(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mweek.setText(weekday);
        mdate.setText(time);

        String max;
        String min;
        if (mTemperature_unit.equals("C")){
            max =mGalleryItem.getTempMax()+"℃";
            min = mGalleryItem.getTempMin()+"℃";
        } else {
            max = (int) (Integer.parseInt(mGalleryItem.getTempMax()) * 1.8 + 32) +"℉";
            min = (int) (Integer.parseInt(mGalleryItem.getTempMin()) * 1.8 + 32) +"℉";
        }

        mmax.setText(max);
        mmin.setText(min);
        mweather.setText(mGalleryItem.getTextDay());
        mhuidity.setText("Humidity: "+mGalleryItem.getHumidity()+" %");
        mpressure.setText("Pressure: "+mGalleryItem.getPressure()+" hPa");
        mwind.setText("Wind: "+mGalleryItem.getWindspeedday()+" km/h  "+mGalleryItem.getWinddirday());

        String Icon = mGalleryItem.getIconDay();
        String name = "s2"+Icon;
        Class drawable = R.drawable.class;
        int resId;
        try {
            Field field = drawable.getField(name);
            resId = field.getInt(name);
        }catch (NoSuchFieldException e){
            resId = 0;
        }catch (IllegalAccessException e){
            resId = 0;
        }
        Drawable mdrawable  = getResources().getDrawable(resId);

        mImageView.setImageDrawable(mdrawable);

        return view;
    }



    detail_fragment(GalleryItem galleryItem, String Temperature_unit){
        mGalleryItem = galleryItem;
        mTemperature_unit = Temperature_unit;
    }
}
