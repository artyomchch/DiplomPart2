package com.example.diplompart2.analyze_fragments.static_analyze_2.room;

import android.app.Application;

import androidx.room.Room;

public class SecondApp extends Application {
    public static SecondApp instance;

    private EmployeeStatic2Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, EmployeeStatic2Database.class, "database")
                .build();
    }



    public static SecondApp getInstance() {
        return instance;
    }

    public EmployeeStatic2Database getDatabase() {
        return database;
    }
}
