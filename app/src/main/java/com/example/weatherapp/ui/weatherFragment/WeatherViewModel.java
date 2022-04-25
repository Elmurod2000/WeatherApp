package com.example.weatherapp.ui.weatherFragment;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.App;
import com.example.weatherapp.common.Resource;
import com.example.weatherapp.data.WeatherAppApi;
import com.example.weatherapp.models.Root;

public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<Root>> liveData;
    WeatherAppApi api;


    public void getWeatherByCityName(String city){
        liveData = App.repository.getWeatherInRussianByCityName(city);
    }
}
