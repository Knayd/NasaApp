package com.example.applaudo.nasaapp.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

        this.showInFullScreen = showInFullScreen;
        //Since I'm using the same adapter, this check and cast is to determine which callbacks
        //the fragment is supposed to listen to.
        if (mGridCallback instanceof OnDetailsItemClicked) {
            this.mDetailsCallback = (OnDetailsItemClicked) mGridCallback;
        } else {
            this.mGridCallback = mGridCallback;
        }

    }

    public interface OnGridItemClicked {
        void onPhotoSimpleClicked(int position, ArrayList<Photo> list);
    }

    public interface OnDetailsItemClicked extends OnGridItemClicked {
        //Extends the other interface because both fragments should handle simple click
        void onPhotoLongClicked(int position, ArrayList<Photo> list);
    }

    //Helper method
    public void setPhotoList(ArrayList<Photo> mPhotoList) {
        this.mPhotoList = mPhotoList;
        notifyDataSetChanged();
    }

    //Helper method
    public void addElements(ArrayList<Photo> mPhotoList) {
        this.mPhotoList.addAll(mPhotoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_photo_recycler, parent, false);
        //Passing the type as a parameter to let the constructor do the change of width and height
        return new PhotoGridViewHolder(v, viewType);
        }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            PhotoGridViewHolder photoGridViewHolder = (PhotoGridViewHolder) holder;
            photoGridViewHolder.bindView(mPhotoList, position);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (showInFullScreen) {
            return FULLSCREEN_TYPE;
        } else {
            return GRID_TYPE;
        }
    }

    class PhotoGridViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_photo_img)
        ImageView mPhotoImg;
        @BindView(R.id.item_parent_container)
        ConstraintLayout mLayout;
        //private ImageView mPhotoFullscreen;

        PhotoGridViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if(type==FULLSCREEN_TYPE){

                //Changing the size of the container
                ViewGroup.LayoutParams layoutParams =  mLayout.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                mLayout.setLayoutParams(layoutParams);

                //Changing the size of the image
                ViewGroup.LayoutParams params = mPhotoImg.getLayoutParams();
                params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
                params.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
                mPhotoImg.setLayoutParams(params);
                mPhotoImg.setScaleType(ImageView.ScaleType.FIT_CENTER);

            }
        }

        //region Callbacks for the interfaces

        //Checking if null before executing the method to determine be sure that the correct callback
        //will be executed

        @OnClick(R.id.item_photo_img)
        public void onClick() {
            if (mGridCallback != null) {
                mGridCallback.onPhotoSimpleClicked(getAdapterPosition(), mPhotoList);
            }
        }

        @OnClick(R.id.item_photo_img)
        public void onDetailsClick() {
            if (mDetailsCallback != null) {
                mDetailsCallback.onPhotoSimpleClicked(getAdapterPosition(), mPhotoList);
            }
        }

        @OnLongClick(R.id.item_photo_img)
        public boolean onPhotoLongClick() {
            if (mDetailsCallback != null) {
                mDetailsCallback.onPhotoLongClicked(getAdapterPosition(), mPhotoList);
            }
            return true;
        }

        //endregion

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
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(mPhotoImg);
        }

    }

}
