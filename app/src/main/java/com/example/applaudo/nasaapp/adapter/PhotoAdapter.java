package com.example.applaudo.nasaapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.glide.GlideApp;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int GRID_TYPE = -1;
    public static final int DETAILS_TYPE = -2;

    private ArrayList<Photo> mPhotoList;
    private OnPhotoClicked mCallback;

    public PhotoAdapter(ArrayList<Photo> mPhotoList, OnPhotoClicked mCallback) {
        this.mPhotoList = mPhotoList;
        this.mCallback = mCallback;
    }

    public interface OnPhotoClicked{
        void onPhotoClicked(int position,ArrayList<Photo> list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_recycler,parent,false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
        photoViewHolder.bindView(mPhotoList,position);
        }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_photo_img) ImageView mPhotoImg;
        //private ImageView mPhotoImg;

        private PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //mPhotoImg = itemView.findViewById(R.id.item_photo_img);
        }

        //.setOnClickListener
        //Callback for the interface
        @OnClick(R.id.item_photo_img)
        public void onClick(){
            mCallback.onPhotoClicked(getAdapterPosition(), mPhotoList);
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
}
