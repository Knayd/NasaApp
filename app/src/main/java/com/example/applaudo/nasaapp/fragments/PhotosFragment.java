package com.example.applaudo.nasaapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotosFragment extends Fragment implements PhotoAdapter.OnGridItemClicked {

    //region Constants
    private PhotoAdapter mAdapter;

    private static final String API_KEY = "pRplRHwGn1Nx3xMNbGjTP8jGDfbKJkQNLCjzZREn";
    private static final int SOL = 30;
    private static final int PAGE = 1;

    public static final String DATASET = "DATASET";
    public static final String POSITION = "POSITION";

    private static final String TRANSACTION_TAG = "TRANSACTION_TAG";

    private int currentPage = 1;

    //endregion

    @BindView(R.id.fragment_photos_recycler)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflating the layout for this fragment
        //And setting the data in the recycler
        View v = inflater.inflate(R.layout.fragment_photos, container, false);

        ButterKnife.bind(this, v);

        final GridLayoutManager manager = new GridLayoutManager(getContext(), 3);

        //Calling the Retrofit Interface to fetch the data

        Api api = getRetrofit(getOkhttpClient());

        //Calling the API and setting the arguments
        Call<PhotoRoot> call = api.getPhotoRoot(SOL, PAGE, API_KEY);

        call.enqueue(new Callback<PhotoRoot>() {
            @Override
            public void onResponse(Call<PhotoRoot> call, Response<PhotoRoot> response) {

                ArrayList<Photo> photoList = response.body().getPhotos();

                mAdapter.setPhotoList(photoList);
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

                    currentPage++; //To load the next page

                    Toast.makeText(recyclerView.getContext(), String.valueOf(currentPage), Toast.LENGTH_LONG).show();

                    Api api = getRetrofit(getOkhttpClient());

                    Call<PhotoRoot> call = api.getPhotoRoot(SOL, currentPage, API_KEY);

                    call.enqueue(new Callback<PhotoRoot>() {
                        @Override
                        public void onResponse(@NonNull Call<PhotoRoot> call, @NonNull Response<PhotoRoot> response) {
                            //Updating the list with the new data
                            mAdapter.addElements(response.body().getPhotos());
                        }

                        @Override
                        public void onFailure(@NonNull Call<PhotoRoot> call, @NonNull Throwable t) {

                        }
                    });
                }

            }
        });

        mAdapter = new PhotoAdapter(PhotosFragment.this, false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(manager);

        return v;
    }

    @Override
    public void onPhotoSimpleClicked(int position, ArrayList<Photo> list) {
        Toast.makeText(getContext(), list.get(position).getId(), Toast.LENGTH_SHORT).show();

        //To load the fragment with the fullscreen photo
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        //Setting the args for the new fragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DATASET, list);
        bundle.putInt(POSITION, position);

        DetailsPhotoFragment detailsPhotoFragment = new DetailsPhotoFragment();
        detailsPhotoFragment.setArguments(bundle);

        transaction.add(R.id.activity_main, detailsPhotoFragment);
        transaction.addToBackStack(TRANSACTION_TAG);
        transaction.commit();
    }


    //Helper method to call Retrofit
    private Api getRetrofit(OkHttpClient client) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        return api;
    }

    //Interceptors
    private class OfflineResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            Request request = chain.request();

            if (networkInfo != null && !networkInfo.isConnected()) {
                request = request.newBuilder()
                        .header("Cache-Control",
                                "public, only-if-cached, max-stale=" + 2419200)
                        .build();
            }

            return chain.proceed(request);
        }
    }

    private class ResponseCacheInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        }
    }

    private OkHttpClient getOkhttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // Enable response caching
                .addNetworkInterceptor(new ResponseCacheInterceptor())
                .addInterceptor(new OfflineResponseCacheInterceptor())
                // Set the cache location and size (5 MB)
                .cache(new Cache(new File(getContext().getCacheDir(),
                        "apiResponses"), 5 * 1024 * 1024))
                .build();

        return okHttpClient;
    }


}
