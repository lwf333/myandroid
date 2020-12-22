package com.example.weather_forecast;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seting_layout);

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
                showcityPopupMenu(setting_location);
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
        name_notification = (TextView)findViewById(R.id.notification);
        if (!mData.isNotification()){
            name_notification.setText("close");
        } else {
            name_notification.setText("open");
        }
        setting_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData.isNotification()){
                    mData.setNotification(false);
                    name_notification.setText("close");
                } else {
                    mData.setNotification(true);
                    name_notification.setText("open");
                }
            }
        });
        name_location = (TextView)findViewById(R.id.city);
        name_location.setText(nlocation);

        name_mTemperature_unit = (TextView)findViewById(R.id.unit);
        if (mTemperature_unit.equals("C")){
            name_mTemperature_unit.setText("摄氏度");
        } else {
            name_mTemperature_unit.setText("华氏度");
        }
    }

    private void showcityPopupMenu(final View view){
        final PopupMenu popupMenu = new PopupMenu(this,view);
        final Data mData = (Data)getApplication();

        popupMenu.getMenuInflater().inflate(R.menu.menu_setting,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.BeiJing:
                        mData.setNlocation("BeiJing");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.ShangHai:
                        mData.setNlocation("ShangHai");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.TianJing:
                        mData.setNlocation("TianJing");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.ChongQing:
                        mData.setNlocation("ChongQing");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.HaErBin:
                        mData.setNlocation("HaErBin");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.Changchun:
                        mData.setNlocation("Changchun");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.Shenyang:
                        mData.setNlocation("Shenyang");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.HuHeHaoTe:
                        mData.setNlocation("HuHeHaoTe");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.ShiJiaZhuang:
                        mData.setNlocation("ShiJiaZhuang");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.WuLuMuQi:
                        mData.setNlocation("WuLuMuQi");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.LanZhou:
                        mData.setNlocation("LanZhou");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.XiNing:
                        mData.setNlocation("XiNing");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.XiAn:
                        mData.setNlocation("XiAn");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.YinChuan:
                        mData.setNlocation("YinChuan");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.ZhengZhou:
                        mData.setNlocation("ZhengZhou");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.JiNan:
                        mData.setNlocation("JiNan");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.TaiYuan:
                        mData.setNlocation("TaiYuan");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.HeFei:
                        mData.setNlocation("HeFei");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.WuHan:
                        mData.setNlocation("WuHan");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.ChangSha:
                        mData.setNlocation("ChangSha");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.NanJing:
                        mData.setNlocation("NanJing");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.ChengDu:
                        mData.setNlocation("ChengDu");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.GuiYang:
                        mData.setNlocation("GuiYang");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.KunMing:
                        mData.setNlocation("KunMing");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.NanNing:
                        mData.setNlocation("NanNing");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.LaSa:
                        mData.setNlocation("LaSa");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.HangZhou:
                        mData.setNlocation("HangZhou");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.NanChang:
                        mData.setNlocation("NanChang");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.GuangZhou:
                        mData.setNlocation("GuangZhou");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.FuZhou:
                        mData.setNlocation("FuZhou");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.TaiBei:
                        mData.setNlocation("TaiBei");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.HaiKou:
                        mData.setNlocation("HaiKou");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.XiangGang:
                        mData.setNlocation("XiangGang");
                        new FetchStringTask().execute();
                        popupMenu.dismiss();
                        break;
                    case R.id.AoMen:
                        mData.setNlocation("AoMen");
                        new FetchStringTask().execute();
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
                nlocation = mData.getNlocation();
                name_location.setText(nlocation);
            }
        });
        popupMenu.show();
    }

    private void showunitPopMenu(final View view){
        final PopupMenu popupMenu = new PopupMenu(this,view);
        final Data mData = (Data)getApplication();

        popupMenu.getMenuInflater().inflate(R.menu.menu_unit,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
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
                if (mData.getTemperature_unit().equals("C")){
                    name_mTemperature_unit.setText("摄氏度");
                } else {
                    name_mTemperature_unit.setText("华氏度");
                }
            }
        });
        popupMenu.show();
    }

    class FetchStringTask extends AsyncTask<Void,Void, String> {
        final Data mData = (Data)getApplication();
        String location;
        @Override
        protected String doInBackground(Void... voids) {
            location = new qcity(mData.getNlocation()).fetchcityid();
            mData.setLocation(location);
            Log.i("setid",location);
            return null;
        }
    }

}
