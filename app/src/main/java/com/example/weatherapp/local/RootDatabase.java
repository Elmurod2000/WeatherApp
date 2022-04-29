package com.example.weatherapp.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weatherapp.models.Root;

@Database(entities = {Root.class}, version = 40
        , exportSchema = false)
public abstract class RootDatabase extends RoomDatabase{
    public abstract RootDao rootDao();
}
