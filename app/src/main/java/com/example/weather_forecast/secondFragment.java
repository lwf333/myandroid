package com.example.weather_forecast;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class secondFragment extends Fragment {

    private RecyclerView msecondRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();
    private String mlocation;
    private String mTemperature_unit;

    private int lastposition=-1;
    public static secondFragment newInstance(String location,String Temperature_unit){
        return new secondFragment(location,Temperature_unit);
    }
    secondFragment(String location,String Temperature_unit){
        mlocation = location;
        mTemperature_unit=Temperature_unit;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new secondFragment.FetchIemsTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_fragment,container,false);

        msecondRecyclerView = (RecyclerView) v.findViewById(R.id.menu_recyclerview);
        msecondRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        setupAdapter();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mThumbnailDownloader.clearQueue();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lastposition!=-1){
            Log.i("way",mItems.get(lastposition).getTextDay());
            Fragment fragment = null;
            fragment = new Fragment();
            getFragmentManager().beginTransaction().replace(R.id.detail_large,fragment).commit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mThumbnailDownloader.quit();
    }

    private void setupAdapter(){
        if (isAdded()){
            secondFragment.weatherAdapter adapter = new secondFragment.weatherAdapter((mItems));
            adapter.setOnItemClickListener(new firstFragment.OnRecyclerItemClickListener() {
                @Override
                public void onItemClick(int position, List<GalleryItem> galleryItems) {
                    lastposition = position;
                    Fragment fragment = null;
                    fragment = new detail_fragment(galleryItems.get(position),mTemperature_unit);
                    getFragmentManager().beginTransaction().replace(R.id.detail_large,fragment).commit();
                }
            });
            msecondRecyclerView.setAdapter(adapter);
        }
    }

    private class weatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<GalleryItem> mGalleryItems;

        public weatherAdapter(List<GalleryItem> galleryItems){
            mGalleryItems = galleryItems;
        }

        private firstFragment.OnRecyclerItemClickListener mOnItemClickListener;
        public void setOnItemClickListener(firstFragment.OnRecyclerItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_gallery_large,viewGroup,false);
            return new secondFragment.weatherAdapter.weatherHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            String Icon = galleryItem.getIconDay();
            String name = "s1"+Icon;
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
            Drawable placeholder  = getResources().getDrawable(resId);
            secondFragment.weatherAdapter.weatherHolder viewHolder = (secondFragment.weatherAdapter.weatherHolder)holder;
            try {
                viewHolder.bindGalleryItem(galleryItem);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            viewHolder.bindDrawbable(placeholder);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }


        class weatherHolder extends RecyclerView.ViewHolder{
            private ImageView mItemImageView;
            private TextView mItemDateView;
            private TextView mItemweatherView;
            private TextView mItemMaxView;
            private TextView mItemMinView;

            public weatherHolder(View itemView){
                super(itemView);
                mItemImageView = (ImageView)itemView.findViewById(R.id.menu_image_view);
                mItemDateView = (TextView)itemView.findViewById(R.id.menu_date_view);
                mItemweatherView = (TextView)itemView.findViewById(R.id.menu_weather_view);
                mItemMaxView = (TextView)itemView.findViewById(R.id.menu_maxtemperature_view);
                mItemMinView = (TextView)itemView.findViewById(R.id.menu_mintemperature_view);
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
                String date =null;
                String time = null;
                date = galleryItem.getFxDate();
                if (Time.IsToday(date)){
                    mItemDateView.setText("Today");
                } else if (Time.IsTomorrow(date)){
                    mItemDateView.setText("Tomorrow");
                } else {
                    time = Time.DateChange(date);
                    mItemDateView.setText(time);
                }
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
    class FetchIemsTask extends AsyncTask<Void,Void, List<GalleryItem>>{
        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            return new qweather(mlocation).fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> galleryItems) {
            mItems = galleryItems;
            setupAdapter();
        }
    }
}
