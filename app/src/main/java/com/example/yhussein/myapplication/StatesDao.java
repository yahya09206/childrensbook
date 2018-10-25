package com.example.yhussein.myapplication;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StatesDao {
    @Query("SELECT * FROM state")
    List<State> getAllStates();

    @Query("SELECT * FROM state WHERE stateid IN (:stateids)")
    List<State> loadStateById(String[] stateids);

    @Update
    void updateStates(State... states);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(State... states);

    @Delete
    void delete(State state);
}
