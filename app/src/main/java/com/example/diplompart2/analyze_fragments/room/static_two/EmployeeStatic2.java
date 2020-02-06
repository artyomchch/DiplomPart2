package com.example.diplompart2.analyze_fragments.room.static_two;

import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity (tableName = "table_static_2")
public class EmployeeStatic2 {

    @PrimaryKey()
    public int apkId;

    public String apkName;

    public String apkFullName;

    public String apkVersion;

    public String apkPath;

    public String apkPermission;

    public EmployeeStatic2(int apkId, String apkName, String apkFullName, String apkVersion, String apkPath, String apkPermission) {
        this.apkId = apkId;
        this.apkName = apkName;
        this.apkFullName = apkFullName;
        this.apkVersion = apkVersion;
        this.apkPath = apkPath;
        this.apkPermission = apkPermission;
    }
}
