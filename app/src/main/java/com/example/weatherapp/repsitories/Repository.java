package com.example.weatherapp.repsitories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.common.Resource;
import com.example.weatherapp.data.WeatherAppApi;
import com.example.weatherapp.local.RootDao;
import com.example.weatherapp.models.Root;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private WeatherAppApi api;
    private RootDao dao;
    private final String appIdKey = "9cf9459f078b5c949a18e7802541c42b";
    private final String units = "metric";

    @Inject
    public Repository(WeatherAppApi api, RootDao dao) {
        this.api = api;
        this.dao = dao;
    }

    public MutableLiveData<Resource<Root>> getWeatherInRussianByCityName(String city){
        MutableLiveData<Resource<Root>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getWeatherInRussianByCityName(city, appIdKey, units).enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call,
                                   @NonNull Response<Root> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
                    Root rootResponse = response.body();
                    rootResponse.setCreatedAt(System.currentTimeMillis());
                    dao.insertCurrent(response.body());
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

    public MutableLiveData<Resource<List<Root>>> getLocalSortedMainResponse(){
        MutableLiveData<Resource<List<Root>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getAllCurrentSorted()));
        return liveData;
    }
    public MutableLiveData<Resource<List<Root>>> getLocalFilteredMainResponse(
            String id){
        MutableLiveData<Resource<List<Root>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getCurrentById(id)));
        return liveData;
    }
}
