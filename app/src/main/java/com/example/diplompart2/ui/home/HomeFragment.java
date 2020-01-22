package com.example.diplompart2.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.diplompart2.R;
import com.example.diplompart2.test_class.DataSource;
import com.example.diplompart2.test_class.RxHelper;
import com.example.diplompart2.test_class.Task;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableCache;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.schedulers.Schedulers;

import static java.sql.DriverManager.println;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static final String TAG = "MaimActivity";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView second = root.findViewById(R.id.second);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });



        //multithreading

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io()) // многопоточность
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Exception {
                        Log.d(TAG, "test: " + Thread.currentThread().getName());
                        second.setText(task.getDescription());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return task.isComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new io.reactivex.Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: called.");
            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + task.getDescription());

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: called.");
            }

        });

        // hrenovui threading
        RxHelper.getObservable()
                .subscribe(new io.reactivex.Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        ((TextView) root.findViewById(R.id.text_home)).setText(s);

                        Log.d("tag", s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("tag", "complete");
                    }
                });




        // this some my threading, maybe will be work!



        Observable.just(1, 2, 3, 4, 5, 6)
                .subscribeOn(Schedulers.io())
                .doOnNext(integer -> Log.d("DDDDDDDDDDDDDDD", "onCreateView: " + "Emitting item " + integer + " on: " + Thread.currentThread().getName()))
                .subscribe(integer -> Log.d("YYYYYYYYYYYYYYY", "onCreateView: " +"Consuming item " + integer + " on: " + Thread.currentThread().getName()));



        Observable.just(1, 2, 4, 6)
                .subscribeOn(Schedulers.io())
                .doOnNext(integer -> asynck());


        return root;
    }

    public void asynck(){
        for (int i = 0; i <1000; i++){
            try {
                Thread.sleep(1000);
                Log.d(TAG, "Asynck: " + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}