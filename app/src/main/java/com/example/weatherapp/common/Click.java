package com.example.weatherapp.common;

import com.example.weatherapp.models.Root;

public interface Click<T> {
    void buttonClick(T data);
    void click(Root fragment);
}
