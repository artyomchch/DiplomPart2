package com.example.diplompart2.analyze_fragments.room.static_two;

import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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


    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkFullName() {
        return apkFullName;
    }

    public void setApkFullName(String apkFullName) {
        this.apkFullName = apkFullName;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getApkPermission() {
        return apkPermission;
    }

    public void setApkPermission(String apkPermission) {
        this.apkPermission = apkPermission;
    }


}
