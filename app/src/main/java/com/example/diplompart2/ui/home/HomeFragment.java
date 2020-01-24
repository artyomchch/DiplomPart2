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
import com.example.diplompart2.test_class.Task;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static final String TAG = "MaimActivity";
    private TextView thread1;
    private Disposable disposable;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        final TextView second = root.findViewById(R.id.second);
        thread1 = root.findViewById(R.id.thread);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //that's example one more


        Observable<String> animalsObservable = getAnimalsObservable();

        io.reactivex.Observer<String> animalsObserver = getAnimalsObserver();


        animalsObservable
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        Log.d(TAG, "test: " + Thread.currentThread().getName());
                        second.setText("Asynck: "  + " " + s + "  " + Thread.currentThread().getName());
                        Thread.sleep(1000);



                        return s.toLowerCase().startsWith("b");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animalsObserver);




        //multithreading

//        Observable<Task> taskObservable = Observable
//                .fromIterable(DataSource.createTasksList())
//                .subscribeOn(Schedulers.io()) // многопоточность
//                .filter(new Predicate<Task>() {
//                    @Override
//                    public boolean test(Task task) throws Exception {
//                        Log.d(TAG, "test: " + Thread.currentThread().getName());
//                       // second.setText(task.getDescription());
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        return task.isComplete();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread());
//
//        taskObservable.subscribe(new io.reactivex.Observer<Task>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe: called.");
//            }
//
//            @Override
//            public void onNext(Task task) {
//                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
//                Log.d(TAG, "onNext: " + task.getDescription() + " " + Thread.currentThread().getName());
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError: ", e);
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: called.");
//            }
//
//        });
//



        return root;
    }



    private io.reactivex.Observer<String> getAnimalsObserver(){
        return new io.reactivex.Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("qqqqqq", "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d("qqqqqq", "Name: " + s + "  " + Thread.currentThread().getName());


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("qqqqqq", "All items are emitted!" + " " + Thread.currentThread().getName());
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
        disposable.dispose();
    }

    public void asynck(String trys){
        for (int i = 0; i <20; i++){
            Log.d(TAG, "Asynck: " + i + " " + trys + "  " + Thread.currentThread().getName());

        }
    }

}