package com.dalujos.matematicaapp.CapaVista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.Fragmentos.ListarEstudianteFrm;
import com.dalujos.matematicaapp.Fragmentos.ListarProfesorFrm;
import com.dalujos.matematicaapp.R;

public class ListarProfesorVista extends AppCompatActivity {

    private String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        tipo = getIntent().getExtras().getString("Tipo");
        // Creación del fragmento principal
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ListarProfesorFrm(tipo), "ListarProfesorFrm")
                    .commit();


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Conexion.CODIGO_DETALLE || requestCode == 3) {
            if (resultCode == RESULT_OK || resultCode == 203) {
                ListarProfesorFrm fragment = (ListarProfesorFrm) getSupportFragmentManager().
                        findFragmentByTag("MainFragment");
                fragment.cargarAdaptador();
            }
        }
    }
}
