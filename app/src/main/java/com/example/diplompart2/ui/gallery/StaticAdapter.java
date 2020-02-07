package com.example.diplompart2.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;

import java.util.List;

public class StaticAdapter extends RecyclerView.Adapter<StaticAdapter.StaticVH>  {
    private static final String TAG = "MovieAdapter";
    List<EmployeeStatic2> static2List;

    public StaticAdapter(List<EmployeeStatic2> static2List){
        this.static2List = static2List;
    }

    @NonNull
    @Override
    public StaticVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.static_row, parent, false);
        return new StaticVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaticVH holder, int position) {
        StaticAdapter staticAdapter = static2List.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class StaticVH extends RecyclerView.ViewHolder{
        private static final String TAG = "MovieVH";
        ConstraintLayout expandableLayout;

        TextView permission;

        public StaticVH(@NonNull final View itemView){
            super(itemView);

            //permission = itemView.findViewById(R.id.)
        }


    }
}
