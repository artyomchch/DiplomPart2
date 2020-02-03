package com.example.diplompart2.analyze_fragments.static_analyze_2;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class TypeAtribute {
    private int applicationNumber;
    private String fullAppName;
    private String nameApp;
    private String versionApp;
    private List<String> permission = new ArrayList<>();
    private Drawable icon;

    public TypeAtribute(int applicationNumber, String fullAppName, String nameApp, String versionApp, List<String> permission, Drawable icon) {
        this.applicationNumber = applicationNumber;
        this.fullAppName = fullAppName;
        this.nameApp = nameApp;
        this.versionApp = versionApp;
        this.permission = permission;
        this.icon = icon;
    }

    public int getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(int applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public String getFullAppName() {
        return fullAppName;
    }

    public void setFullAppName(String fullAppName) {
        this.fullAppName = fullAppName;
    }

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    public String getVersionApp() {
        return versionApp;
    }

    public void setVersionApp(String versionApp) {
        this.versionApp = versionApp;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
