/*
package com.example.weather_forecast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;

    private Boolean mHasQuit = false;
    private Handler mRequstHandler;
    private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();
    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> mThumbnailDownloadListener;
    private String mlocation;

    public interface ThumbnailDownloadListener<T>{
        void onThumbailDownloaded(T target, Bitmap thumbnail);
    }

    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener){
        mThumbnailDownloadListener = listener;
    }

    public ThumbnailDownloader(Handler responseHandler){
        super(TAG);
        mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mResponseHandler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD){
                    T target = (T)msg.obj;
                    Log.i(TAG, "Got a request for URL:" + mRequestMap.get(target));
                    handleRequest(target);
                }
            }
        };
    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }

    public void queueThumbnail(T target,String url){
        Log.i(TAG,"Got a URL:"+ url);

        if (url == "no"){
            mRequestMap.remove(target);
        }else {
            mRequestMap.put(target,url);
            mRequstHandler.obtainMessage(MESSAGE_DOWNLOAD,target)
                    .sendToTarget();
        }
    }

    public void clearQueue(){
        mResponseHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }

    private void handleRequest(final T target){
        try{
            final String url = mRequestMap.get(target);
            final Bitmap bitmap;
            if (url == null){
                return;
            } else {
                byte[] bitmapBytes = new qweather(mlocation).getUrlBytes(url);
                bitmap = BitmapFactory
                        .decodeByteArray(bitmapBytes,0,bitmapBytes.length);
                Log.i(TAG,"Bitmap created");
            }

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mRequestMap.get(target)!=url||mHasQuit){
                        return;
                    }
                    mRequestMap.remove(target);
                    mThumbnailDownloadListener.onThumbailDownloaded(target,bitmap);
                }
            });
        }catch (IOException ioe){
            Log.e(TAG,"图片下载失败",ioe);
        }
    }
}

 */
