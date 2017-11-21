package com.example.openweather.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.example.openweather.App;

import butterknife.Unbinder;

import static butterknife.ButterKnife.bind;

/**
 * Created by kumar on 11/21/17.
 */

public class BaseActivity extends AppCompatActivity {

    Unbinder unbinder;

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        unbinder = bind(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        unbinder = bind(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        App.getInstance().getAppComponent().inject(this);
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if(unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }
}
