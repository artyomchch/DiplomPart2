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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.example.diplompart2.analyze_fragments.static_analyze_1.retrofit.RetroPart1;
import com.example.diplompart2.analyze_fragments.static_analyze_2.TypeAtribute2;
import com.example.diplompart2.analyze_fragments.strings.StringsApp;
import com.example.diplompart2.ui.home.HomeFragment;
import com.github.ybq.android.spinkit.sprite.CircleSprite;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.view.View.VISIBLE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static androidx.core.content.ContextCompat.startActivities;
import static com.example.diplompart2.analyze_fragments.static_analyze_1.check_root.CheckRoot.isRooted;


public class StaticFragment1 extends Fragment {



    //Rotate Loading
    private ProgressBar progressBar;
    private Sprite FadingCircle = new FadingCircle(); // sprite for animation

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
    //URL
    private String URL = "https://aqueous-temple-55115.herokuapp.com";
    //Gson
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
    //Retrofit
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //interface
    private RetroPart1 intf = retrofit.create(RetroPart1.class);

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
        //Rotate Loading
        progressBar = root.findViewById(R.id.spin_kit);
        //animation
        animation();
        progressBar.setIndeterminateDrawable(FadingCircle);


        //get manafacture ..
        Stopwatch stopwatch = Stopwatch.createStarted();
        returnData();
        stopwatch.stop();
        Log.e("first task", "onCreateView: " + stopwatch );

        return root;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    private void manafacture(Integer integer){

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



    }

    private void animation() {
        animationDrawable = (AnimationDrawable) FrameStat1.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();

    }

    private void setTextValue(Integer integer){

        progressBar.setVisibility(VISIBLE);

        model.setText(getModel);
        model.setVisibility(View.VISIBLE);
        version.setText(getVersion);
        version.setVisibility(View.VISIBLE);
        imei.setText(getIMEI);
        imei.setVisibility(View.VISIBLE);
        TypeAtributes1 ta1 = new TypeAtributes1(getModel, getIMEI, getVersion, boolRoot);
        if (!boolRoot){
            RootView.setImageResource(R.drawable.quit);
        }
        FrameStat1.setBackground(Objects.requireNonNull(getContext())
                .getDrawable(R.drawable.fragment_bg));
        animation();

    }

    private void returnData(){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::manafacture)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::setTextValue)
                .observeOn(Schedulers.io())
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
                        Log.d(TAG, "onComplete: Succses");
                        progressBar.setVisibility(View.INVISIBLE);



                    }
                });
    }
    //room
    private void room(){


        EmployeeStatic1Database db = App.getInstance().getDatabase();  // получение базы данных
        EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao

        //передаем в таблицу
        EmployeeStatic1 employeeStatic1 = new EmployeeStatic1();
        employeeStatic1.staticId = 1;
        employeeStatic1.root = boolRoot;
        employeeStatic1.model = getModel;
        employeeStatic1.system = getVersion;
        employeeStatic1.imei = getIMEI;
        Log.d("ds", "room: check  " + Thread.currentThread().getName());
       // employeeStatic1Dao.delete(employeeStatic1);
        employeeStatic1Dao.insertStatic(employeeStatic1);
        Log.d("ds", "room: second check " + Thread.currentThread().getName());


    }
    //retrofit
    private void retrofit(Integer integer){
        //database
        room();

        Map<String, String> mapJson = new HashMap<String, String>();
        mapJson.put("staticid", "1");
        mapJson.put("root", boolRoot.toString());
        mapJson.put("model", getModel);
        mapJson.put("system", getVersion);
        mapJson.put("imei", getIMEI);



        Log.d(TAG, "retrofit: " + mapJson);
        Gson gson = new Gson();
        String json = gson.toJson(mapJson);
        Log.d(TAG, "retrofit: " + json);
        Call<Object> putPart1 = intf.getPart1(json);

        Log.d(TAG, "retrofit: " + putPart1);


        try {
            Response<Object> reer = putPart1.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

   }



}
