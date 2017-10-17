package com.dalujos.matematicaapp.CapaVista;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.Fragmentos.BuscarEjercicioFrm;
import com.dalujos.matematicaapp.R;

public class DetEjercicioVista extends AppCompatActivity {

    private String idEjer;
    private String tipo;

    public static void launch(Activity activity, String idEjer, String tipo) {
        Intent intent = getLaunchIntent(activity, idEjer, tipo);
        activity.startActivityForResult(intent, Conexion.CODIGO_DETALLE);
    }


    public static Intent getLaunchIntent(Context context, String idEjer, String tipo) {
        Intent intent = new Intent(context, DetEjercicioVista.class);
        intent.putExtra(Conexion.EXTRA_ID, idEjer);
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
            idEjer = getIntent().getStringExtra(Conexion.EXTRA_ID);

        if (getIntent().getStringExtra("Tipo") != null)
            tipo = getIntent().getStringExtra("Tipo");


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, BuscarEjercicioFrm.createInstance(idEjer, tipo), "DetailFragment")
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
                BuscarEjercicioFrm fragment = (BuscarEjercicioFrm) getSupportFragmentManager().
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
