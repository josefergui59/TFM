package com.dalujos.matematicaapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dalujos.matematicaapp.CapaVista.DetEjercicioVista;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.Ejercicio;

import java.util.List;

/**
 * Adaptador del recycler view
 */
public class AdaptadorEjercicio extends RecyclerView.Adapter<AdaptadorEjercicio.EjercicioViewHolder>
        implements ItemClickListener {


    private List<Ejercicio> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;

    private  String tipo;


    public AdaptadorEjercicio(List<Ejercicio> items, Context context, String tipo) {
        this.context = context;
        this.items = items;
        this.tipo = tipo;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public EjercicioViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);
        return new EjercicioViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(EjercicioViewHolder viewHolder, int i) {
        viewHolder.nomUsuario.setText(items.get(i).getEJENOMBRE());
        viewHolder.logIn.setText(items.get(i).getEJEDESCRIPCION());
        viewHolder.fechaNac.setText(items.get(i).getEJEFECHACREACION());
        viewHolder.monedero.setText(items.get(i).getEJETEXTO());
    }


    @Override
    public void onItemClick(View view, int position) {
        //Al hacer click abro una nueva activity
        DetEjercicioVista.launch(
                (Activity) context, items.get(position).getEJECODIGO(), tipo);
    }


    public static class EjercicioViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView nomUsuario;
        public TextView logIn;
        public TextView fechaNac;
        public TextView monedero;
        public ItemClickListener listener;

        public EjercicioViewHolder(View v, ItemClickListener listener) {
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




