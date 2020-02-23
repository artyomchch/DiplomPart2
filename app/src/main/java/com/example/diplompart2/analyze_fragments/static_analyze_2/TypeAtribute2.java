package com.example.diplompart2.analyze_fragments.static_analyze_2;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class TypeAtribute2 {

    private String nameApp;
    private String packageName;
    private String versionApp;
    private String pathApp;
    private Drawable icon;
    private String permission;
    private ArrayList<String> permissionArray;
    private boolean expanded;


    public TypeAtribute2(String nameApp, String packageName, String versionApp, String pathApp, Drawable icon, String permission) {
        this.nameApp = nameApp;
        this.packageName = packageName;
        this.versionApp = versionApp;
        this.pathApp = pathApp;
        this.icon = icon;
        this.permission = permission;
        this.expanded = expanded;
    }

    public TypeAtribute2(String nameApp, String packageName, String versionApp, String pathApp, ArrayList<String> permissionArray){
        this.nameApp = nameApp;
        this.packageName = packageName;
        this.versionApp = versionApp;
        this.pathApp = pathApp;
        this.permissionArray = permissionArray;
    }



    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getPathApp() {
        return pathApp;
    }

    public void setPathApp(String pathApp) {
        this.pathApp = pathApp;
    }
}
