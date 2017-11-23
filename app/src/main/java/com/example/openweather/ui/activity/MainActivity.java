package com.example.openweather.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.openweather.R;
import com.example.openweather.ui.fragment.MainFragment;
import com.example.openweather.ui.presenter.OnLocationPermissionGrantedListener;

public class MainActivity extends BaseActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 10;
    private static final String FRAG_TAG = "mainFragTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, MainFragment.newInstance(), FRAG_TAG)
                .commit();
    }

    @VisibleForTesting
    public void setupToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.rootToolBar);
        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_curr_loc:
                // User chose the "current location" item, show the app settings UI...
                processCurrentLocationRequest();
                return true;

            case R.id.action_load_file:
                //loadFile();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void processCurrentLocationRequest() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            //load the fused location provider
            Toast.makeText(this, "already have location permission", Toast.LENGTH_LONG).show();
            processPermissionComplete();
        } else {
            PermissionActivity.startActivityForResult(this, LOCATION_PERMISSION_REQUEST_CODE,
                    getString(R.string.location_permission_rational),
                    PermissionInteractor.LOCATION_PERMISSIONS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            //TODO
            if (resultCode == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "got location permission", Toast.LENGTH_LONG).show();
                processPermissionComplete();
            } else {
                Toast.makeText(this, "User denied", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void processPermissionComplete() {
        ((OnLocationPermissionGrantedListener)getSupportFragmentManager().findFragmentByTag(FRAG_TAG)).onLocationPermissionGranted();
    }
}
