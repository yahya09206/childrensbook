package com.example.yhussein.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM setting")
    List<Setting> getAllBooks();

    @Query("SELECT * FROM setting WHERE photo_id IN (:photoids)")
    List<Setting> loadBookById(String[] photoids);

    @Update
    void updateSettings(Setting... settings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Setting... settings);

    @Delete
    void delete(Setting setting);
}

