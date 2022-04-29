package com.example.weatherapp.di;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.weatherapp.data.WeatherAppApi;
import com.example.weatherapp.local.RootDao;
import com.example.weatherapp.local.RootDatabase;
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
    public static RootDatabase provideDatabase(Application context){
        return Room.databaseBuilder(context.getApplicationContext(),
                RootDatabase.class, "weather")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static RootDao provideDoa(RootDatabase database){
        return database.rootDao();
    }

    @Provides
    @Singleton
    public static Repository provideApiRepository(WeatherAppApi api, RootDao dao){
        return new Repository(api, dao);
    }
}
