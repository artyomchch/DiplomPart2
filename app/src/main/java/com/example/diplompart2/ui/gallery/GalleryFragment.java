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
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    EditText et_name, et_password;
    TextView tv_status;
    Button btn_login;

    Observable<Boolean> observable;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);


        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);


        et_name = root.findViewById(R.id.et_name);
        et_password = root.findViewById(R.id.et_password);
        btn_login = root.findViewById(R.id.btn_login);
        tv_status = root.findViewById(R.id.tv_status);



        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Observable<String> nameObservable = RxTextView.textChanges(et_name).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return charSequence.toString();
            }
        });

        Observable<String> passwordObservable = RxTextView.textChanges(et_password).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return charSequence.toString();
            }
        });


        observable = Observable.combineLatest(nameObservable, passwordObservable, new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(String s, String s2) throws Exception {
                return isValidForm(s, s2);
            }
        });

        observable.subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                updateButton(aBoolean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });



        return root;
    }

    public void updateButton(boolean valid) {
        if (valid)
            btn_login.setEnabled(true);
        else
            btn_login.setEnabled(false);
    }

    public boolean isValidForm(String name, String password) {
        boolean validName = !name.isEmpty();

        if (!validName) {
            et_name.setError("Please enter valid name");
        }

        boolean validPass = !password.isEmpty() && password.equals("fuck");
        if (!validPass) {
            et_password.setError("Incorrect password");
        }
        return validName && validPass;
    }

}