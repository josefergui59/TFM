package com.dalujos.matematicaapp.CapaVista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.Fragmentos.BuscarProfesorFrm;
import com.dalujos.matematicaapp.R;
public class DetProfesorVista extends AppCompatActivity {


    private String idPro;
    private String tipo;

    public static void launch(Activity activity, String idProfesor, String tipo) {
        Intent intent = getLaunchIntent(activity, idProfesor, tipo);
        activity.startActivityForResult(intent, Conexion.CODIGO_DETALLE);
    }


    public static Intent getLaunchIntent(Context context, String idProfesor, String tipo) {
        Intent intent = new Intent(context, DetProfesorVista.class);
        intent.putExtra(Conexion.EXTRA_ID, idProfesor);
        intent.putExtra("Tipo", tipo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Setear ícono "X" como Up button
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        // Retener instancia
        if (getIntent().getStringExtra(Conexion.EXTRA_ID) != null)
            idPro = getIntent().getStringExtra(Conexion.EXTRA_ID);

        if (getIntent().getStringExtra("Tipo") != null)
            tipo = getIntent().getStringExtra("Tipo");


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BuscarProfesorFrm.createInstance(idPro, tipo), "DetailFragment")
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Conexion.CODIGO_ACTUALIZACION) {
            if (resultCode == RESULT_OK) {
                BuscarProfesorFrm fragment = (BuscarProfesorFrm) getSupportFragmentManager().
                        findFragmentByTag("DetailFragment");
                fragment.cargarDatos();

                setResult(RESULT_OK); // Propagar código para actualizar
            } else if (resultCode == 203) {
                setResult(203);
                finish();
            } else {
                setResult(RESULT_CANCELED);
            }
        }
    }

}
