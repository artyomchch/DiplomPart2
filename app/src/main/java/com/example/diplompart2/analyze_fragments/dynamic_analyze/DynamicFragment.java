package com.example.diplompart2.analyze_fragments.dynamic_analyze;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.diplompart2.R;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;


public class DynamicFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dynamic, container, false);
        MaterialSpinner spinner =  root.findViewById(R.id.spinner);
        spinner.setItems("Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"
                , "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"
                , "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"
                , "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"
                , "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"
                , "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"
                , "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow"
                , "Jelly Bean", "KitKat", "Lollipop", "Marshmallow", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        return root;
    }
}
