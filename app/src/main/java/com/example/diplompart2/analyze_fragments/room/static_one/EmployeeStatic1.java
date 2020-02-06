package com.example.diplompart2.analyze_fragments.room.static_one;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EmployeeStatic1 {

    @PrimaryKey
    public int staticId;

    public Boolean root;

    public String model;

    public String system;

    public String imei;
}
