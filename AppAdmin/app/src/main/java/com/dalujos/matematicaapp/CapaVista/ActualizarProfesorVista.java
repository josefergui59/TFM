package com.dalujos.matematicaapp.CapaVista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.Fragmentos.ActualizarProfesorFrm;
import com.dalujos.matematicaapp.Fragmentos.DatePickerFragment;
import com.dalujos.matematicaapp.Fragmentos.ActualizarEstudianteFrm;
import com.dalujos.matematicaapp.R;



public class ActualizarProfesorVista extends AppCompatActivity
        implements DatePickerFragment.OnDateSelectedListener {



    /**
     * Etiqueta del valor extra del dialogo
     */
    private static final String EXTRA_NOMBRE = "NOMBRE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        String extra = getIntent().getStringExtra(Conexion.EXTRA_ID);

        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_done);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ActualizarProfesorFrm.createInstance(extra), "UpdateFragment")
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public void onDateSelected(int year, int month, int day) {
        ActualizarProfesorFrm updateFragment = (ActualizarProfesorFrm)
                getSupportFragmentManager().findFragmentByTag("UpdateFragment");

        if (updateFragment != null) {
            updateFragment.actualizarFecha(year, month, day);
        }
    }


}
