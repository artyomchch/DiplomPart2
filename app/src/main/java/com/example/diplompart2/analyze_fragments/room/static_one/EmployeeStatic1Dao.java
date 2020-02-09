package com.example.diplompart2.analyze_fragments.room.static_one;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface EmployeeStatic1Dao {

    @Query("SELECT * FROM table_static_2")
    Flowable<List<EmployeeStatic2>> getAll();

    @Query("SELECT * FROM table_static_2 WHERE apkId = :id")
    //Flowable<EmployeeStatic2> getById(long id);
    EmployeeStatic2 getById(long id);

    @Query("SELECT * FROM table_static_1")
    Single<EmployeeStatic1> getAll2();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EmployeeStatic2 employeeStatic2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStatic(EmployeeStatic1 employeeStatic1);

    @Update
    void update(EmployeeStatic1 employeeStatic1);

    @Delete
    void delete(EmployeeStatic1 employeeStatic1);
}
