package com.example.weatherapp.data;

import com.example.weatherapp.models.Root;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAppApi {
    @GET("/data/2.5/weather")
    Call<Root> getWeatherInRussianByCityName(
            @Query("q") String city,
            @Query("appid") String app_id,
            @Query("units") String units);
}
