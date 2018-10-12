package com.example.applaudo.nasaapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.adapter.PhotoAdapter;
import com.example.applaudo.nasaapp.dialogfragment.BottomSheetPhotoDialogFragment;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsPhotoFragment extends Fragment implements PhotoAdapter.OnPhotoClicked, PhotoAdapter.OnPhotoLongClicked {

    private PhotoAdapter mAdapter;

    public static final String DIALOG_POSITION="DIALOG_POSITION";
    public static final String DIALOG_LIST="DIALOG_LIST";

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

            mAdapter = new PhotoAdapter(this,true,this);

            mAdapter.setPhotoList(list);

            //To scroll to the desired position
            manager.scrollToPosition(position);

            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(manager);

            //This is to make the images snap in the center
            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(recyclerView);

            Toast.makeText(getContext(),list.get(position).getId(), Toast.LENGTH_SHORT).show();
        }

        return v;


        //TODO: Clean afterwards

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

    }

    @Override
    public void onPhotoClicked(int position, ArrayList<Photo> list) {

    }

    @Override
    public void onPhotoLongClicked(int position, ArrayList<Photo> list) {
        BottomSheetPhotoDialogFragment dialogFragment = BottomSheetPhotoDialogFragment.getInstance();

        //Sending the data to the bottom sheet dialog from the fullscreen photo

        Bundle bundle = new Bundle();
        bundle.putInt(DIALOG_POSITION,position);
        bundle.putParcelableArrayList(DIALOG_LIST,list);
        dialogFragment.setArguments(bundle);

        dialogFragment.show(getFragmentManager(), "IDK");
    }
}
