package com.example.applaudo.nasaapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PhotoRoot {

    @SerializedName("photos")
    private ArrayList<Photo> mPhotos;


    public PhotoRoot(ArrayList<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public ArrayList<Photo> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(ArrayList<Photo> mPhotos) {
        this.mPhotos = mPhotos;
    }
}




