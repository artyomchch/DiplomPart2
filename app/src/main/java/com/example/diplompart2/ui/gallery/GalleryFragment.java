package com.example.diplompart2.ui.gallery;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.Converters;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;
import com.example.diplompart2.analyze_fragments.static_analyze_2.TypeAtribute2;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class GalleryFragment extends Fragment {
    //recycleView
    private RecyclerView recyclerView;
    private List<TypeAtribute2> typeList;
    //Convert
    private Converters converters = new Converters();
    // ProgressBar
    private ProgressBar progressBarList;
    private Sprite ThreeBounce = new ThreeBounce(); // sprite for animation
    //integers
    private static int countRow;

    private GalleryViewModel galleryViewModel;
    private EmployeeStatic1Database db = App.getInstance().getDatabase(); // получение базы данных
    private EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //recyclerView
        recyclerView = root.findViewById(R.id.recyclerStatic);
        progressBarList = root.findViewById(R.id.progressList);
        //progressBar
        progressBarList.setVisibility(VISIBLE);
        progressBarList.setIndeterminateDrawable(ThreeBounce);
        //mainTask
        count();


        return root;
    }


    private void count(){
        db.employeeStatic1Dao().getRowCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        Log.d("TAG", "onSuccess: " + integer);
                        //countRow = integer;
                        complete(integer);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e );
                    }

                });
    }

    private void initRecyclerView() {

        StaticAdapter staticAdapter = new StaticAdapter(typeList);
        recyclerView.setAdapter(staticAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void complete(int i)  {
        typeList = new ArrayList<>();
        Observable.range(1, i)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::getDataFromRoom)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: "+ integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: "+ e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        progressBarList.stopNestedScroll();
                        progressBarList.setVisibility(INVISIBLE);
                        initRecyclerView();
                    }
                });
    }

    private Drawable ico(String app) throws PackageManager.NameNotFoundException {
        Drawable appIco = Objects.requireNonNull(getActivity()).getPackageManager().getApplicationIcon(app);
        return appIco;
    }

    private void getDataFromRoom(int i) throws PackageManager.NameNotFoundException {

        EmployeeStatic2 employee = employeeStatic1Dao.getById(i);
        typeList.add(new TypeAtribute2(
                employee.apkName,
                employee.apkFullName,
                employee.apkVersion,
                employee.apkPath,
                ico(employee.apkFullName),
                getConvertListPermission(converters.fromString((employee.apkPermission))).toString()));

        System.out.println(getConvertListPermission(converters.fromString(employee.apkPermission)) + "            Thread ->   " + Thread.currentThread().getName());


    }

    private StringBuilder getConvertListPermission(ArrayList<String> perm) {

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < perm.size(); i++) {
            s.append(i + 1 + ") " + perm.get(i) + "\n");
        }
        return s;

    }

}