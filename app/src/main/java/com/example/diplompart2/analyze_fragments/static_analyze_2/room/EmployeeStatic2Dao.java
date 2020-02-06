package com.example.diplompart2.analyze_fragments.static_analyze_2.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.diplompart2.analyze_fragments.static_analyze_1.room.EmployeeStatic1;

@Dao
public interface EmployeeStatic2Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EmployeeStatic2 employeeStatic2);

}
