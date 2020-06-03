package com.example.ad340app_a1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SettingsDao {
    // Get settings from database
    @Query("SELECT * FROM settings")
    LiveData<List<Settings>> getAll();

    // Get settings of user via email
    @Query("SELECT * FROM settings WHERE email = :email")
    LiveData<List<Settings>> getSettingsById(String[] email);

    // Update db settings to app settings
    @Update
    void updateSettings(Settings... settings);

    // Insert settings
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSettings(Settings... settings);

    @Delete
    void deleteSettings(Settings settings);

}
