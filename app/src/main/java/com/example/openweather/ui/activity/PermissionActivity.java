package com.example.openweather.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.view.WindowManager;

import com.example.openweather.R;

import javax.inject.Inject;

/**
 * Activity to handle checking and, if necessary, requesting permissions on the fly. We run this as an activity.
 */
public class PermissionActivity extends BaseActivity {

    public static final String PACKAGE_URL_SCHEME = "package:";
    public static final String EXTRA_PERMISSIONS = "com.eurosport.permissions.EXTRA_PERMISSIONS";

    public static final int PERMISSION_REQUEST_CODE = 40;
    public static final String MESSAGE_INTENT_KEY = "justificationMessage";

    public static final int SETTINGS_REQUEST_CODE = 42;

    PermissionInteractor interactor; //TODO inject

    @VisibleForTesting
    boolean shouldSkipRequest;


    @VisibleForTesting
    String[] permissions;

    @VisibleForTesting
    String justificationMessage;

    @VisibleForTesting
    AlertDialog dialog;

    /**
     * Used for triggering permission requests.
     *
     * @param activity    Activity that needs the permission and is listening for response
     * @param requestCode identifier for calling Activity
     * @param permissions Which permissions are being requested
     */
    public static void startActivityForResult(Activity activity, int requestCode,
                                              String... permissions) {
        Intent intent = newIntent(activity, permissions);
        activity.startActivityForResult(intent, requestCode, null);
    }

    /**
     * Start this activity with some optional settings for handling denied permissions.
     * @param activity It's a activity.
     * @param requestCode Request code.
     * @param justificationMessage The Justification Message to show to the user upon denial.
     * @param permissions The pemissions list.
     */
    public static void startActivityForResult(Activity activity,
                                              int requestCode,
                                              String justificationMessage,
                                              String... permissions) {
        Intent intent = newIntent(activity, justificationMessage, permissions);
        activity.startActivityForResult(intent, requestCode, null);
    }

    /**
     * Obtain an Intent to start the PermissionActivity.
     *
     * @param context - The Context used to create the Intent.
     * @param permissions - Array of permission Strings to request.
     * @return - Intent to start the PermissionActivity for result.
     */
    public static Intent newIntent(Context context, String... permissions) {
        return newIntent(context, null, permissions);
    }

    /**
     * Create a new intent to launch this activity with an optional justification message.
     * @param context -
     * @param justificationMessage -
     * @param permissions -
     * @return -
     */
    public static Intent newIntent(Context context,
                                   String justificationMessage,
                                   String... permissions) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);

        if (justificationMessage != null) {
            intent.putExtra(MESSAGE_INTENT_KEY, justificationMessage);
        }

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if ((getIntent() == null) || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException(
                    "This Activity needs to be launched using the static startActivityForResult() method.");
        }

        super.onCreate(savedInstanceState);
        interactor = new PermissionInteractor(this);
        setContentView(R.layout.activity_permission_frame);

        //default the result until we set it to GRANTED
        setResult(PackageManager.PERMISSION_DENIED);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onResume() {
        super.onResume();

        permissions = getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
        justificationMessage = getIntent().getStringExtra(MESSAGE_INTENT_KEY);

        if (interactor.hasPermissions(permissions)) {
            setPermissionsGranted();
        } else if (!shouldSkipRequest){
            requestPermissions(permissions);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_REQUEST_CODE) {
            if (interactor.hasPermissions(permissions)) {
                setPermissionsGranted();
            } else {
                setPermissionsDenied();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE
                && interactor.areAllPermissionsGranted(permissions, grantResults)) {
            setPermissionsGranted();
        } else {
            shouldSkipRequest = true;
            showMissingPermissionDialog();
        }
    }

    @VisibleForTesting
    void setPermissionsGranted() {
        setResult(PackageManager.PERMISSION_GRANTED);
        finish();
    }

    @VisibleForTesting
    void setPermissionsDenied() {
        setResult(PackageManager.PERMISSION_DENIED);
        finish();
    }


    @VisibleForTesting
    void showMissingPermissionDialog() {
        dialog = new AlertDialog.Builder(PermissionActivity.this)
                .setTitle(R.string.app_permissions)
                .setMessage(getDialogMessage())
                .setPositiveButton(R.string.settings, buildPositiveDialogClickListener())
                .setNegativeButton(R.string.ok, buildNegativeDialogClickListener())
                .setOnCancelListener(buildOnCancelListener())
                .show();
    }

    @VisibleForTesting
    DialogInterface.OnClickListener buildPositiveDialogClickListener() {
        return (dialog, which) -> {
            startAppSettings();
            shouldSkipRequest = false;
        };
    }

    @VisibleForTesting
    DialogInterface.OnClickListener buildNegativeDialogClickListener() {
        return (dialog, which) -> setPermissionsDenied();
    }

    @VisibleForTesting
    DialogInterface.OnCancelListener buildOnCancelListener() {
        return dialog -> setPermissionsDenied();
    }

    @VisibleForTesting
    void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @VisibleForTesting
    void startAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivityForResult(intent, SETTINGS_REQUEST_CODE);
    }

    @VisibleForTesting
    String getDialogMessage() {
        if (justificationMessage != null) {
            return justificationMessage;
        } else {
            return getString(R.string.permission_rationale_general);
        }
    }



}
