package com.example.diplompart2.analyze_fragments.static_analyze_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.google.common.base.Stopwatch;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.core.content.ContextCompat.startActivities;
import static com.example.diplompart2.analyze_fragments.static_analyze_1.check_root.CheckRoot.isRooted;


public class StaticFragment1 extends Fragment {

    //TextView ..
    private TextView model, version, imei;
    // Animation
    private AnimationDrawable animationDrawable;
    // FrameLayout
    private FrameLayout FrameStat1;
    // ImageView
    private ImageView RootView;
    //boolRoot
    private Boolean boolRoot;
    //Imei
    private String getIMEI;
    //model
    private String getModel;
    //version
    private String getVersion;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_static_fragment_1, container, false);



        //TextView
        model = root.findViewById(R.id.modelText);
        version = root.findViewById(R.id.modelVersion);
        imei = root.findViewById(R.id.IMEI);
        // FrameLayout
        FrameStat1 = root.findViewById(R.id.stat1);
        // ImageView
        RootView = root.findViewById(R.id.rootImage);

        //animation
        animation();

        //get manafacture ..
        Stopwatch stopwatch = Stopwatch.createStarted();
        returnData();
        stopwatch.stop();
        Log.e("first task", "onCreateView: " + stopwatch );

        return root;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private int manafacture(Integer integer){

        Stopwatch stopwatch = Stopwatch.createStarted();
        TelephonyManager telephonyManager = (TelephonyManager) Objects.requireNonNull(getContext()).
                getSystemService(Context.TELEPHONY_SERVICE);
        assert telephonyManager != null;
        getIMEI = telephonyManager.getImei(0); // номер imei
        //imei.setText(telephonyManager.getImei(0));
        boolRoot = isRooted(); // есть ли рут
        getModel = Build.MANUFACTURER + " " + Build.MODEL;
        getVersion = "Android " +Build.VERSION.RELEASE;
        stopwatch.stop();
        Log.e("first task get Imei", "manafacture: " + stopwatch  + "  ->  "+ Thread.currentThread().getName());

        //database
        room();

        return integer;
    }

    private void animation() {
        animationDrawable = (AnimationDrawable) FrameStat1.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();
    }

    private int setTextValue(Integer integer){

        model.setText(getModel);
        version.setText(getVersion);
        imei.setText(getIMEI);
        TypeAtributes1 ta1 = new TypeAtributes1(getModel, getIMEI, getVersion, boolRoot);
        if (!boolRoot){
            RootView.setImageResource(R.drawable.quit);
        }
        FrameStat1.setBackground(Objects.requireNonNull(getContext())
                .getDrawable(R.drawable.fragment_bg));
        animation();


        return integer;

    }

    private void returnData(){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::manafacture)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::setTextValue)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void room(){

//        EmployeeStatic1Database db =  Room.databaseBuilder(Objects.requireNonNull(getContext()).getApplicationContext(),
//                EmployeeStatic1Database.class, "database").build();  // получение базы данных
        EmployeeStatic1Database db = App.getInstance().getDatabase();
        EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao

        //передаем в таблицу
        EmployeeStatic1 employeeStatic1 = new EmployeeStatic1();
        employeeStatic1.staticId = 1;
        employeeStatic1.root = boolRoot;
        employeeStatic1.model = getModel;
        employeeStatic1.system = getVersion;
        employeeStatic1.imei = getIMEI;
        Log.d("ds", "room: check  " + Thread.currentThread().getName());
        employeeStatic1Dao.insert(employeeStatic1);
        Log.d("ds", "room: second check " + Thread.currentThread().getName());
       // List<EmployeeStatic1> employees = employeeStatic1Dao.getAll();
        //Log.e("Database", "room: " + employees );


    }
}