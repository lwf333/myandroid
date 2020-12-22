package com.example.weather_forecast;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class qAuto {
    private static final String TAG = "qAuto";
    private static final String API_KEY = "15ad925746e7407aa085901c50cb7d5c";
    private String nlocation;
    qAuto(String location){
        nlocation = location;
    }

    public day1Item fetchday1(){
        day1Item item = new day1Item();
        try{
            String url = Uri.parse("https://devapi.qweather.com/v7/weather/now?")
                    .buildUpon()
                    .appendQueryParameter("key",API_KEY)
                    .appendQueryParameter("location",nlocation)
                    .appendQueryParameter("format","json")
                    .appendQueryParameter("nojsoncalllback","1")
                    .build().toString();
            String jsonString = getUrl.getUrlString(url);
            Log.i(TAG,"Received JSON:"+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONObject jsonObject = jsonBody.getJSONObject("now");
            item.setIcon(jsonObject.getString("icon"));
            item.setNow(jsonObject.getString("temp"));
            item.setText(jsonObject.getString("text"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
