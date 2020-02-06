package com.example.diplompart2.analyze_fragments.static_analyze_2.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {EmployeeStatic2.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class EmployeeStatic2Database extends RoomDatabase {
    public abstract EmployeeStatic2Dao employeeStatic2Dao();
}


