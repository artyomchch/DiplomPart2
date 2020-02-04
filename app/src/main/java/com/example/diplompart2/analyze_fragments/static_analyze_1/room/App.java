package com.example.diplompart2.analyze_fragments.static_analyze_1.room;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    public static App instance;

    private EmployeeStatic1Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, EmployeeStatic1Database.class, "database")
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public EmployeeStatic1Database getDatabase() {
        return database;
    }
}
