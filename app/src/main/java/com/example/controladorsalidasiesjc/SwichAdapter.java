package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SwichAdapter extends RecyclerView.Adapter<SwichAdapter.ViewHolderSwich>{

    List<FranjaHoraria> listSwich;
    Curso curso;
    Context context;

    public SwichAdapter(List<FranjaHoraria> listSwich,Curso curso,Context context) {
        this.listSwich = listSwich;
        this.curso = curso;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderSwich onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_switch,null,false);
        return new ViewHolderSwich(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SwichAdapter.ViewHolderSwich holder, int position) {
        FranjaHoraria franjaHoraria = listSwich.get(position);
        holder.asignarSwitch(franjaHoraria,curso,context);
    }

    @Override
    public int getItemCount() {
        return listSwich.size();
    }

    public class ViewHolderSwich extends RecyclerView.ViewHolder {

        Switch switchAdapter;
        FranjaHoraria franjaHoraria;
        Curso curso;
        Context context;

        public ViewHolderSwich(@NonNull @NotNull View itemView) {
            super(itemView);
            switchAdapter = itemView.findViewById(R.id.switchAdapter);
            switchAdapter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        RelacionFranjasCursos.addRelacionFranjasCursos(context,franjaHoraria,curso);
                    }else{
                        RelacionFranjasCursos.deleteRelacionFranjasCursos(context,franjaHoraria,curso);
                    }
                }
            });
        }

        public void asignarSwitch(FranjaHoraria franjaHoraria,Curso curso,Context context) {
            this.franjaHoraria = franjaHoraria;
            this.switchAdapter.setText(franjaHoraria.horaInicio.toString() + "-" + franjaHoraria.horaFinal.toString());
            this.curso = curso;
            this.context = context;
            if(RelacionFranjasCursos.getRelacionFranjasCursos(context,franjaHoraria,curso)){
                this.switchAdapter.setChecked(true);
            }else{
                this.switchAdapter.setChecked(false);
            }
        }
    }
}
