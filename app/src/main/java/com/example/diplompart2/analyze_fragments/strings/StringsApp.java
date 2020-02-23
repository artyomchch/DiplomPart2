package com.example.diplompart2.analyze_fragments.strings;

import android.content.pm.PackageInfo;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class StringsApp {
    static Integer CountOfApp;
    static List<PackageInfo> packageList;
    // счеткик изменений
    static int VARIABLE_TO_OBSERVE = 0;



    // подписчик
    Subject<Integer> mObservable = PublishSubject.create();

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

    public static int getVariableToObserve() {
        return VARIABLE_TO_OBSERVE;
    }

    public static void setVariableToObserve(int variableToObserve) {
        VARIABLE_TO_OBSERVE = variableToObserve;
    }
    public Subject<Integer> getmObservable() {
        return mObservable;
    }

    public void setmObservable(Subject<Integer> mObservable) {
        this.mObservable = mObservable;
    }
}

