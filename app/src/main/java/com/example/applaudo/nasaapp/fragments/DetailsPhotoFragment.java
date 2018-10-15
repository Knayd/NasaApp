package com.example.applaudo.nasaapp.fragments;

import android.app.Activity;
import android.content.Context;
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
import com.example.applaudo.nasaapp.activity.MainActivity;
import com.example.applaudo.nasaapp.adapter.PhotoAdapter;
import com.example.applaudo.nasaapp.dialogfragment.BottomSheetPhotoDialogFragment;
import com.example.applaudo.nasaapp.models.Photo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsPhotoFragment extends Fragment implements PhotoAdapter.OnDetailsItemClicked {

    private PhotoAdapter mAdapter;

    public static final String DIALOG_POSITION = "DIALOG_POSITION";
    public static final String DIALOG_LIST = "DIALOG_LIST";

    private ToolbarViewHolder mToolbarViewHolder;
    private RecyclerViewHolder mRecyclerViewHolder;


    private HideToolbar mCallback;


    public interface HideToolbar {
        void hideToolbar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallback = (MainActivity) context;
    }

    @Override
    public void onPhotoSimpleClicked() {
        mCallback.hideToolbar();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details_photo, container, false);

        //Binding each ViewHolder individually
        mToolbarViewHolder = new ToolbarViewHolder(container);
        mRecyclerViewHolder = new RecyclerViewHolder(v);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        //Retrieving the args
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<Photo> list = bundle.getParcelableArrayList(PhotosFragment.DATASET);
            int position = bundle.getInt(PhotosFragment.POSITION);

            mAdapter = new PhotoAdapter(this, true);

            mAdapter.setPhotoList(list);

            //To scroll to the desired position
            manager.scrollToPosition(position);

            mRecyclerViewHolder.recyclerView.setAdapter(mAdapter);
            mRecyclerViewHolder.recyclerView.setLayoutManager(manager);

            //This is to make the images snap in the center
            SnapHelper helper = new PagerSnapHelper();
            helper.attachToRecyclerView(mRecyclerViewHolder.recyclerView);

        }

        //Hiding the elements in the toolbar
        mToolbarViewHolder.toolbar.setBackgroundColor(Color.TRANSPARENT);
        mToolbarViewHolder.appTitle.setVisibility(View.GONE);
        mToolbarViewHolder.backButton.setVisibility(View.INVISIBLE);

        return v;

    }


    @Override
    public void onPhotoLongClicked(int position, ArrayList<Photo> list) {
        BottomSheetPhotoDialogFragment dialogFragment = BottomSheetPhotoDialogFragment.getInstance();

        //Sending the data to the bottom sheet dialog from the fullscreen photo

        Bundle bundle = new Bundle();
        bundle.putInt(DIALOG_POSITION, position);
        bundle.putParcelableArrayList(DIALOG_LIST, list);
        dialogFragment.setArguments(bundle);

        dialogFragment.show(getFragmentManager(), "IDK");
    }


    //Viewholders to bind the different views using Butteknife
    class RecyclerViewHolder {

        @BindView(R.id.fragment_photodetails_recycler)
        RecyclerView recyclerView;

        RecyclerViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    class ToolbarViewHolder {
        @BindView(R.id.toolbar_main)
        Toolbar toolbar;
        @BindView(R.id.toolbar_back_button)
        ImageView backButton;
        @BindView(R.id.toolbar_app_title)
        TextView appTitle;

        ToolbarViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.toolbar_back_button)
        public void backButtonPressed() {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            appTitle.setVisibility(View.VISIBLE);
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            transaction.detach(DetailsPhotoFragment.this);
            transaction.commit();

            mCallback.hideToolbar();
        }
    }

}
