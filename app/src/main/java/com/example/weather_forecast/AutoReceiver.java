package com.example.weather_forecast;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.net.ConnectivityManagerCompat;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class AutoReceiver extends IntentService {
    private static final String TAG = "AutoReceiver";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(15);
    private static final String key = "com.example.weather_forecast.MainActivity";

    private String location;
    private day1Item mDay1Item;

    public static Intent newIntent(Context context) {
        return new Intent(context, AutoReceiver.class);
    }

    public static void setServiceAlarm(Context context,boolean isOn){
        Intent i = AutoReceiver.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context,0,i,0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn){
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),POLL_INTERVAL_MS,pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public AutoReceiver() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "Received an intent:" + intent);
        String location;
        day1Item item = new day1Item();
        Data app = (Data)getApplication();
        location = app.getLocation();
        item = new qAuto(location).fetchday1();

        String Icon = item.getIcon();
        Class drawable =  R.drawable.class;
        int resId;
        String name = "s2"+Icon;
        try {
            Field field = drawable.getField(name);
            resId = field.getInt(name);
        }catch (NoSuchFieldException e){
            resId = 0;
        }catch (IllegalAccessException e){
            resId = 0;
        }

        Resources resources = getResources();
        Intent i = MainActivity.newIntent(this);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("淦，快过来看")
                .setSmallIcon(resId)
                .setContentTitle("Weather Forecast")
                .setContentText(item.getText()+" 温度："+item.getNow()+"℃")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0,notification);
    }
    private boolean inNetworkAvailableAndConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo()!=null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }

    public static boolean isServiceAlarmOn(Context context){
        Intent i = AutoReceiver.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context,0,i,PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }
}