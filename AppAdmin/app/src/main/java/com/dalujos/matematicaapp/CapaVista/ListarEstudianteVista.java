package com.dalujos.matematicaapp.CapaVista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.Fragmentos.ListarEstudianteFrm;
import com.dalujos.matematicaapp.R;


/**
 * Actividad principal que contiene un fragmento con una lista.
 * Recuerda que la nueva librería de soporte reemplazó la clase
 * {@link android.support.v7.app.ActionBarActivity} por
 * {@link AppCompatActivity} para el uso de la action bar
 * en versiones antiguas.
 */
public class ListarEstudianteVista extends AppCompatActivity {

    private String tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        tipo = getIntent().getExtras().getString("Tipo");
        // Creación del fragmento principal
        if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ListarEstudianteFrm(tipo), "ListarEstudianteFrm")
                        .commit();


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Conexion.CODIGO_DETALLE || requestCode == 3) {
            if (resultCode == RESULT_OK || resultCode == 203) {
                    ListarEstudianteFrm fragment = (ListarEstudianteFrm) getSupportFragmentManager().
                            findFragmentByTag("MainFragment");
                    fragment.cargarAdaptador();
            }
        }
    }
}
