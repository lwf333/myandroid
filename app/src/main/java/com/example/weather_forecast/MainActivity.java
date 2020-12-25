package com.example.weather_forecast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.weather_forecast.DataItem.Data;
import com.example.weather_forecast.DateBase.CityBaseHelper;
import com.example.weather_forecast.DateBase.CityItemLab;

public class MainActivity extends AppCompatActivity {
    private static final String key = "com.example.weather_forecast.MainActivity";
    private boolean isTwopage;

    private Button msetting;
    private Button mlocation;

    private String location;
    private String Temperature_unit;
    private Toolbar mToolbar;
    private SQLiteDatabase mDatabase;

    private static final String STRATCITY = "北京";
    private static final String STRATLOCATION = "101010100";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = new CityBaseHelper(getApplicationContext()).getWritableDatabase();

        if (!CityItemLab.cityexist(new String[]{STRATCITY},STRATCITY,mDatabase)){
            CityItemLab.addcityItem(STRATLOCATION,STRATCITY,mDatabase);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Data app = (Data)getApplication();
        if(app.getLocation().equals("")){
            app.setLocation(STRATLOCATION);
            app.setNlocation(STRATCITY);
            app.setTemperature_unit("C");
        }
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
                            String encodeName = Uri.encode(app.getNlocation());
                            Uri locationUri = Uri.parse("geo:0,0?q="+encodeName);
                            Intent intent1 = new Intent(Intent.ACTION_VIEW);
                            Intent chooser = Intent.createChooser(intent1,"请选择地图软件");
                            intent1.setData(locationUri);
                            if (intent1.resolveActivity(getPackageManager())!=null){
                                startActivity(chooser);
                            }
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