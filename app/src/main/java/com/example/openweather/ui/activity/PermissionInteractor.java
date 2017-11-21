package com.example.openweather.ui.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.ContextCompat;

import com.example.openweather.injection.ActivityScope;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Helper class for checking permissions and for holding permission arrays used in the app.
 */
public class PermissionInteractor {

    public static final String[] LOCATION_PERMISSIONS = new String[]{
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
    };

    public static final String[] NETWORK_PERMISSIONS = new String[]{
            ACCESS_NETWORK_STATE
    };


    private Context context;


    public PermissionInteractor(Context context) {
        this.context = context;
    }

    /**
     * Method to check if necessary permissions are available.
     *
     * @param permissions Permission(s) that needed for feature.
     * @return Returns true if all permissions are available, false otherwise.
     */
    public boolean hasPermissions(String... permissions) {
        for (String permission : permissions) {
            if (!checkSinglePermission(permission)) {
                return false;
            }
        }
        return true;
    }

    @VisibleForTesting
    boolean checkSinglePermission(String permission) {
        return delegateSinglePermissionCheck(permission) == PERMISSION_GRANTED;
    }

    @VisibleForTesting
    int delegateSinglePermissionCheck(String permission) {
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * Returns true if all permissions are granted, false otherwise.
     *
     * @param permissions  Array of Permissions that were requested.
     * @param grantResults Array of Permission status codes.
     * @return result of permission check.
     */
    public boolean areAllPermissionsGranted(String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PERMISSION_GRANTED) {
                //Timber.d("Permission: %s was not granted.", permissions[i]);
                return false;
            }
        }
        return true;
    }

    /**
     * Convenience method for clients to compare their result with the PERMISSION_GRANTED result code. This allows
     * things like presenters to leverage the injected permission util rather than taking a dependency directly on the
     * Android SDK.
     *
     * @return The code that should be used to evaluate a PERMISSION_GRANTED activity result code from the
     * PermissionActivity.
     */
    public int getPermissionGrantedResultCode() {
        return PackageManager.PERMISSION_GRANTED;
    }
}
