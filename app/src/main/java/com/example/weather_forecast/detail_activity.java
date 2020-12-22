package com.example.weather_forecast;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class detail_activity extends AppCompatActivity {

    private String location;
    private String Temperature_unit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

    }

    @Override
    protected void onResume() {
        super.onResume();
        final Data app = (Data) getApplication();
        location = app.getLocation();
        Temperature_unit = app.getTemperature_unit();
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_detail);
        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_detail,fragment).commit();
        }else {
            fragment = createFragment();
            fm.beginTransaction().replace(R.id.fragment_detail, fragment).commit();
        }

    }


    protected Fragment createFragment() {
        return detail_activity_fragment.newInstance(location,Temperature_unit);
    }
}
