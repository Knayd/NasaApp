package com.example.applaudo.nasaapp.models;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("id")
    private String mId;
    @SerializedName("img_src")
    private String mImageSrc;

    @SerializedName("rover")
    private Rover mRover;

    @SerializedName("camera")
    private Camera mCamera;

    public Photo(String mId, String mImageSrc, Rover mRover, Camera mCamera) {
        this.mId = mId;
        this.mImageSrc = mImageSrc;
        this.mRover = mRover;
        this.mCamera = mCamera;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getImageSrc() {
        return mImageSrc;
    }

    public void setImageSrc(String mImageSrc) {
        this.mImageSrc = mImageSrc;
    }

    public Rover getRover() {
        return mRover;
    }

    public void setRover(Rover mRover) {
        this.mRover = mRover;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void setCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }
}
