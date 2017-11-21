package com.example.openweather.ui.viewmodel;

public enum ViewModelType {
    GETTY_IMAGE;

    public int getId() {
        return ordinal();
    }
}
