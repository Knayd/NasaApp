package com.example.applaudo.nasaapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.glide.GlideApp;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int GRID_TYPE = -1;
    private static final int FULLSCREEN_TYPE = -2;

    private ArrayList<Photo> mPhotoList = new ArrayList<>(); //This so "size" returns "0" at first load

    private OnGridItemClicked mGridCallback;
    private OnDetailsItemClicked mDetailsCallback;

    private boolean showInFullScreen; //This is to determine whether or not show the image on fullscreen

    public PhotoAdapter(OnGridItemClicked mGridCallback, Boolean showInFullScreen) {
        this.mGridCallback = mGridCallback;
        this.showInFullScreen = showInFullScreen;
    }

    public PhotoAdapter(OnDetailsItemClicked mDetailsCallback, Boolean showInFullScreen) {
        this.showInFullScreen = showInFullScreen;
        this.mDetailsCallback = mDetailsCallback;
    }

    public interface OnGridItemClicked {
        void onPhotoSimpleClicked(int position, ArrayList<Photo> list);
    }

    public interface OnDetailsItemClicked {

        void onPhotoSimpleClicked();
        void onPhotoLongClicked(int position, ArrayList<Photo> list);
    }

    //Helper method
    public void setPhotoList(ArrayList<Photo> mPhotoList) {
        this.mPhotoList = mPhotoList;
        notifyDataSetChanged();
    }

    //Helper method
    public void addElements(ArrayList<Photo> mPhotoList){
        this.mPhotoList.addAll(mPhotoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;

        switch (viewType){
            case GRID_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_photo_recycler,parent,false);
                return new PhotoGridViewHolder(v);
            default:
                //Fullscreen
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fullscreen_photo_recycler,parent,false);
                return new PhotoFullScreenViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof PhotoGridViewHolder) {
            PhotoGridViewHolder photoGridViewHolder = (PhotoGridViewHolder) holder;
            photoGridViewHolder.bindView(mPhotoList,position);
        } else {
            PhotoFullScreenViewHolder photoFullScreenViewHolder= (PhotoFullScreenViewHolder) holder;
            photoFullScreenViewHolder.bindView(mPhotoList,position);
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(showInFullScreen){
            return FULLSCREEN_TYPE;
        } else {
            return GRID_TYPE;
        }
    }

    class PhotoGridViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_photo_img) ImageView mPhotoImg;
        //private ImageView mPhotoFullscreen;

        private PhotoGridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //mPhotoFullscreen = itemView.findViewById(R.id.item_photo_img);
        }

        //.setOnClickListener
        //Callback for the interface
        @OnClick(R.id.item_photo_img)
        public void onClick(){
            mGridCallback.onPhotoSimpleClicked(getAdapterPosition(), mPhotoList);
        }

        void bindView(ArrayList<Photo> list, int position) {
            //Method to load the image from url
            String url = list.get(position).getImageSrc();

            //This is to create the progress bar on every image
            CircularProgressDrawable drawable = new CircularProgressDrawable(mPhotoImg.getContext());
            drawable.setStrokeWidth(5f);
            drawable.setCenterRadius(30f);
            drawable.start();
            GlideApp.with(mPhotoImg.getContext())
                    .load(url)
                    .placeholder(drawable)
                    .error(R.drawable.nasa_placeholder)
                    .into(mPhotoImg);
        }

    }

    class PhotoFullScreenViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_fullscreen_photo_img) ImageView mPhotoFullscreen;


         PhotoFullScreenViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.item_fullscreen_photo_img)
        public void onClick(){
            mDetailsCallback.onPhotoSimpleClicked();
        }

        @OnLongClick(R.id.item_fullscreen_photo_img)
        public boolean onPhotoLongClick(){
             mDetailsCallback.onPhotoLongClicked(getAdapterPosition(), mPhotoList);
             return true;
        }

        void bindView(ArrayList<Photo> list, int position) {
            //Method to load the image from url
            String url = list.get(position).getImageSrc();

            //This is to create the progress bar on every image
            CircularProgressDrawable drawable = new CircularProgressDrawable(mPhotoFullscreen.getContext());
            drawable.setStrokeWidth(5f);
            drawable.setCenterRadius(30f);
            drawable.start();
            GlideApp.with(mPhotoFullscreen.getContext())
                    .load(url)
                    .placeholder(drawable)
                    .error(R.drawable.nasa_placeholder)
                    .into(mPhotoFullscreen);
        }
    }
}
