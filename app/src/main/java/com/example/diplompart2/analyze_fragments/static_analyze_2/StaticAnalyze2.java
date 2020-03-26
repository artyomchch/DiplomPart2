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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;
import com.example.diplompart2.analyze_fragments.static_analyze_1.retrofit.RetroPart1;
import com.example.diplompart2.analyze_fragments.static_analyze_2.permission.GetIntents;
import com.example.diplompart2.analyze_fragments.static_analyze_2.permission.GetPermission;
import com.example.diplompart2.analyze_fragments.room.Converters;
import com.example.diplompart2.analyze_fragments.strings.StringsApp;
import com.example.diplompart2.ui.home.HomeFragment;
import com.example.myloadingbutton.MyLoadingButton;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;

import com.google.gson.Gson;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Completable;
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

import static android.view.View.VISIBLE;


public class StaticAnalyze2 extends Fragment {
    //Rotate Loading
    private ProgressBar progressBar;
    private Sprite FadingCircle = new FadingCircle(); // sprite for animation
    //TAG observable static 2
    private static String TAG = "Observable Static 2";
    //TextViews
    private TextView CountApp, ProcApp;
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
    //apkpermission
    private ArrayList<String> appPermissionArray = new ArrayList<String>();
    //list of arrays

    private static List<TypeAtribute2> app = new ArrayList<TypeAtribute2>();
    //gson
    private Gson gson = new Gson();
    //URL
    private String URL = "https://aqueous-temple-55115.herokuapp.com";
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


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
        //Rotate Loading
        progressBar = root.findViewById(R.id.spin_kit2);
        //animation
        animation();

        //countApp
        CountApp.setText(String.valueOf(sa.getCountOfApp()));
        ProcApp.setText("0 / "+ String.valueOf(sa.getCountOfApp()));
       // employeeStatic1Dao.deleteAllItems();
        mainTask();




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
    private int getAppInfo(Integer num)  {

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
        appPermissionArray = getPermission.getPermission(notNormalPermissionView);
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
                .doOnNext(this::getAppInfo)
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
                        //progresbar
                        progressBar.setIndeterminateDrawable(FadingCircle);
                        Log.d(TAG, "All numbers emitted!");
                        FrameStat2.setBackground(Objects.requireNonNull(getContext())
                                .getDrawable(R.drawable.fragment_bg));
                        animation();
                        json = gson.toJson(app);
                        //Log.d(TAG, "onComplete: "+ json);
                        progressBar.setVisibility(VISIBLE);
                        returnData();




                    }
                });
    }
    //база данных (локальная/серверная)
    private void room(){

        List<TypeAtribute2> listss = new ArrayList<TypeAtribute2>();
        EmployeeStatic2 employeeStatic2 = new EmployeeStatic2(i+1,appName,appFullName,appVersion,appPatch,appPermissions);
        listss.add(new TypeAtribute2( appName, appFullName, appVersion, appPatch, appPermissionArray));

        app.add(new TypeAtribute2(i+1, listss));

        employeeStatic1Dao.insert(employeeStatic2);

        Log.d(TAG, "room and json: success");
    }
    //retrofit data
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
                        progressBar.setVisibility(View.INVISIBLE);
                        HomeFragment homeFragment = new HomeFragment();
                        addListener(homeFragment);
                        setVariable("ds");

                    }
                });
    }
    //retrofit
    private void retrofit(Integer integer){
        //Retrofit
         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //interface
        RetroPart1 intf = retrofit.create(RetroPart1.class);
        Call<Object> putPart2 = intf.getPart2("{\"apps\" : " + json + "}");
        try {
            Response<Object> reer = putPart2.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("MyTextProperty", oldValue, newValue);
    }


}
