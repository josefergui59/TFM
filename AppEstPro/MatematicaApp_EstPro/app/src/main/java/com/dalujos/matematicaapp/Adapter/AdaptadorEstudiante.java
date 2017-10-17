package com.dalujos.matematicaapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.CapaVista.DetEjercicioVista;
import com.dalujos.matematicaapp.utils.Estudiante;

import java.util.List;

public class AdaptadorEstudiante extends RecyclerView.Adapter<AdaptadorEstudiante.EstudianteViewHolder>
        implements ItemClickListener {

    private List<Estudiante> items;
    private Context context;
    private  String tipo;

    public AdaptadorEstudiante(List<Estudiante> items, Context context, String tipo) {
        this.context = context;
        this.items = items;
        this.tipo = tipo;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public EstudianteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new EstudianteViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(EstudianteViewHolder viewHolder, int i) {
        viewHolder.nomUsuario.setText(items.get(i).getESTNOMBRE());
        viewHolder.logIn.setText(items.get(i).getESTLOGIN());
        viewHolder.fechaNac.setText(items.get(i).getESTFECHANAC());
        viewHolder.monedero.setText(items.get(i).getESTMONEDERO());
    }


    @Override
    public void onItemClick(View view, int position) {
        //Al hacer click abro una nueva activity
        DetEjercicioVista.launch(
                (Activity) context, items.get(position).getESTCODIGO(), tipo);
    }


    public static class EstudianteViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nomUsuario;
        public TextView logIn;
        public TextView fechaNac;
        public TextView monedero;
        public ItemClickListener listener;

        public EstudianteViewHolder(View v, ItemClickListener listener) {
            super(v);
            nomUsuario = (TextView) v.findViewById(R.id.nomUsuario);
            logIn = (TextView) v.findViewById(R.id.logIn);
            fechaNac = (TextView) v.findViewById(R.id.descrip);
            monedero = (TextView) v.findViewById(R.id.monedero);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}

interface ItemClickListener {
    void onItemClick(View view, int position);
}



