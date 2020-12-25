package com.example.weather_forecast;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.weather_forecast.DataItem.Data;
import com.example.weather_forecast.DataItem.GalleryItem;
import com.example.weather_forecast.DateBase.GalleryItemLab;
import com.example.weather_forecast.DateBase.SQLBaseHelper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class detail_activity_fragment extends Fragment {
    private static final String EXTRA_POSITION = "com.example.weather_forecast.position";

    private SQLiteDatabase mDatebase;

    private List<GalleryItem> mItems = new ArrayList<>();
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
    private String mlocation;
    private int mposition;
    private Toolbar mToolbar;
    String mTemperature_unit;

    public static detail_activity_fragment newInstance(String location,String Temperature_unit,int position){
        return new detail_activity_fragment(location, Temperature_unit,position);
    }

    detail_activity_fragment(String location,String Temperature_unit,int position){
        mlocation = location;
        mTemperature_unit = Temperature_unit;
        mposition = position;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_item_view,container,false);

        mToolbar = (Toolbar)view.findViewById(R.id.detail_item_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        getActivity().setTitle("");

        mweek = (TextView)view.findViewById(R.id.detail_week);
        mdate = (TextView)view.findViewById(R.id.detail_date);
        mmax = (TextView)view.findViewById(R.id.detail_max);
        mmin = (TextView)view.findViewById(R.id.detail_min);
        mweather = (TextView)view.findViewById(R.id.detail_weather);
        mhuidity = (TextView)view.findViewById(R.id.detail_humidity);
        mpressure = (TextView)view.findViewById(R.id.detail_pressure);
        mwind = (TextView)view.findViewById(R.id.detail_wind);
        mImageView = (ImageView) view.findViewById(R.id.detail_image);
        if (isConnectIsNomarl()) {
            new FetchdetailTask().execute();
        } else {
            mDatebase = new SQLBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
            List<GalleryItem> galleryItems;
            galleryItems = GalleryItemLab.getmGalleryItems(mlocation,mDatebase);
            mGalleryItem = galleryItems.get(mposition);
            postview();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                showShareDialog();
                return true;
            case R.id.detail_menu_setting:
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
                return true;
            case R.id.detail_menu_map:
                final Data app = (Data) getActivity().getApplication();
                String encodeName = Uri.encode(app.getNlocation());
                Uri locationUri = Uri.parse("geo:0,0?q="+encodeName);
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                Intent chooser = Intent.createChooser(intent1,"请选择地图软件");
                intent1.setData(locationUri);
                if (intent1.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivity(chooser);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class FetchdetailTask extends AsyncTask<Void,Void,List<GalleryItem>> {
        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            return new qweather(mlocation).fetchItems();
        }
        @Override
        protected void onPostExecute(List<GalleryItem> galleryItems) {
            mGalleryItem =galleryItems.get(mposition);
            postview();
        }
    }
    public void postview(){
        String date = null;
        String weekday = null;
        String time = null;
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
            max =  mGalleryItem.getTempMax()+"℃";
            min =  mGalleryItem.getTempMin()+"℃";
        } else {
            max = (int) (Integer.parseInt( mGalleryItem.getTempMax()) * 1.8 + 32) +"℉";
            min = (int) (Integer.parseInt( mGalleryItem.getTempMin()) * 1.8 + 32) +"℉";
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
    }

    private void showShareDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("选择分享类型");
        builder.setItems(new String[]{"邮件", "短信"},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which){
                            case 0:
                                sendMail("超级无敌天气预报分享");
                                break;
                            case 1:
                                sendSMS();
                                break;
                        }
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void sendMail(String emailBody){
        Intent email = new Intent(Intent.ACTION_SEND);
        String date = mGalleryItem.getFxDate();
        String max = mGalleryItem.getTempMax()+"℃";;
        String min = mGalleryItem.getTempMin()+"℃";
        String humidity = mGalleryItem.getHumidity()+"%";
        String pressure = mGalleryItem.getPressure()+"hPa";
        String windspeed = mGalleryItem.getWindspeedday()+"km/h";
        String winddir = mGalleryItem.getWinddirday();

        email.setType("plain/text");
        String emailSubject =
                "日期："+date+"\n"
                +"最高温度："+max+"\n"
                +"最低温度:"+min+"\n"
                +"空气湿度:"+humidity+"\n"
                +"大气压:"+pressure+"\n"
                +"风速:"+windspeed+"\n"
                +"风向:"+winddir+"\n";

        email.putExtra(Intent.EXTRA_EMAIL,"");
        email.putExtra(Intent.EXTRA_SUBJECT,emailSubject);
        email.putExtra(Intent.EXTRA_TEXT,emailBody);
        startActivityForResult(Intent.createChooser(email,"发邮件的软件"),1001);
    }

    private void sendSMS(){
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW,smsToUri);
        String date = mGalleryItem.getFxDate();
        String max = mGalleryItem.getTempMax()+"℃";;
        String min = mGalleryItem.getTempMin()+"℃";
        String humidity = mGalleryItem.getHumidity()+"%";
        String pressure = mGalleryItem.getPressure()+"hPa";
        String windspeed = mGalleryItem.getWindspeedday()+"km/h";
        String winddir = mGalleryItem.getWinddirday();
        String smsbody =
                "日期："+date+"\n"
                        +"最高温度："+max+"\n"
                        +"最低温度:"+min+"\n"
                        +"空气湿度:"+humidity+"\n"
                        +"大气压:"+pressure+"\n"
                        +"风速:"+windspeed+"\n"
                        +"风向:"+winddir+"\n";
        sendIntent.putExtra("address","");
        sendIntent.putExtra("sms_body",smsbody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent,1002);
    }

    private boolean isConnectIsNomarl() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
