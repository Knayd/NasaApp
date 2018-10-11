package com.example.applaudo.nasaapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Camera implements Parcelable {

    @SerializedName("name")
    private String mName;

    public Camera(String mName) {

        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
    }

    protected Camera(Parcel in) {
        this.mName = in.readString();
    }

    public static final Parcelable.Creator<Camera> CREATOR = new Parcelable.Creator<Camera>() {
        @Override
        public Camera createFromParcel(Parcel source) {
            return new Camera(source);
        }

        @Override
        public Camera[] newArray(int size) {
            return new Camera[size];
        }
    };
}
