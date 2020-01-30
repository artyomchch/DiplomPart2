package com.example.diplompart2.ui.home;

import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.diplompart2.MainActivity;
import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.static_analyze_1.StaticFragment1;
import com.example.diplompart2.login_regist.fragment_login;
import com.example.diplompart2.test_class.DataSource;
import com.example.diplompart2.test_class.Task;
import com.example.myloadingbutton.MyLoadingButton;
import com.marozzi.roundbutton.RoundButton;


import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment implements MyLoadingButton.MyLoadingButtonClick {

    // Fragments
    Fragment fragmentStatic1, frag;
    FragmentTransaction fragmentTransaction;


    private HomeViewModel homeViewModel;
    private final static String FIRST_FRAG_TAG = "f1";
    private static final String TAG = "MaimActivity";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MyLoadingButton myLoadingButton;
    private FragmentManager fragmentManager;




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

        //Loading button
        myLoadingButton = root.findViewById(R.id.my_loading_button);
        myLoadingButton.setMyButtonClickListener(this);


        //fragments
       fragmentStatic1 = new StaticFragment1();






        //that's example one more
        Observable<String> animalsObservable = getAnimalsObservable();

        DisposableObserver<String> animalsObserver = getAnimalsObserver();

        DisposableObserver<String> animalsObserverAllCaps = getAnimalsAllCapsObserver();

        compositeDisposable.add(
                animalsObservable
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        Thread.sleep(1000);
                        Log.d(TAG, "test: " + s + " " + Thread.currentThread().getName());
                        return s.toLowerCase().startsWith("b");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(animalsObserver));


        compositeDisposable.add(
                animalsObservable
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                       // Thread.sleep(1000);
                        Log.d(TAG, "test: " + s + " " + Thread.currentThread().getName());
                        //thread1.setText("test: " + s + " " + Thread.currentThread().getName());
                        return s.toLowerCase().startsWith("c");
                    }
                })
                .map(new Function<String, String>() {

                    @Override
                    public String apply(String s) throws Exception {
                        return s.toUpperCase();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(animalsObserverAllCaps));





        return root;
    }





    private DisposableObserver<String> getAnimalsObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s + " " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }


    private DisposableObserver<String> getAnimalsAllCapsObserver(){
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s + " " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    private Observable<String> getAnimalsObservable() {
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog");
    }
    @Override
     public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onMyLoadingButtonClick() {

        myLoadingButton.showLoadingButton();
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.button_go_down);
        myLoadingButton.setY(1700);
        myLoadingButton.setAnimation(a);

        loadFragments();



    }



    public void loadFragments(){
        fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in_custom, android.R.anim.fade_out)
                .replace(R.id.StaticFrameLayout, fragmentStatic1)
                .commit();

    }









}