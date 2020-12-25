package com.example.weather_forecast;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.weather_forecast.DataItem.Data;

public class detail_activity extends AppCompatActivity {

    private static final String EXTRA_POSITION = "com.example.weather_forecast.position";

    private String location;
    private String Temperature_unit;
    private int position;
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
        position = getIntent().getIntExtra(EXTRA_POSITION,0);
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
        return detail_activity_fragment.newInstance(location,Temperature_unit,position);
    }
}
