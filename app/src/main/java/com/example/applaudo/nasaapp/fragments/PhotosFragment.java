package com.example.applaudo.nasaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.adapter.PhotoAdapter;
import com.example.applaudo.nasaapp.models.Photo;
import com.example.applaudo.nasaapp.models.PhotoRoot;
import com.example.applaudo.nasaapp.network.Api;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotosFragment extends Fragment implements PhotoAdapter.OnPhotoClicked {


    private PhotoAdapter mAdapter;
    private static final String API_KEY = "pRplRHwGn1Nx3xMNbGjTP8jGDfbKJkQNLCjzZREn";
    private static final int SOL = 50;
    private static final int PAGE = 3;

    @BindView(R.id.fragment_photos_recycler) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Inflating the layout for this fragment
        //And setting the data in the recycler
        View v = inflater.inflate(R.layout.fragment_photos, container, false);

        ButterKnife.bind(this,v);

        final GridLayoutManager manager = new GridLayoutManager(getContext(),3);

        //Calling the Retrofit Interface to fetch the data

                Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        //Calling the API and setting the arguments
        Call<PhotoRoot> call = api.getPhotoRoot(SOL,PAGE,API_KEY);

        call.enqueue(new Callback<PhotoRoot>() {
            @Override
            public void onResponse(Call<PhotoRoot> call, Response<PhotoRoot> response) {

                List<Photo> photoList = response.body().getPhotos();

                mAdapter = new PhotoAdapter(photoList,PhotosFragment.this);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(manager);
                }

            @Override
            public void onFailure(Call<PhotoRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void onPhotoClicked(int position) {
        Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
