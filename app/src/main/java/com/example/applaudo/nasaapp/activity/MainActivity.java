package com.example.applaudo.nasaapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.fragments.DetailsPhotoFragment;
import com.example.applaudo.nasaapp.fragments.PhotosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DetailsPhotoFragment.HideToolbar {

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_back_button)
    ImageView mBackButton;
    @BindView(R.id.toolbar_app_title)
    TextView mAppTitle;

    Boolean isBackButtonVisible = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        //Creating the new fragment.
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        PhotosFragment photosFragment = new PhotosFragment();
        transaction.replace(R.id.activity_main,photosFragment);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
            //To handle back pressing in the fullscreen menu
             int fragmentCount = getFragmentManager().getBackStackEntryCount();
            if (fragmentCount == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }

            //To set back the color to the toolbar
            mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            mAppTitle.setVisibility(View.VISIBLE);
            mBackButton.setVisibility(View.GONE);

            isBackButtonVisible = false;

    }

    @Override
    public void hideToolbar() {

        if(isBackButtonVisible) {
            mBackButton.setVisibility(View.GONE);
            isBackButtonVisible = false;

        } else {
            mBackButton.setVisibility(View.VISIBLE);
            isBackButtonVisible = true;
        }

    }
}
