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

    public CityWeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(WeatherResponse.Weather weather, LongPressGestureDetector.Listener listener) {
        GestureDetectorCompat gestureDetector = new GestureDetectorCompat(itemView.getContext(), new LongPressGestureDetector(listener));

        itemView.setOnTouchListener((v, event) -> {
            gestureDetector.onTouchEvent(event);
            return true;
        });

        cityName.setText(weather.getDescription());
        Glide.with(itemView.getContext())
                .load(String.format(itemView.getContext().getString(R.string.imageUrlPrefix),weather.getIcon()))
                .into(imageView);
    }
}
