package com.example.applaudo.nasaapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.adapter.PhotoAdapter;
import com.example.applaudo.nasaapp.dialogfragment.BottomSheetPhotoDialogFragment;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsPhotoFragment extends Fragment implements PhotoAdapter.OnDetailsItemClicked {

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

            mAdapter = new PhotoAdapter(this,true);

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

        //Getting the reference for the toolbar and its elements
        final Toolbar mToolbar = container.findViewById(R.id.toolbar_main);

        ImageView mBackButton = mToolbar.findViewById(R.id.toolbar_back_button);
        final TextView mAppTitle = mToolbar.findViewById(R.id.toolbar_app_title);

        mToolbar.setBackgroundColor(Color.TRANSPARENT);
        mAppTitle.setVisibility(View.GONE);
        //mBackButton.setVisibility(View.GONE);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.setBackgroundColor(Color.BLUE);
                mAppTitle.setVisibility(View.VISIBLE);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.detach(DetailsPhotoFragment.this);
                transaction.commit();
            }
        });

        return v;

    }

    @Override
    public void onPhotoSimpleClicked() {
        //TODO: To be implemented
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
