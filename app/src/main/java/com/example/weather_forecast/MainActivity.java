package com.example.weather_forecast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String key = "com.example.weather_forecast.MainActivity";
    private boolean isTwopage;

    private Button msetting;
    private Button mlocation;

    private String location;
    private String Temperature_unit;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Data app = (Data)getApplication();
        app.setLocation("101010100");
        app.setNlocation("BeiJing");
        app.setTemperature_unit("C");
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Data app = (Data)getApplication();
        location=app.getLocation();
        Temperature_unit = app.getTemperature_unit();
        AutoReceiver.setServiceAlarm(this, app.isNotification());
        //检测是否为大设备
        if (findViewById(R.id.detail_large)!=null){
            isTwopage = true;
        }else {
            isTwopage = false;
        }
        FragmentManager fm = getSupportFragmentManager();


        //如果是平板
        if (isTwopage){
            Fragment fragment = fm.findFragmentById(R.id.menu_large);
            mToolbar = (Toolbar)findViewById(R.id.second_toolbar);
            setSupportActionBar(mToolbar);
            mToolbar.setTitle("");
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_setting:
                            Intent intent = new Intent(MainActivity.this, Setting.class);
                            startActivity(intent);
                            break;
                        case R.id.menu_map:
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
            if (fragment == null){
                fragment = createFragment2();
                fm.beginTransaction().add(R.id.menu_large,fragment).commit();
            }else {
                fragment = createFragment2();
                fm.beginTransaction().replace(R.id.menu_large, fragment).commit();
            }
            createFragment2();
        } else{    //如果是手机
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);
            if (fragment == null){
                fragment = createFragment1();
                fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
            } else {
                fragment = createFragment1();
                fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    protected Fragment createFragment1() {
        return firstFragment.newInstance(location,Temperature_unit);
    }
    protected Fragment createFragment2(){
        return secondFragment.newInstance(location,Temperature_unit);
    }

    public static Intent newIntent(Context context){
        return new Intent(context,MainActivity.class);
    }
}