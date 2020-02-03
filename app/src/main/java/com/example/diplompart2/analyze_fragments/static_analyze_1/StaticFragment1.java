package com.example.diplompart2.analyze_fragments.static_analyze_1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diplompart2.MainActivity;
import com.example.diplompart2.R;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.common.base.Stopwatch;

import java.io.File;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivities;
import static com.example.diplompart2.analyze_fragments.static_analyze_1.check_root.CheckRoot.isRooted;


public class StaticFragment1 extends Fragment {

    //TextView ..
    private TextView model, rootT , version, imei;
    // Animation
    private AnimationDrawable animationDrawable;
    // FrameLayout
    private FrameLayout FrameStat1;
    // ImageView
    ImageView RootView;
    //boolRoot
    private Boolean BoolRoot;
    //Imei
    private String getIMEI;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_static_fragment_1, container, false);


        //TextView
        model = root.findViewById(R.id.modelText);
        version = root.findViewById(R.id.modelVersion);
        imei = root.findViewById(R.id.IMEI);
        rootT =root.findViewById(R.id.rootView);
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
        getIMEI = telephonyManager.getImei(0);
        //imei.setText(telephonyManager.getImei(0));
        BoolRoot = isRooted();
        stopwatch.stop();
        Log.e("first task get Imei", "manafacture: " + stopwatch  + "  ->  "+ Thread.currentThread().getName());
        return integer;
    }

    private void animation() {
        animationDrawable = (AnimationDrawable) FrameStat1.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();
    }

    private int setTextValue(Integer integer){


        model.setText(Build.MANUFACTURER + " " + Build.MODEL);
        version.setText("Android " +Build.VERSION.RELEASE);
        imei.setText(getIMEI);
        if (!BoolRoot){
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
}
