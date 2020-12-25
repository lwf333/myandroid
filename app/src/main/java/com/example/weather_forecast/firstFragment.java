package com.example.weather_forecast;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.fragment.app.Fragment;

import com.example.weather_forecast.DataItem.Data;
import com.example.weather_forecast.DataItem.Day1;
import com.example.weather_forecast.DataItem.GalleryItem;
import com.example.weather_forecast.DateBase.GalleryItemLab;
import com.example.weather_forecast.DateBase.SQLBaseHelper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class firstFragment extends Fragment {
    private static final String TAG = "firstFragment";
    private static final String EXTRA_POSITION = "com.example.weather_forecast.position";

    private RecyclerView mfirstRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();

    private String mlocation;
    private String mTemperature_unit;
    private Toolbar mToolbar;
    private SQLiteDatabase mDatabase;

    //private ThumbnailDownloader<weatherHolder>mThumbnailDownloader;
    public interface  OnRecyclerItemClickListener{
        void onItemClick(int position,List<GalleryItem>galleryItems);
    }

    public static firstFragment newInstance(String location,String Temperature_unit){
        return new firstFragment(location, Temperature_unit);
    }
    firstFragment(String location,String Temperature_unit){
        mlocation = location;
        mTemperature_unit = Temperature_unit;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDatabase = new SQLBaseHelper(getActivity().getApplicationContext()).getWritableDatabase();
        if (isConnectIsNomarl()) {
            new FetchIemsTask().execute();
        } else {
            mItems = GalleryItemLab.getmGalleryItems(mlocation,mDatabase);
            setupAdapter();
        }
        setHasOptionsMenu(true);
        //Handler responseHandler = new Handler();
        //mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        /*mThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<weatherHolder>() {
                    @Override
                    public void onThumbailDownloaded(weatherHolder target, Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(),bitmap);
                        target.bindDrawbable(drawable);
                    }
                }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();

         */
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_gallery,container,false);

        mToolbar = (Toolbar)v.findViewById(R.id.first_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        getActivity().setTitle("");

        mfirstRecyclerView = (RecyclerView) v.findViewById(R.id.weather_recycler_view);
        mfirstRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        setupAdapter();
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting:
                Intent intent = new Intent(getActivity(), Setting.class);
                startActivity(intent);
                return true;
            case R.id.menu_map:
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mThumbnailDownloader.clearQueue();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        //mThumbnailDownloader.quit();
    }

    private void setupAdapter(){
        if (isAdded()){
            weatherAdapter adapter = new weatherAdapter((mItems));
            adapter.setOnItemClickListener(new OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(int position, List<GalleryItem> galleryItems) {
                    Intent intent = new Intent(getActivity(), detail_activity.class);
                    intent.putExtra(EXTRA_POSITION,position);
                    startActivity(intent);
                }
            });
            mfirstRecyclerView.setAdapter(adapter);
        }
    }

    private class weatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private final int TODAY = 0;
        private final int OTHER = 1;

        private List<GalleryItem> mGalleryItems;

        public weatherAdapter(List<GalleryItem> galleryItems){
            mGalleryItems = galleryItems;
        }

        private OnRecyclerItemClickListener mOnItemClickListener;
        public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view;
            if (viewType == TODAY){
                view = LayoutInflater.from(getActivity()).inflate(R.layout.day1_item_gallery,viewGroup,false);
                return new day1Holder(view);
            }
            else {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_gallery,viewGroup,false);
                return new weatherHolder(view);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == TODAY){
                return TODAY;
            }
            else {
                return OTHER;
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            String Icon = galleryItem.getIconDay();
            Class drawable =  R.drawable.class;
            int resId;
            if (holder instanceof day1Holder){
                String name = "s2"+Icon;
                try {
                    Field field = drawable.getField(name);
                    resId = field.getInt(name);
                }catch (NoSuchFieldException e){
                    resId = 0;
                }catch (IllegalAccessException e){
                    resId = 0;
                }
                Drawable placeholder  = getResources().getDrawable(resId);
                day1Holder viewHolder = (day1Holder)holder;
                try {
                    viewHolder.bindGalleryItem(galleryItem);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                viewHolder.bindDrawbable(placeholder);
            }
            else{
                String name = "s1"+Icon;
                try {
                    Field field = drawable.getField(name);
                    resId = field.getInt(name);
                }catch (NoSuchFieldException e){
                    resId = 0;
                }catch (IllegalAccessException e){
                    resId = 0;
                }
                Drawable placeholder  = getResources().getDrawable(resId);
                weatherHolder viewHolder = (weatherHolder)holder;
                try {
                    viewHolder.bindGalleryItem(galleryItem);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                viewHolder.bindDrawbable(placeholder);
            }
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }

        class day1Holder extends RecyclerView.ViewHolder{
            private ImageView mItemImageView;
            private TextView mItemWeekView;
            private TextView mItemDateView;
            private TextView mItemweatherView;
            private TextView mItemMaxView;
            private TextView mItemMinView;
            private Button mMaplocation;
            private Button mSetting;
            public day1Holder(View itemView){
                super(itemView);
                mItemImageView = (ImageView)itemView.findViewById(R.id.first_image_view);
                mItemWeekView = (TextView)itemView.findViewById(R.id.week);
                mItemDateView = (TextView)itemView.findViewById(R.id.date);
                mItemweatherView = (TextView)itemView.findViewById(R.id.first_weather_view);
                mItemMaxView = (TextView)itemView.findViewById(R.id.maxtemperature);
                mItemMinView = (TextView)itemView.findViewById(R.id.mintemperature);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(getAdapterPosition(),mGalleryItems);
                        }
                    }
                });
            }

            public void bindGalleryItem(GalleryItem galleryItem) throws ParseException {
                Day1.setIconDay(galleryItem.getIconDay());
                Day1.setTempMax(galleryItem.getTempMax());
                Day1.setTextDay(galleryItem.getTextDay());
                String date = galleryItem.getFxDate();
                String week = null;
                week = Time.StringToWeek(date);
                mItemWeekView.setText(week);

                String day = null;
                String time = null;

                if (Time.IsToday(date)){
                    day = "Today";
                } else if (Time.IsTomorrow(date)){
                    day = "Tomorrow";
                } else {
                    day = Time.StringToWeek(date);
                }

                time =  Time.DateChange(date);
                mItemDateView.setText(day+","+time);
                mItemweatherView.setText(galleryItem.getTextDay());

                String max;
                String min;
                if (mTemperature_unit.equals("C")){
                    max = galleryItem.getTempMax()+"℃";
                    min = galleryItem.getTempMin()+"℃";
                } else {
                    max = (int) (Integer.parseInt(galleryItem.getTempMax()) * 1.8 + 32) +"℉";
                    min = (int) (Integer.parseInt(galleryItem.getTempMin()) * 1.8 + 32) +"℉";
                }

                mItemMaxView.setText(max);
                mItemMinView.setText(min);
            }
            public void bindDrawbable(Drawable drawable){
                mItemImageView.setImageDrawable(drawable);
            }
        }

        class weatherHolder extends RecyclerView.ViewHolder{
            private ImageView mItemImageView;
            private TextView mItemDateView;
            private TextView mItemweatherView;
            private TextView mItemMaxView;
            private TextView mItemMinView;

            public weatherHolder(View itemView){
                super(itemView);
                mItemImageView = (ImageView)itemView.findViewById(R.id.item_image_view);
                mItemDateView = (TextView)itemView.findViewById(R.id.item_date_view);
                mItemweatherView = (TextView)itemView.findViewById(R.id.item_weather_view);
                mItemMaxView = (TextView)itemView.findViewById(R.id.item_maxtemperature_view);
                mItemMinView = (TextView)itemView.findViewById(R.id.item_mintemperature_view);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(getAdapterPosition(),mGalleryItems);
                        }
                    }
                });
            }

            public void bindGalleryItem(GalleryItem galleryItem) throws ParseException {
                String week = null;
                week = galleryItem.getFxDate();
                if (Time.IsToday(week)){
                    week = "Today";
                } else if (Time.IsTomorrow(week)){
                    week = "Tomorrow";
                } else {
                    week = Time.StringToWeek(week);
                }
                mItemDateView.setText(week);
                mItemweatherView.setText(galleryItem.getTextDay());

                String max;
                String min;
                if (mTemperature_unit.equals("C")){
                    max = galleryItem.getTempMax()+"℃";
                    min = galleryItem.getTempMin()+"℃";
                } else {
                    max = (int) (Integer.parseInt(galleryItem.getTempMax()) * 1.8 + 32) +"℉";
                    min = (int) (Integer.parseInt(galleryItem.getTempMin()) * 1.8 + 32) +"℉";
                }

                mItemMaxView.setText(max);
                mItemMinView.setText(min);
            }

            public void bindDrawbable(Drawable drawable){
                mItemImageView.setImageDrawable(drawable);
            }
        }
    }

    class FetchIemsTask extends AsyncTask<Void,Void,List<GalleryItem>>{
        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            return new qweather(mlocation).fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> galleryItems) {
            mItems = galleryItems;
            String keyid;
            for (int position=0;position<galleryItems.size();position++){
                keyid = mlocation+position;
                String [] whereArgs = {mlocation};
                if (GalleryItemLab.itemexist(whereArgs,keyid,mDatabase)){
                    GalleryItemLab.updateGalleryItem(galleryItems.get(position),mlocation,position,mDatabase);
                } else {
                    GalleryItemLab.addGalleryItem(galleryItems.get(position),mlocation,position,mDatabase);
                }
            }
            setupAdapter();
        }
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
