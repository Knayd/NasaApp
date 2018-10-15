package com.example.applaudo.nasaapp.dialogfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.fragments.DetailsPhotoFragment;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BottomSheetPhotoDialogFragment extends android.support.design.widget.BottomSheetDialogFragment {

    public static BottomSheetPhotoDialogFragment getInstance() {
        return new BottomSheetPhotoDialogFragment();
    }

    @BindView(R.id.bottom_sheet_name)
    TextView mName;
    @BindView(R.id.bottom_sheet_launch)
    TextView mLaunch;
    @BindView(R.id.bottom_sheet_landing)
    TextView mLanding;
    @BindView(R.id.bottom_sheet_share)
    TextView mShare;

    private List<Photo> dataList;
    int position;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_photo_details, container, false);

        Bundle bundle = getArguments();

        ButterKnife.bind(this, v);

        //Retrieving the data sent from the fullscreen photo
        position = bundle.getInt(DetailsPhotoFragment.DIALOG_POSITION);

        dataList = bundle.getParcelableArrayList(DetailsPhotoFragment.DIALOG_LIST);


        mName.setText(dataList.get(position).getCamera().getName());
        mLanding.setText(dataList.get(position).getRover().getLandingDate());
        mLaunch.setText(dataList.get(position).getRover().getLaunchDate());
        mShare.setText("Share");

        return v;
    }


    @OnClick(R.id.bottom_sheet_share)
    public void shareUrl(){
        Intent share = new Intent();
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, dataList.get(position).getImageSrc());
        startActivity(Intent.createChooser(share,"Share image!"));
    }



}
