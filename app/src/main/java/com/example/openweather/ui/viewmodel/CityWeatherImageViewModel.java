package com.example.openweather.ui.viewmodel;

import android.view.View;

import com.example.openweather.R;
import com.example.openweather.api.model.ImageResult;
import com.example.openweather.api.model.WeatherResponse;
import com.example.openweather.ui.viewholder.CityWeatherViewHolder;
import com.example.openweather.util.LongPressGestureDetector;

import static com.example.openweather.ui.viewmodel.ViewModelType.GETTY_IMAGE;

public class CityWeatherImageViewModel extends BaseViewModel<CityWeatherViewHolder> implements LongPressGestureDetector.Listener {
    private final WeatherResponse weatherResponse;
    private final Listener listener;

    public CityWeatherImageViewModel(WeatherResponse weatherResponse, Listener listener) {
        super(R.layout.getty_image_layout);
        this.weatherResponse = weatherResponse;
        this.listener = listener;
    }

    @Override
    public CityWeatherViewHolder createItemViewHolder(View view) {
        return new CityWeatherViewHolder(view);
    }

    @Override
    public void bindViewHolder(CityWeatherViewHolder holder) {
        holder.bind(weatherResponse, this);
    }

    @Override
    public ViewModelType getViewType() {
        return GETTY_IMAGE;
    }

    @Override
    public void onLongPress() {
        if (listener != null) {
            //listener.onImageLongPress(weather.getDescription(), weather.getThumbUri());
        }
    }

    public interface Listener {
        void onImageLongPress(String id, String uri);
    }
}
