package com.example.weatherapp.ui.weatherFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.common.Resource;
import com.example.weatherapp.models.Root;
import com.example.weatherapp.repsitories.Repository;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<Root>> liveData;
    public LiveData<Resource<List<Root>>> liveDataRoom;
    private final Repository repository;

    @Inject
    public WeatherViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getWeatherByCityName(String city){
        liveData = repository.getWeatherInRussianByCityName(city);
    }

    public void getLocalCurrentWeather(String id){
        if (Objects.requireNonNull(repository.getLocalFilteredMainResponse(id).getValue()).data.size()>0) {
            liveDataRoom = repository.getLocalFilteredMainResponse(id);
        } else {
            getLocalLastMainResponse();
        }
    }

    public void getLocalLastMainResponse(){
        liveDataRoom = repository.getLocalSortedMainResponse();
    }
}
