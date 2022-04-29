package com.example.weatherapp.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.weatherapp.models.Root;

import java.util.List;

@Dao
public interface RootDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrent(Root mainResponse);

    @Query("SELECT * FROM root  ORDER BY createdAt DESC")
    List<Root> getAllCurrentSorted();

    @Query("SELECT * FROM root WHERE name=:id")
    List<Root> getCurrentById(String id);
}
