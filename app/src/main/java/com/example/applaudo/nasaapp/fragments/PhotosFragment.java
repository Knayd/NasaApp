package com.example.applaudo.nasaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotosFragment extends Fragment implements PhotoAdapter.OnPhotoClicked {


    private PhotoAdapter mAdapter;
    private static final String API_KEY = "pRplRHwGn1Nx3xMNbGjTP8jGDfbKJkQNLCjzZREn";
    private static final int SOL = 30;
    private static final int PAGE = 1;

    public static final String DATASET = "DATASET";
    public static final String POSITION = "POSITION";

    private static final String TRANSACTION_TAG= "TRANSACTION_TAG";

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

                ArrayList<Photo> photoList = response.body().getPhotos();

                mAdapter = new PhotoAdapter(photoList,PhotosFragment.this,false);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(manager);
                }

            @Override
            public void onFailure(Call<PhotoRoot> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        //To do the pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    Toast.makeText(recyclerView.getContext(), "Last", Toast.LENGTH_LONG).show();

                }

            }
        });



        return v;
    }

    @Override
    public void onPhotoClicked(int position, ArrayList<Photo> list) {
        Toast.makeText(getContext(), list.get(position).getId(), Toast.LENGTH_SHORT).show();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        //Setting the args for the new fragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DATASET, list);
        bundle.putInt(POSITION,position);

        DetailsPhotoFragment detailsPhotoFragment = new DetailsPhotoFragment();
        detailsPhotoFragment.setArguments(bundle);

        transaction.add(R.id.activity_main,detailsPhotoFragment);
        transaction.addToBackStack(TRANSACTION_TAG);
        transaction.commit();
    }



}
