package com.example.applaudo.nasaapp.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.fragments.PhotosFragment;
import com.example.applaudo.nasaapp.models.Photo;
import com.example.applaudo.nasaapp.models.PhotoRoot;
import com.example.applaudo.nasaapp.network.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creating the new fragment.
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        PhotosFragment photosFragment = new PhotosFragment();
        transaction.replace(R.id.activity_main,photosFragment);
        transaction.commit();

    }
}
