package com.example.diplompart2.test_class;

import android.util.AndroidException;

import androidx.annotation.MainThread;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxHelper {
    private static final String TEXT = "rx rx rx";

    public static Observable<String> getObservable(){
        Observable<String> observable =
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Date date = new Date();
                        emitter.onNext(TEXT);

                        emitter.onNext("hhh");

                        emitter.onNext("sds");

                        emitter.onNext(date.toString());
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .map(s->s.toUpperCase())
                .cache()
                .observeOn(AndroidSchedulers.mainThread());


        return observable;
    }
}
