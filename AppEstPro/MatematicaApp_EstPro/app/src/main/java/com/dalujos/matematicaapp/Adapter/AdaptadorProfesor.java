package com.dalujos.matematicaapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.CapaVista.DetResolverEjerVista;
import com.dalujos.matematicaapp.utils.Profesor;

import java.util.List;

/**
 * Adaptador del recycler view
 */
public class AdaptadorProfesor extends RecyclerView.Adapter<AdaptadorProfesor.ProfesorViewHolder>
        implements ItemClickListener {


    private List<Profesor> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;

    private  String tipo;


    public AdaptadorProfesor(List<Profesor> items, Context context, String tipo) {
        this.context = context;
        this.items = items;
        this.tipo = tipo;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ProfesorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new ProfesorViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ProfesorViewHolder viewHolder, int i) {
        viewHolder.nomUsuario.setText(items.get(i).getPRONOMBRE());
        viewHolder.logIn.setText(items.get(i).getPROLOGIN());
        viewHolder.fechaNac.setText(items.get(i).getPROFECHANAC());
        viewHolder.monedero.setText(items.get(i).getPROEJERCICIORES());
    }


    @Override
    public void onItemClick(View view, int position) {
        //Al hacer click abro una nueva activity
       /* DetResolverEjerVista.launch(
                (Activity) context, items.get(position).getPROCODIGO(), tipo);*/
    }


    public static class ProfesorViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nomUsuario;
        public TextView logIn;
        public TextView fechaNac;
        public TextView monedero;
        public ItemClickListener listener;

        public ProfesorViewHolder(View v, ItemClickListener listener) {
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




