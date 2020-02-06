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
import com.example.diplompart2.analyze_fragments.static_analyze_2.permission.GetIntents;
import com.example.diplompart2.analyze_fragments.static_analyze_2.permission.GetPermission;
import com.example.diplompart2.analyze_fragments.room.Converters;
import com.example.diplompart2.analyze_fragments.strings.StringsApp;
import com.google.common.base.Stopwatch;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class StaticAnalyze2 extends Fragment {

    private byte[] app;
    //TAG observable static 2
    private static String TAG = "Observable Static 2";
    //TextViews
    private TextView CountApp, ProcApp;
    //ArrayList
   // private ArrayList<String> appPermission = new ArrayList<>();
    //Drawable
    private Drawable appIcon;
    //Strings of app
    private String appName, appFullName, appPatch, appVersion, appPermissions;
    // Animation
    private AnimationDrawable animationDrawable;
    // FrameLayout
    private FrameLayout FrameStat2;
    //GlobalStrings
    StringsApp sa = new StringsApp();
    //Some xml file in String var
    private String xmlString;
    //Counter
    private static int i;
    EmployeeStatic1Database db = App.getInstance().getDatabase();  // получение базы данных
    EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao

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
      //  app = appDrawToByte(appIcon); // получение иконки в байтовой версии
     //   Log.d(TAG, "mainVoid: ");


        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
       // bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        //get Intents of app and normal view of xml file manifest
        GetIntents getIntents = new GetIntents();
        String notNormalPermissionView = getIntents.getIntents(appPatch);
        //get normal permission view
        GetPermission getPermission = new GetPermission();
        // json формат разрешений списка разрешений
        appPermissions = Converters.fromArrayList(getPermission.getPermission(notNormalPermissionView)); // json формат разрешений

        room(); // запись в бд


      //  StringBuilder stringBuilder = new StringBuilder();
      //  stringBuilder.append(appName + " " + appPatch + " " + appFullName + " " + appVersion + "\n");

//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

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
                .filter(new Predicate<Integer>() {   // Фильтр не нужных приложений
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        PackageInfo packageInfo=sa.getPackageList().get(integer);
                        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0 ;
                    }
                })
                .doOnNext(this::mainVoid)
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
    //база данных (локальная)
    private void room(){

        EmployeeStatic2 employeeStatic2 = new EmployeeStatic2(i+1,appName,appFullName,appVersion,appPatch,appPermissions);
        employeeStatic1Dao.insert(employeeStatic2);
        Log.d(TAG, "room: ");
    }
    //converter draw to byte[]
    private byte[] appDrawToByte(Drawable d){
      // Drawable d; // the drawable (Captain Obvious, to the rescue!!!)

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        getBitmapFromDrawable(d).compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @NonNull
    private Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bmp);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bmp;
    }

}
