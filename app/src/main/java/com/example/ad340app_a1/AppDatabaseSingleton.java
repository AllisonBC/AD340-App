package com.example.ad340app_a1;


import android.content.Context;

import androidx.room.Room;

public class AppDatabaseSingleton {

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context context) {
        if(db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "settings_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}
