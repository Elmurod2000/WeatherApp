package com.example.weatherapp.repsitories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.common.Resource;
import com.example.weatherapp.data.WeatherAppApi;
import com.example.weatherapp.models.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private WeatherAppApi api;
    private final String appIdKey = "9cf9459f078b5c949a18e7802541c42b";
    private final String units = "metric";

    public Repository(WeatherAppApi api) {
        this.api=api;
    }

    public MutableLiveData<Resource<Root>> getWeatherInRussianByCityName(String city){
        System.out.println("---------3-----------");
        MutableLiveData<Resource<Root>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getWeatherInRussianByCityName(city, appIdKey, units).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call,
                                   @NonNull Response<Root> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
                }else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return liveData;
    }
}
