package com.example.openweather.ui.viewholder;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.openweather.R;
import com.example.openweather.api.model.City;
import com.example.openweather.api.model.ImageResult;
import com.example.openweather.api.model.WeatherResponse;
import com.example.openweather.util.LongPressGestureDetector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityWeatherViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.city_name)
    TextView cityName;

    @BindView(R.id.weather_status)
    TextView weatherStatus;

    @BindView(R.id.weather_temp)
    TextView temperatureTextView;

    @BindView(R.id.weather_pressure)
    TextView pressureTextView;

    @BindView(R.id.weather_humidity)
    TextView humidityTextView;


    public CityWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(WeatherResponse weatherResponse, LongPressGestureDetector.Listener listener) {
        GestureDetectorCompat gestureDetector = new GestureDetectorCompat(itemView.getContext(), new LongPressGestureDetector(listener));

        itemView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });

        WeatherResponse.Weather weather = weatherResponse.getWeatherList().get(0);

        weatherStatus.setText(weather.getDescription());
        temperatureTextView.setText(""+weatherResponse.getMain().getTemp());
        humidityTextView.setText(""+weatherResponse.getMain().getHumidity());
        pressureTextView.setText(""+weatherResponse.getMain().getPressure());
        cityName.setText(weatherResponse.getName());
        if (weather.getIcon() != null) {
            Glide.with(itemView.getContext())
                    .load(String.format(itemView.getContext().getString(R.string.imageUrlPrefix), weather.getIcon()))
                    .into(imageView);
        }
    }
}
