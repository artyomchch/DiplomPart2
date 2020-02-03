package com.example.diplompart2.analyze_fragments.strings;

import android.content.pm.PackageInfo;

import java.util.List;

public class StringsApp {
    static Integer CountOfApp;
    static List<PackageInfo> packageList;


    public Integer getCountOfApp() {
        return CountOfApp;
    }

    public void setCountOfApp(Integer countOfApp) {
        CountOfApp = countOfApp;
    }

    public List<PackageInfo> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<PackageInfo> packageList) {
        StringsApp.packageList = packageList;
    }
}

