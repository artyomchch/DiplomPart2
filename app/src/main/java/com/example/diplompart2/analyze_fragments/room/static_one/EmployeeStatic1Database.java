package com.example.diplompart2.analyze_fragments.room.static_one;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2Dao;

@Database(entities = {EmployeeStatic1.class, EmployeeStatic2.class}, version = 1)
public abstract class EmployeeStatic1Database extends RoomDatabase {
    public abstract EmployeeStatic1Dao employeeStatic1Dao();
    public abstract EmployeeStatic2Dao employeeStatic2Dao();
}
