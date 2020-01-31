package com.example.diplompart2.analyze_fragments.static_analyze_2;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.diplompart2.R;

import java.util.List;
import java.util.Objects;

public class StaticAnalyze2 extends Fragment {
    //TextView
    TextView CountApp, ProcApp;
    // Animation
    private AnimationDrawable animationDrawable;
    // FrameLayout
    private FrameLayout FrameStat2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_static_analyze2, container, false);
        //TextView
        CountApp = root.findViewById(R.id.countApp);
        ProcApp = root.findViewById(R.id.processedApp);
        // FrameLayout
        FrameStat2 = root.findViewById(R.id.stat2);

        //animation
        animation();

        List<PackageInfo> packagelist = Objects.requireNonNull(getActivity()).getPackageManager().
                getInstalledPackages(0);
        int k= 0;
        for (int i= 0; i < packagelist.size(); i++){
            PackageInfo packageInfo=packagelist.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)== 0){
                k++;
            }
        }
        CountApp.setText(String.valueOf(k));

        return root;
    }



    private void animation(){

        animationDrawable = (AnimationDrawable) FrameStat2.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();

    }
}
