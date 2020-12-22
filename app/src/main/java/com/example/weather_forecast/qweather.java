package com.example.weather_forecast;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class qweather{

    private static final String TAG = "qweather";
    private static final String API_KEY = "15ad925746e7407aa085901c50cb7d5c";
    private String mlocation;

    qweather(String location){
        mlocation = location;
    }

    public List<GalleryItem>fetchItems(){
        List<GalleryItem>items = new ArrayList<>();
        Log.i("wea",mlocation);

        try{
            String url = Uri.parse("https://devapi.qweather.com/v7/weather/15d?")
                    .buildUpon()
                    .appendQueryParameter("key",API_KEY)
                    .appendQueryParameter("format","json")
                    .appendQueryParameter("nojsoncalllback","1")
                    .appendQueryParameter("location",mlocation)
                    .build().toString();
            String jsonString = getUrl.getUrlString(url);
            Log.i(TAG,"Received JSON:"+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items,jsonBody);
        }catch (IOException ioe){
            Log.e(TAG,"Failed to fetch items",ioe);
        }catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON",je);
        }
        Log.i("qw",items.get(0).getTextDay());
        return items;
    }
    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONArray weatherJsonArray = jsonBody.getJSONArray("daily");
        for(int i = 0;i<weatherJsonArray.length();i++){
            JSONObject weatherJsonObject = weatherJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setFxDate(weatherJsonObject.getString("fxDate"));
            item.setIconDay(weatherJsonObject.getString("iconDay"));
            item.setTextDay(weatherJsonObject.getString("textDay"));
            item.setIconNight(weatherJsonObject.getString("iconNight"));
            item.setTextNight(weatherJsonObject.getString("textNight"));
            item.setTempMax(weatherJsonObject.getString("tempMax"));
            item.setTempMin(weatherJsonObject.getString("tempMin"));
            item.setHumidity(weatherJsonObject.getString("humidity"));
            item.setPressure(weatherJsonObject.getString("pressure"));
            item.setWinddirday(weatherJsonObject.getString("windDirDay"));
            item.setWindspeedday(weatherJsonObject.getString("windSpeedDay"));
            items.add(item);
        }
    }
}
