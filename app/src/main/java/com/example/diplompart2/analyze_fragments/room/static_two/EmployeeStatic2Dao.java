package com.example.diplompart2.analyze_fragments.room.static_two;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface EmployeeStatic2Dao {
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(EmployeeStatic2 employeeStatic2);
}
