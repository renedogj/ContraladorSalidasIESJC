package com.example.controladorsalidasiesjc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class SwichAdapter extends RecyclerView.Adapter<SwichAdapter.ViewHolderSwich>{

    List<FranjaHoraria> listSwich;
    Curso curso;
    Context context;
    String alumno;

    public SwichAdapter(List<FranjaHoraria> listSwich,Curso curso,Context context, String alumno) {
        this.listSwich = listSwich;
        this.curso = curso;
        this.context = context;
        this.alumno = alumno;
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
        holder.asignarSwitch(franjaHoraria,curso,context,alumno);
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
        String alumno;

        public ViewHolderSwich(@NonNull @NotNull View itemView) {
            super(itemView);
            switchAdapter = itemView.findViewById(R.id.switchAdapter);
            switchAdapter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        RelacionFranjasCursos.addRelacionFranjasCursos(context,franjaHoraria,curso,alumno);
                    }else{
                        RelacionFranjasCursos.deleteRelacionFranjasCursos(context,franjaHoraria,curso,alumno);
                    }
                }
            });
        }

        public void asignarSwitch(FranjaHoraria franjaHoraria, Curso curso, Context context, String alumno) {
            this.franjaHoraria = franjaHoraria;
            this.switchAdapter.setText(franjaHoraria.horaInicio.toString() + "-" + franjaHoraria.horaFinal.toString());
            this.curso = curso;
            this.context = context;
            this.alumno = alumno;
            this.switchAdapter.setChecked(RelacionFranjasCursos.getRelacionFranjasCursos(context, franjaHoraria, curso,alumno));
        }
    }
}
