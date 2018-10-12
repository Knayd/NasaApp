package com.example.applaudo.nasaapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.fragments.PhotosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;

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
            mToolbar.setBackgroundColor(Color.RED);

    }
}
