package com.example.diplompart2.analyze_fragments.room.static_one;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface EmployeeStatic1Dao {

    @Query("SELECT * FROM employeestatic1")
    List<EmployeeStatic1> getAll();

    @Query("SELECT * FROM employeestatic1 WHERE staticId = :id")
    EmployeeStatic1 getById(long id);

    @Query("SELECT * FROM employeestatic1")
    Single<EmployeeStatic1> getAll2();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EmployeeStatic1 employeeStatic1);

    @Update
    void update(EmployeeStatic1 employeeStatic1);

    @Delete
    void delete(EmployeeStatic1 employeeStatic1);
}
