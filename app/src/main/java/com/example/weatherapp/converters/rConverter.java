package com.example.weatherapp.converters;

import androidx.room.TypeConverter;

import com.example.weatherapp.models.Weather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;


public class rConverter implements Serializable {
    @TypeConverter
    public String fromWeatherList(List<Weather> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Weather>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Weather> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Weather>>() {
        }.getType();
        List<Weather> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}