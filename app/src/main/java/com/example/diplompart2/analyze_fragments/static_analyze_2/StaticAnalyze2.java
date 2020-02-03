package com.example.diplompart2.analyze_fragments.static_analyze_2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.strings.StringsApp;
import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StaticAnalyze2 extends Fragment {

    //CompositeDisposable
    private CompositeDisposable disposable = new CompositeDisposable();
    //TAG observable static 2
    private static String TAG = "Observable Static 2";
    //TextView
    private TextView CountApp, ProcApp;
    // Animation
    private AnimationDrawable animationDrawable;
    // FrameLayout
    private FrameLayout FrameStat2;
    //PackageList
    //List<PackageInfo> packagelist;
    //GlobalStrings
    StringsApp sa = new StringsApp();
    //Counter
    private static int i;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_static_analyze2, container, false);
        //TextView
        CountApp = root.findViewById(R.id.countApp);
        ProcApp = root.findViewById(R.id.processedApp);
        // FrameLayout
        FrameStat2 = root.findViewById(R.id.stat2);

        //animation
        animation();
        //countApp
        CountApp.setText(String.valueOf(sa.getCountOfApp()));
        //packageList
       // List<PackageInfo> mypck;

       // packagelist = Objects.requireNonNull(getActivity()).getPackageManager()
        //        .getInstalledPackages(0);
      //  mypck = packagelist;

        Stopwatch stopwatch = Stopwatch.createStarted();
        mainTask();
        stopwatch.stop();
        Log.e("second Task", "onCreateView: " + stopwatch );

        return root;
    }



    //Анимация
    private void animation(){

        animationDrawable = (AnimationDrawable) FrameStat2.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();

    }
    //Главная задача
    private int idk(Integer num){
        PackageInfo packageInfo=sa.getPackageList().get(num);
        //StringsApp of app
        String appName = packageInfo.applicationInfo.loadLabel(Objects.requireNonNull(getActivity())
                .getPackageManager()).toString();  // Название приложения
        String appPatch = packageInfo.applicationInfo.sourceDir; // путь к приложению
        String appFullName = packageInfo.packageName; //тех. названия приложения
        String appVersion = packageInfo.versionName;  //версия приложения


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appName + " " + appPatch + " " + appFullName + " " + appVersion + "\n");

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // ProcApp.setText(stringBuilder);
       // i++;
    //    Log.e(TAG, i + "   ->   " + num + ") " + stringBuilder + " "+ Thread.currentThread().getName());



        return num;
    }
    //Показатель
    private int setText(Integer integer){

        i++;
        ProcApp.setText(i + " / " + sa.getCountOfApp()+ " -> " + Thread.currentThread().getName());
        if (i == sa.getCountOfApp()){
            i = 0;
            FrameStat2.setBackground(Objects.requireNonNull(getContext())
                    .getDrawable(R.drawable.fragment_bg));
            animation();

        }
        return integer;
    }
    //Асихроность
    private void mainTask(){
        //permission
        Observable.range(1, sa.getPackageList().size())
                .subscribeOn(Schedulers.computation())
                .doOnNext(this::idk)
                .filter(new Predicate<Integer>() {   // Фильтр не нужных приложений
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        PackageInfo packageInfo=sa.getPackageList().get(integer);
                        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 ;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::setText)
                .subscribe(new DisposableObserver<Integer>  () {
                    int countApp= 0;
                    @Override
                    public void onNext(Integer integer) {
                        countApp++;
                        Log.d(TAG, "Number: "+ countApp  + " number App: "+ integer + " " );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "All numbers emitted!");

                    }
                });
    }


}
