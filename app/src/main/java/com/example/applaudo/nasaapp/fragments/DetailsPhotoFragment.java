package com.example.applaudo.nasaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.adapter.PhotoAdapter;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsPhotoFragment extends Fragment implements PhotoAdapter.OnPhotoClicked {

    private PhotoAdapter mAdapter;
    @BindView(R.id.fragment_photodetails_recycler) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details_photo, container, false);
        ButterKnife.bind(this,v);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);

        //Retrieving the args
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<Photo> list = bundle.getParcelableArrayList(PhotosFragment.DATASET);
            int position = bundle.getInt(PhotosFragment.POSITION);

            mAdapter = new PhotoAdapter(list,this,true);

            //To scroll to the desired position
            manager.scrollToPosition(position);

            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(manager);

            //This is to make the images snap in the center
            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(recyclerView);

            Toast.makeText(getContext(),list.get(position).getId(), Toast.LENGTH_SHORT).show();
        }




//        Button btn = v.findViewById(R.id.btn_close);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//
//                transaction.detach(DetailsPhotoFragment.this);
//                transaction.commit();
//            }
//        });

        return v;
    }

    @Override
    public void onPhotoClicked(int position, ArrayList<Photo> list) {

    }
}
