package com.example.diplompart2.analyze_fragments.static_analyze_2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;
import com.example.diplompart2.analyze_fragments.static_analyze_1.retrofit.RetroPart1;
import com.example.diplompart2.analyze_fragments.static_analyze_2.permission.GetIntents;
import com.example.diplompart2.analyze_fragments.static_analyze_2.permission.GetPermission;
import com.example.diplompart2.analyze_fragments.room.Converters;
import com.example.diplompart2.analyze_fragments.strings.StringsApp;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StaticAnalyze2 extends Fragment {
    //TAG observable static 2
    private static String TAG = "Observable Static 2";
    //TextViews
    private TextView CountApp, ProcApp;
    //Drawable
    private Drawable appIcon;
    //Strings of app
    private String appName, appFullName, appPatch, appVersion, appPermissions;
    //Json
    private String json;
    // Animation
    private AnimationDrawable animationDrawable;
    // FrameLayout
    private FrameLayout FrameStat2;
    //GlobalStrings
    private StringsApp sa = new StringsApp();
    //Counter
    private static int i;
    //Database
    private EmployeeStatic1Database db = App.getInstance().getDatabase();  // получение базы данных
    private EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao
    //map
    private Map<String, String> mapJson = new HashMap<String, String>();
    //apkpermission
    ArrayList<String> apkPermissionArray = new ArrayList<String>();
    //list of arrays
    protected static List<tY> listss = new ArrayList<EmployeeStatic2>();
    //gson
    private Gson gson = new Gson();
    //URL
    private String URL = "https://aqueous-temple-55115.herokuapp.com";
    //Retrofit
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //interface
    private RetroPart1 intf = retrofit.create(RetroPart1.class);

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
        ProcApp.setText("0 / "+ String.valueOf(sa.getCountOfApp()) + " -> main");
        //stopwatch
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
    private int mainVoid(Integer num) throws PackageManager.NameNotFoundException {

        PackageInfo packageInfo=sa.getPackageList().get(num);
        //StringsApp of app
        appName = packageInfo.applicationInfo.loadLabel(Objects.requireNonNull(getActivity())
                .getPackageManager()).toString();  // Название приложения
        Log.d(TAG, "App Name: " + appName);
        appPatch = packageInfo.applicationInfo.sourceDir; // путь к приложению
        appFullName = packageInfo.packageName; //тех. названия приложения
        appVersion = packageInfo.versionName;  //версия приложения
      //  appIcon = getActivity().getPackageManager().getApplicationIcon(appFullName); // получение иконки

        //get Intents of app and normal view of xml file manifest
        GetIntents getIntents = new GetIntents();
        String notNormalPermissionView = getIntents.getIntents(appPatch);
        //get normal permission view
        GetPermission getPermission = new GetPermission();
        // json формат разрешений списка разрешений
        appPermissions = Converters.fromArrayList(getPermission.getPermission(notNormalPermissionView)); // json формат разрешений
        apkPermissionArray = getPermission.getPermission(notNormalPermissionView);
        room(); // запись в бд

        return num;
    }

    //Асихроность
    private void mainTask(){
        //permission
        Observable.range(0, sa.getPackageList().size())
                .subscribeOn(Schedulers.computation())
                .filter(new Predicate<Integer>() {   // Фильтр не нужных приложений
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        PackageInfo packageInfo=sa.getPackageList().get(integer);
                        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 ;
                    }
                })
                .doOnNext(this::mainVoid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Integer>  () {
                    int countApp= 0;
                    @Override
                    public void onNext(Integer integer) {
                        countApp++;
                        i++;
                        ProcApp.setText(i + " / " + sa.getCountOfApp());
                        Log.d(TAG, "Number: "+ countApp  + " number App: "+ integer + " " );

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ",e );
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "All numbers emitted!");
                        FrameStat2.setBackground(Objects.requireNonNull(getContext())
                                .getDrawable(R.drawable.fragment_bg));
                        animation();
                        json = gson.toJson(listss);
                        //Log.d(TAG, "onComplete: "+ json);
                        returnData();


                    }
                });
    }
    //база данных (локальная)
    private void room(){
        EmployeeStatic2 employeeStatic2 = new EmployeeStatic2(i+1,appName,appFullName,appVersion,appPatch,appPermissions);
//        mapJson.put("apk_name", appName);
//        mapJson.put("apkFullName" , appFullName);
//        mapJson.put("apk_version", appVersion);
//        mapJson.put("apk_path", appPatch);
//        mapJson.put("apk_permission", appPermissions);
       // listss.add(new EmployeeStatic2(i+1, appName,appFullName,appVersion,appPatch,appPermissions));
        listss.add(new TypeAtribute2(appName, appFullName, appVersion, appPatch, apkPermissionArray));

        employeeStatic1Dao.insert(employeeStatic2);

        Log.d(TAG, "room and json: success");
    }

    private void returnData(){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::retrofit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e );
                    }

                    @Override
                    public void onComplete() {
                        Log.d("aaaaaaaaaaaaaa", "onComplete: Succses");


                    }
                });
    }


    public void retrofit(Integer integer){
        Call<Object> putPart2 = intf.getPart2("{\"app\" : " + json + "}");
        try {
            Response<Object> reer = putPart2.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
