package com.example.diplompart2.analyze_fragments.static_analyze_1;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.diplompart2.MainActivity;
import com.example.diplompart2.R;



public class StaticFragment1 extends Fragment {

    //TextView ..
    private TextView model, rootT , version, imei;
    TelephonyManager telephonyManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_static_fragment_1, container, false);

        //TextView
        model = root.findViewById(R.id.modelText);
        version = root.findViewById(R.id.modelVersion);
        imei = root.findViewById(R.id.IMEI);

      //  manafacture();



        return root;

    }


    private void manafacture(){
        MainActivity mn = new MainActivity();
        model.setText(Build.MANUFACTURER + " " + android.os.Build.PRODUCT);
        version.setText(Build.SERIAL);

        imei.setText(mn.getDeviceID());
    }





}
