package com.example.diplompart2.analyze_fragments.static_analyze_1.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {EmployeeStatic1.class}, version = 1)
public abstract class EmployeeStatic1Database extends RoomDatabase {
    public abstract EmployeeStatic1Dao employeeStatic1Dao();
}
