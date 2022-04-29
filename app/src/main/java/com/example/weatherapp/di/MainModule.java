package com.example.weatherapp.di;

import androidx.lifecycle.ViewModel;

import com.example.weatherapp.data.WeatherAppApi;
import com.example.weatherapp.repsitories.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class MainModule {

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(OkHttpClient client){
        return  new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public static WeatherAppApi provideApi(Retrofit retrofit){
        return retrofit.create(WeatherAppApi.class);
    }

    @Provides
    @Singleton
    public static Repository provideApiRepository(WeatherAppApi api){
        return new Repository(api);
    }

}
