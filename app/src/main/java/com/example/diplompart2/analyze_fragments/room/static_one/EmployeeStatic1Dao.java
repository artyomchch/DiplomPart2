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
    // выборка по айди
    @Query("SELECT * FROM table_static_2 WHERE apkId = :id")
    EmployeeStatic2 getById(long id);

//    @Query("SELECT apkId, apkName,apkFullName FROM table_static_2")
//    EmployeeStatic2 getNameApp();

    @Query("SELECT * FROM table_static_1")
    Single<EmployeeStatic1> getAll2();

    //получение кол-во приложений
    @Query("SELECT COUNT(*) FROM table_static_2")
    Single<Integer> getRowCount();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EmployeeStatic2 employeeStatic2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStatic(EmployeeStatic1 employeeStatic1);

    @Update
    void update(EmployeeStatic1 employeeStatic1);

    @Delete
    void delete(EmployeeStatic2 employeeStatic2);

    @Query("DELETE FROM table_static_2")
    void deleteAllItems();
}
