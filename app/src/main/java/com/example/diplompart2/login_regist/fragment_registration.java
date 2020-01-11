package com.example.diplompart2.login_regist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.diplompart2.R;
import com.example.diplompart2.login_regist.interfaces.OnActivityDataListener;
import com.example.diplompart2.login_regist.interfaces.OnActivityDataListenerRegister;
import com.example.diplompart2.login_regist.interfaces.OnFragment1DataListener;
import com.example.diplompart2.login_regist.interfaces.OnFragment2DataListener;


public class fragment_registration extends Fragment implements OnActivityDataListenerRegister {
    EditText rLogin, rPassword;
    private OnFragment2DataListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_registration, container, false);

        rLogin = v.findViewById(R.id.reg_email);
        rPassword = v.findViewById(R.id.passwordReg);
        return v;
    }


    @Override
    public void onActivityDataListenerRegister() {
        String L = rLogin.getText().toString();
        String P = rPassword.getText().toString();
        mListener.onFragment2DataListener(L, P);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment2DataListener) {
            mListener = (OnFragment2DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }


}
