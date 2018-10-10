package com.example.applaudo.nasaapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Photo> mPhotoList;

    public PhotoAdapter(List<Photo> mPhotoList) {
        this.mPhotoList = mPhotoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_recycler,parent,false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;

        photoViewHolder.mPhotoId.setText(mPhotoList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder{

        private TextView mPhotoId;
        private ImageView mPhotoImg;

        //TODO:Missing setters and getters
        public PhotoViewHolder(View itemView) {
            super(itemView);
            mPhotoId = itemView.findViewById(R.id.item_photo_id);
            mPhotoImg = itemView.findViewById(R.id.item_photo_img);

        }


    }
}
