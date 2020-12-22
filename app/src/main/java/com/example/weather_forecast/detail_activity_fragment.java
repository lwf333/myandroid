package com.example.weather_forecast;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class detail_activity_fragment extends Fragment {
    private static final String EXTRA_POSITION = "com.example.weather_forecast.position";

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
    private int position;
    private Toolbar mToolbar;
    String mTemperature_unit;

    public static detail_activity_fragment newInstance(String location,String Temperature_unit){
        return new detail_activity_fragment(location, Temperature_unit);
    }

    detail_activity_fragment(String location,String Temperature_unit){
        mlocation = location;
        mTemperature_unit = Temperature_unit;
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
        new FetchdetailTask().execute();
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
            mGalleryItem =galleryItems.get(position);
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
                                sendMail("共享软件");
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
        email.setType("plain/text");
        String emailSubject = "共享软件";

        email.putExtra(Intent.EXTRA_EMAIL,"476029289@qq.com");
        email.putExtra(Intent.EXTRA_SUBJECT,emailSubject);
        email.putExtra(Intent.EXTRA_TEXT,emailBody);
        startActivityForResult(Intent.createChooser(email,"发邮件的软件"),1001);
    }

    private void sendSMS(){
        Uri smsToUri = Uri.parse("smsto:");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW,smsToUri);
        sendIntent.putExtra("address","17754731082");
        sendIntent.putExtra("sms_body","？？？");
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent,1002);
    }
}
