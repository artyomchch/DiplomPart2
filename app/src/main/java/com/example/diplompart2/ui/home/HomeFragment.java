package com.example.diplompart2.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.drm.DrmStore;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.static_analyze_1.StaticFragment1;
import com.example.diplompart2.analyze_fragments.static_analyze_2.StaticAnalyze2;
import com.example.diplompart2.analyze_fragments.strings.StringsApp;
import com.example.myloadingbutton.MyLoadingButton;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.common.base.Stopwatch;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static android.view.View.VISIBLE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment implements MyLoadingButton.MyLoadingButtonClick, PropertyChangeListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 100;
    // Fragments
    private Fragment fragmentStatic1, fragmentStatic2;
    private FragmentTransaction fragmentTransaction;


    private HomeViewModel homeViewModel;
    private static final String TAG = "MaimActivity";
    private static MyLoadingButton myLoadingButton;




    //Rotate Loading
    private ProgressBar progressBar;
    private ProgressBar progressBar2;
    private Sprite FadingCircle = new FadingCircle(); // sprite for animation

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        //Rotate Loading
        progressBar = root.findViewById(R.id.spin_kit_home_1);
        progressBar2 = root.findViewById(R.id.spin_kit_home_2);

        //Loading button
        myLoadingButton = root.findViewById(R.id.my_loading_button);
        myLoadingButton.setMyButtonClickListener(this);


        //fragments
       fragmentStatic1 = new StaticFragment1();
       fragmentStatic2 = new StaticAnalyze2();

        //progresbar
        progressBar.setIndeterminateDrawable(FadingCircle);
        progressBar2.setIndeterminateDrawable(FadingCircle);
        //multiThread
        multiThread();





        return root;
    }


    @Override
    public void onMyLoadingButtonClick() {

        myLoadingButton.showLoadingButton();
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.button_go_down);

        myLoadingButton.setY(1700);
        myLoadingButton.setAnimation(a);

        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//                progressBar.setVisibility(VISIBLE);
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
                .replace(R.id.StaticFrameLayout2, fragmentStatic2)
                .commit();

    }
    //запрос на разрешения использования мобильной сети, передача данных в другие классы
    private int setGlobalStrings(Integer integer){
        //Check Permission
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE); // разрешение не предоставлено
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

            Log.d(TAG, "propertyChange: trigge");
            System.out.println("Triggered");
        }
    }


}