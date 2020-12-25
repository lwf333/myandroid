package com.example.weather_forecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.city_picker.CityListActivity;
import com.example.weather_forecast.DataItem.Data;
import com.example.weather_forecast.DateBase.CityBaseHelper;
import com.example.weather_forecast.DateBase.CityItemLab;
import com.example.weather_forecast.DateBase.SQLBaseHelper;

public class Setting extends Activity {
    private String location;
    private String nlocation;
    private String mTemperature_unit;

    private LinearLayout setting_location;
    private LinearLayout setting_mTemperature_unit;
    private LinearLayout setting_notification;
    private TextView name_location;
    private TextView name_mTemperature_unit;
    private TextView name_notification;
    private Data mData;
    private SQLiteDatabase mDatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seting_layout);
        mDatabase = new CityBaseHelper(getApplicationContext()).getWritableDatabase();

        mData = (Data) getApplication();
        location = mData.getLocation();
        nlocation = mData.getNlocation();
        mTemperature_unit = mData.getTemperature_unit();
        setting_location = findViewById(R.id.setting_location);
        name_location = findViewById(R.id.city);
        name_location.setText(nlocation);
        setting_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityListActivity.startCityActivityForResult(Setting.this);
            }
        });
        setting_mTemperature_unit = findViewById(R.id.temperature_units);
        setting_mTemperature_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showunitPopMenu(setting_mTemperature_unit);
            }
        });

        setting_notification = findViewById(R.id.notifications);
        name_notification = (TextView) findViewById(R.id.notification);
        if (!mData.isNotification()) {
            name_notification.setText("close");
        } else {
            name_notification.setText("open");
        }
        setting_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mData.isNotification()) {
                        mData.setNotification(false);
                        name_notification.setText("close");
                    } else {
                        mData.setNotification(true);
                        name_notification.setText("open");
                    }
                }
        });
        name_location = (TextView) findViewById(R.id.city);
        name_location.setText(nlocation);

        name_mTemperature_unit = (TextView) findViewById(R.id.unit);
        if (mTemperature_unit.equals("C")) {
            name_mTemperature_unit.setText("摄氏度");
        } else {
            name_mTemperature_unit.setText("华氏度");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            String city = data.getStringExtra("city");
            if (isConnectIsNomarl()) {
                mData.setNlocation(city);
                name_location.setText(city);
                new FetchStringTask().execute();
            } else {
                if (CityItemLab.cityexist(new String[]{city},city,mDatabase)) {
                    location = CityItemLab.getmlocation(city, mDatabase);
                    mData.setLocation(location);
                    mData.setNlocation(city);
                    name_location.setText(city);
                }
            }
        }
    }


    private void showunitPopMenu(final View view) {
        final PopupMenu popupMenu = new PopupMenu(this, view);
        final Data mData = (Data) getApplication();

        popupMenu.getMenuInflater().inflate(R.menu.menu_unit, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.C:
                        mData.setTemperature_unit("C");
                        popupMenu.dismiss();
                        break;
                    case R.id.F:
                        mData.setTemperature_unit("F");
                        popupMenu.dismiss();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                if (mData.getTemperature_unit().equals("C")) {
                    name_mTemperature_unit.setText("摄氏度");
                } else {
                    name_mTemperature_unit.setText("华氏度");
                }
            }
        });
        popupMenu.show();
    }

    class FetchStringTask extends AsyncTask<Void, Void, String> {
        final Data mData = (Data) getApplication();
        String location;

        @Override
        protected String doInBackground(Void... voids) {
            location = new qcity(mData.getNlocation()).fetchcityid();
            mData.setLocation(location);
            if (!CityItemLab.itemexist(new String[]{location}, location, mDatabase))
                CityItemLab.addcityItem(location, mData.getNlocation(), mDatabase);
            return null;
        }
    }
    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String intentName = info.getTypeName();
            Log.i("通了没！", "当前网络名称：" + intentName);
            return true;
        } else {
            Log.i("通了没！", "没有可用网络");
            return false;
        }
    }
}
