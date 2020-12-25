package com.example.weather_forecast;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.weather_forecast.DataItem.Data;
import com.example.weather_forecast.DataItem.Day1;
import com.example.weather_forecast.DataItem.day1Item;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class AutoReceiver extends IntentService {
    private static final String TAG = "AutoReceiver";
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1);
    private static final String key = "com.example.weather_forecast.MainActivity";

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

        String Icon = Day1.getIconDay();
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


        Intent i = MainActivity.newIntent(this);
        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            String channelId = "State";
            String channelName = "状态";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId,channelName,importance);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"default");

        mBuilder.setTicker("淦，快过来看")
                .setChannelId("State")
                .setSmallIcon(resId)
                .setContentTitle("Weather Forecast")
                .setContentText(Day1.getTextDay()+" 温度："+Day1.getTempMax()+"℃")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),resId))
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pi)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(10,mBuilder.build());
    }

    public static boolean isServiceAlarmOn(Context context){
        Intent i = AutoReceiver.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context,0,i,PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId,String channeLName,int importance){
        NotificationChannel channel = new NotificationChannel(channelId,channeLName,importance);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}