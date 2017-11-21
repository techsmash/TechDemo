package com.example.openweather.ui.presenter;

import com.example.openweather.injection.ActivityScope;
import com.example.openweather.injection.AppComponent;
import com.example.openweather.ui.fragment.MainFragment;

import dagger.Component;

/**
 * Created by kumar on 11/21/17.
 */

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {MainModule.class}
)
public interface MainFragmentComponent {
    void inject(MainFragment fragment);


}
