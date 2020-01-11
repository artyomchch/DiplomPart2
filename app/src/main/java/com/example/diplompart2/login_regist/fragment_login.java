package com.example.diplompart2.login_regist;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.diplompart2.MainActivity;
import com.example.diplompart2.R;
import com.example.diplompart2.login_regist.interfaces.OnActivityDataListener;
import com.example.diplompart2.login_regist.interfaces.OnFragment1DataListener;


public class fragment_login extends Fragment implements OnActivityDataListener {

    EditText eLogin, ePassword;
    private OnFragment1DataListener mListener;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_login, container, false);
        eLogin = v.findViewById(R.id.logLog);
        ePassword = v.findViewById(R.id.logPass);
        return v;
    }

    @Override
    public void onActivityDataListener() {
        String L = eLogin.getText().toString();
        String P = ePassword.getText().toString();
        mListener.onFragment1DataListener(L, P);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1DataListener) {
            mListener = (OnFragment1DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }
}
