package com.example.applaudo.nasaapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.applaudo.nasaapp.R;
import com.example.applaudo.nasaapp.fragments.PhotosFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}
