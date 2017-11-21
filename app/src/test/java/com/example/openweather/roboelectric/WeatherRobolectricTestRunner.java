package com.example.openweather.roboelectric;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by kumar on 11/21/17.
 */

public class WeatherRobolectricTestRunner extends RobolectricTestRunner {
    public WeatherRobolectricTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }
}
