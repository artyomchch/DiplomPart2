package com.example.diplompart2.ui.tools;

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

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }



//        //that's example one more
//        Observable<String> animalsObservable = getAnimalsObservable();
//
//        DisposableObserver<String> animalsObserver = getAnimalsObserver();
//
//        DisposableObserver<String> animalsObserverAllCaps = getAnimalsAllCapsObserver();
//
//        compositeDisposable.add(
//                animalsObservable
//                .subscribeOn(Schedulers.io())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        Thread.sleep(1000);
//                        Log.d(TAG, "test: " + s + " " + Thread.currentThread().getName());
//                        return s.toLowerCase().startsWith("b");
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(animalsObserver));
//
//
//        compositeDisposable.add(
//                animalsObservable
//                .subscribeOn(Schedulers.io())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                       // Thread.sleep(1000);
//                        Log.d(TAG, "test: " + s + " " + Thread.currentThread().getName());
//                        //thread1.setText("test: " + s + " " + Thread.currentThread().getName());
//                        return s.toLowerCase().startsWith("c");
//                    }
//                })
//                .map(new Function<String, String>() {
//
//                    @Override
//                    public String apply(String s) throws Exception {
//                        return s.toUpperCase();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(animalsObserverAllCaps));
//

//    private DisposableObserver<String> getAnimalsObserver() {
//        return new DisposableObserver<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "Name: " + s + " " + Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "All items are emitted!");
//            }
//        };
//    }
//
//
//    private DisposableObserver<String> getAnimalsAllCapsObserver(){
//        return new DisposableObserver<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d(TAG, "Name: " + s + " " + Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "onError: " + e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "All items are emitted!");
//            }
//        };
//    }
//
//    private Observable<String> getAnimalsObservable() {
//        return Observable.fromArray(
//                "Ant", "Ape",
//                "Bat", "Bee", "Bear", "Butterfly",
//                "Cat", "Crab", "Cod",
//                "Dog", "Dove",
//                "Fox", "Frog");

//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        compositeDisposable.clear();
//    }
}