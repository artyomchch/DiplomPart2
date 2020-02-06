package com.example.diplompart2.analyze_fragments.static_analyze_2.room;

import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class EmployeeStatic2 {

    @PrimaryKey
    public int apkId;

    public String apkName;

    public String apkFullName;

    public String apkVersion;

    public String apkPath;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) // image
    public byte[] apkImage;

    //public ArrayList<String> apkPermission = new ArrayList<>();
}
