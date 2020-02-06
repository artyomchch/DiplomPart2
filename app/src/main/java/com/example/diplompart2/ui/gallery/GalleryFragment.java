package com.example.diplompart2.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.static_analyze_1.room.App;
import com.example.diplompart2.analyze_fragments.static_analyze_1.room.EmployeeStatic1;
import com.example.diplompart2.analyze_fragments.static_analyze_1.room.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.static_analyze_1.room.EmployeeStatic1Database;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);

        EmployeeStatic1Database db = App.getInstance().getDatabase(); // получение базы данных
        EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao

        db.employeeStatic1Dao().getAll2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<EmployeeStatic1>() {
                    @Override
                    public void onSuccess(EmployeeStatic1 employee) {
                        textView.setText(employee.model + " " + employee.imei + " " + employee.system+ " " + employee.root);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // ...
                    }
                });




        return root;
    }


}