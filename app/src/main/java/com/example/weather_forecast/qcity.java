package com.example.weather_forecast;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class qcity {
    private static final String TAG = "qcity";
    private static final String API_KEY = "15ad925746e7407aa085901c50cb7d5c";
    private String nlocation;

    qcity(String location){
        nlocation = location;
    }

    public String fetchcityid(){
        String id = null;
        try{
            String url = Uri.parse("https://geoapi.qweather.com/v2/city/lookup?")
                    .buildUpon()
                    .appendQueryParameter("key",API_KEY)
                    .appendQueryParameter("location",nlocation)
                    .appendQueryParameter("format","json")
                    .appendQueryParameter("nojsoncalllback","1")
                    .build().toString();
            String jsonString = getUrl.getUrlString(url);
            Log.i(TAG,"Received JSON:"+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONArray cityJsonArray = jsonBody.getJSONArray("location");
            JSONObject cityJsonObject = cityJsonArray.getJSONObject(0);
            id = cityJsonObject.getString("id");
            Log.i("id",id);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return id;
    }
}
