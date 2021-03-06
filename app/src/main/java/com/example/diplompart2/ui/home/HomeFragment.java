package com.example.diplompart2.ui.home;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.dynamic_analyze.DynamicFragment;
import com.example.diplompart2.analyze_fragments.dynamic_analyze.SaveJson;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.example.diplompart2.analyze_fragments.static_analyze_1.StaticFragment1;
import com.example.diplompart2.analyze_fragments.static_analyze_2.StaticAnalyze2;
import com.example.diplompart2.analyze_fragments.strings.StringsApp;
import com.example.myloadingbutton.MyLoadingButton;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.snackbar.Snackbar;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.VISIBLE;

public class HomeFragment extends Fragment implements MyLoadingButton.MyLoadingButtonClick, PropertyChangeListener {
    //Database
    private EmployeeStatic1Database db = App.getInstance().getDatabase();  // получение базы данных
    private EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;

    // Fragments
    private static Fragment fragmentStatic1, fragmentStatic2, fragmentDynamic;
    private FragmentTransaction fragmentTransaction;
    // FrameLayout
    private static FrameLayout frameDyna;

    private HomeViewModel homeViewModel;
    private static final String TAG = "MaimActivity";
    private static MyLoadingButton myLoadingButton;

    //TextView
    private static TextView dynaText, statText, warText;
    //ImageView
    private ImageView dotLine;
    //Listener
    private String variable = "Initial";
    private PropertyChangeSupport support = new PropertyChangeSupport(this);


    //Rotate Loading
    //private static ProgressBar progressBar;
   // private ProgressBar progressBar2;
   // private Sprite Pulse = new Pulse(); // sprite for animation

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //TextView
        dynaText = root.findViewById(R.id.text_dyna);
        statText = root.findViewById(R.id.text_stat);
        warText = root.findViewById(R.id.war_stat);
        //ImageView
        dotLine = root.findViewById(R.id.dot_line);
        //FrameLayout
        frameDyna = root.findViewById(R.id.DynamicFrameLayout);


        //Loading button
        myLoadingButton = root.findViewById(R.id.my_loading_button);
        myLoadingButton.setMyButtonClickListener(this);

        //fragments
       fragmentStatic1 = new StaticFragment1();
       fragmentStatic2 = new StaticAnalyze2();
       fragmentDynamic = new DynamicFragment();


        multiThread();

        return root;
    }


    @Override
    public void onMyLoadingButtonClick() {

        myLoadingButton.showLoadingButton();
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.button_go_down);

        myLoadingButton.setY(1650);
        myLoadingButton.setAnimation(a);

        dynaText.setVisibility(VISIBLE);
        statText.setVisibility(VISIBLE);
        dotLine.setVisibility(VISIBLE);
        warText.setVisibility(VISIBLE);
       // progressBar.setVisibility(View.VISIBLE);

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                progressBar.setVisibility(View.VISIBLE);
//                progressBar2.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadFragments();
//                progressBar.setVisibility(View.INVISIBLE);
//                progressBar2.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
    //загрузка фрагментов
    private void loadFragments(){

        fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in_custom, android.R.anim.fade_out)
                .replace(R.id.StaticFrameLayout, fragmentStatic1);
        fragmentTransaction.setCustomAnimations(R.anim.fade_in_custom, android.R.anim.fade_out)
                .replace(R.id.StaticFrameLayout2, fragmentStatic2);
        fragmentTransaction.setCustomAnimations(R.anim.fade_in_custom, android.R.anim.fade_out)
                .replace(R.id.DynamicFrameLayout, fragmentDynamic)
                .commit();


    }
    //запрос на разрешения использования мобильной сети, передача данных в другие классы
    private int setGlobalStrings(Integer integer){
        //clear database
        employeeStatic1Dao.deleteAllItems();
        //Check Permission
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE); // разрешение не предоставлено
        }
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE); // разрешение не предоставлено
        }

        else {
            // разрешение предоставлено
        }


        StringsApp sa = new StringsApp();
        sa.setCountOfApp(countOfApp()); // кол-во отфильрованных приложений

        sa.setPackageList(Objects.requireNonNull(getActivity()).getPackageManager()
                .getInstalledPackages(0));  // кол-во всего приложений
        Log.d("Threads", Thread.currentThread().getName());
        return integer;
    }
    //Кол-во отфильтрованных приложений
    private int countOfApp(){
        List<PackageInfo> packagelist = Objects.requireNonNull(getActivity()).getPackageManager().
                getInstalledPackages(0);
        int k= 0;
        for (int i= 0; i < packagelist.size(); i++){
            PackageInfo packageInfo=packagelist.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)== 0){
                k++;
            }
        }

        return k;
    }
    // выполнение пересчета приложений в отдельном потоке
    private void multiThread(){
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::setGlobalStrings)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Integer>() {
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
    //listener
    @Override
    public void propertyChange(PropertyChangeEvent event)  {

        if (event.getPropertyName().equals("MyTextProperty")) {
            System.out.println(event.getNewValue().toString());

            myLoadingButton.showDoneButton();
//            progressBar.setVisibility(View.INVISIBLE);
            DynamicFragment dynamicFragment = new DynamicFragment();
            addListener(dynamicFragment);
            setVariable("ds");



            //загрузка фрагмента "Динамический анализ"
       //     progressBar.setVisibility(View.INVISIBLE);
            frameDyna.setVisibility(VISIBLE);
            warText.setVisibility(View.INVISIBLE);

            Log.d(TAG, "propertyChange: trigger");
            System.out.println("Triggered");
        }



    }
    //отправка сигнала об окончании процесса в фрагмент
    private void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    //отправка сигнала об окончании процесса в фрагмент
    private void setVariable(String newValue) {
        String oldValue = variable;
        variable = newValue;
        support.firePropertyChange("getApplication", oldValue, newValue);
    }


}