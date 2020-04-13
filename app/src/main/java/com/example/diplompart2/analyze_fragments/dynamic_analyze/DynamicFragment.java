package com.example.diplompart2.analyze_fragments.dynamic_analyze;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;
import com.example.diplompart2.analyze_fragments.static_analyze_2.TypeAtribute2;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DynamicFragment extends Fragment implements PropertyChangeListener {
    private EmployeeStatic1Database db = App.getInstance().getDatabase(); // получение базы данных
   // private EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao
    //ArrayList
    private static ArrayList<String> apkName = new ArrayList<>();
    private static ArrayList <String> apkFullName = new ArrayList<>();
    //MaterialSpiner
    private static MaterialSpinner spinner;
    //String
    private static String openApp = "";
    //Button
    private Button openAppButton;
    private Button sendInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dynamic, container, false);
        spinner =  root.findViewById(R.id.spinner);
        openAppButton = (Button) root.findViewById(R.id.open_app);
        sendInfo = root.findViewById(R.id.sendToServer);
        hookOfApp("sdsd", 3);

        openAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
                Intent launchIntent = Objects.requireNonNull(getContext()).getPackageManager().getLaunchIntentForPackage(openApp);
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
                else {
                    Snackbar.make(view, "Приложение не выбрано!" , Snackbar.LENGTH_LONG).show();
                }
            }
        });


        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Данные отправляються", Snackbar.LENGTH_LONG).show();
                saveTextMultithreading();

            }
        });



        return root;
    }


   // Получение информации о производа данных
    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("getApplication")) {
            // здесь будет процедура в отдельном потоке получение данных об приложениях
            getAppTitleFromRoom();
            setDataSpinner();
        }


    }

    // получение данных из бд
    private void getAppTitleFromRoom(){
        db.employeeStatic1Dao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new  Consumer<List<EmployeeStatic2>>() {
                    @Override
                    public void accept(List<EmployeeStatic2> employees) throws Exception {
                        for (int i =0; i <employees.size(); i++){
                            apkName.add(employees.get(i).apkName);
                            apkFullName.add(employees.get(i).apkFullName);
                        }
                        openApp = apkFullName.get(0);
                        spinner.setItems(apkName);
                        }
                });


    }
    // отображения данных в спиннере
    private void setDataSpinner(){

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                openApp = apkFullName.get(position);
                hookOfApp(openApp, position);
            }
        });
    }
    private void hookOfApp(String nameApp, int position){

    }
    private void stopAppHook(){}

    private void test(){

    }
    //запись и отправка данных на сервер
    private void saveText() throws IOException {
        SaveJson saveJson = new SaveJson(Objects.requireNonNull(getContext()).getApplicationContext());
        saveJson.jsonToObject();
        saveJson.setParamName();
        saveJson.write();
    }
    // использование другого потока
    private void saveTextMultithreading(){
        Observable.just(1)
                .subscribeOn(Schedulers.computation())
                .doOnNext(integer ->saveText())
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
                       // XposedBridge.log(e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");

                    }
                });
    }
}
