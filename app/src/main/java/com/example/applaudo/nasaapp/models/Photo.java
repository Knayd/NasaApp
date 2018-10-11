package com.example.applaudo.nasaapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mImageSrc);
        dest.writeParcelable(this.mRover, flags);
        dest.writeParcelable(this.mCamera, flags);
    }

    protected Photo(Parcel in) {
        this.mId = in.readString();
        this.mImageSrc = in.readString();
        this.mRover = in.readParcelable(Rover.class.getClassLoader());
        this.mCamera = in.readParcelable(Camera.class.getClassLoader());
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
