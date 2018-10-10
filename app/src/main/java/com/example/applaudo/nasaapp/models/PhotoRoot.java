package com.example.applaudo.nasaapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoRoot {

    @SerializedName("photos")
    private List<Photo> mPhotos;


    public PhotoRoot(List<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public List<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }
}




