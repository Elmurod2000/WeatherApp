package com.example.weatherapp.ui.weatherFragment;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.App;
import com.example.weatherapp.common.Resource;
import com.example.weatherapp.data.WeatherAppApi;
import com.example.weatherapp.models.Root;
import com.example.weatherapp.repsitories.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<Root>> liveData;

    private final Repository repository;

    @Inject
    public WeatherViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getWeatherByCityName(String city){
        liveData = repository.getWeatherInRussianByCityName(city);
    }
}
