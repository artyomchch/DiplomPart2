package com.example.diplompart2.ui.gallery;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;
import com.example.diplompart2.analyze_fragments.static_analyze_1.TypeAtributes1;
import com.example.diplompart2.analyze_fragments.static_analyze_2.TypeAtribute2;

import java.util.List;
import java.util.Objects;

public class StaticAdapter extends RecyclerView.Adapter<StaticAdapter.StaticVH>  {
    private static final String TAG = "MovieAdapter";
    List<TypeAtribute2> typeList2;





    public StaticAdapter(List<TypeAtribute2> typeList2){
        this.typeList2 = typeList2;
    }

    @NonNull
    @Override
    public StaticVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.static_row, parent, false);
        return new StaticVH(view);
    }



    @Override
    public void onBindViewHolder(@NonNull StaticVH holder, int position) {
        TypeAtribute2 typeAtribute2 = typeList2.get(position);
        holder.name.setText(typeAtribute2.getNameApp());
        holder.fullName.setText(typeAtribute2.getFullAppName());
        holder.version.setText(typeAtribute2.getVersionApp());
        holder.path.setText(typeAtribute2.getPathApp());
        holder.permission.setText(typeAtribute2.getPermission());
        holder.icon.setImageDrawable(typeAtribute2.getIcon());


        boolean isExpanded = typeList2.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }


    @Override
    public int getItemCount() {
        return typeList2.size();
    }


    class StaticVH extends RecyclerView.ViewHolder{
        private static final String TAG = "MovieVH";
        ConstraintLayout mainConstraint;
        ConstraintLayout expandableLayout;
        TextView name, fullName, version, path, permission;
        ImageView icon;


        public StaticVH(@NonNull final View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.appDB);
            fullName = itemView.findViewById(R.id.fullAppDB);
            version = itemView.findViewById(R.id.versionDB);
            path = itemView.findViewById(R.id.pathDB);
            permission = itemView.findViewById(R.id.permissionDB);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            mainConstraint = itemView.findViewById(R.id.mainConstraint);
            icon = itemView.findViewById(R.id.appViewDB);


            mainConstraint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TypeAtribute2 typeAtribute2 = typeList2.get(getAdapterPosition());
                    typeAtribute2.setExpanded(!typeAtribute2.isExpanded());
                    notifyItemChanged(getAdapterPosition());

                }
            });
        }




    }
}
