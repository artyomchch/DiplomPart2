package com.example.diplompart2.analyze_fragments.static_analyze_1.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EmployeeStatic1 {

    @PrimaryKey
    public int staticId;

    public String root;

    public String model;

    public String system;

    public String imei;
}
