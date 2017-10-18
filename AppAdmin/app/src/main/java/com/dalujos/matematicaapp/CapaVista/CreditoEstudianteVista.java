package com.dalujos.matematicaapp.CapaVista;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.Adapter.AdaptadorEstudiante;
import com.dalujos.matematicaapp.Adapter.Estilos;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.Modelo.EstudianteModelo;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.Estudiante;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class CreditoEstudianteVista extends AppCompatActivity {

    private static final String TAG = CreditoEstudianteVista.class.getSimpleName();
    private Gson gson = new Gson();
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
    private EstudianteModelo estudianteModelo;
    private Estilos estilos;
    private String ESTMONEDERO, id;
    EditText  txtClave;
    Spinner spnLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credito_estudiante_vista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializar();
        cargarBotonGuardar();
        cargarSpinner();

    }

    private void inicializar(){
        estudianteModelo = new EstudianteModelo(this);
        estilos = new Estilos(getAssets());
        //EditText
        spnLista = (Spinner)findViewById(R.id.spnLstEst);
        txtClave = (EditText)findViewById(R.id.txtClave);
         }

    public static void launch(Activity activity, String idMeta) {
        Intent intent = getLaunchIntent(activity, idMeta);
        activity.startActivityForResult(intent, Conexion.CODIGO_DETALLE);
    }

    public static Intent getLaunchIntent(Context context, String idMeta) {
        Intent intent = new Intent(context, CreditoEstudianteVista.class);
        intent.putExtra(Conexion.EXTRA_ID, idMeta);
        return intent;
    }

    public void cargarBotonGuardar(){
        Button btnEstudiante = (Button) findViewById(R.id.btnCrearEstudiante);
        btnEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");//dd/MM/yyyy
                Date now = new Date();

                id = spinnerMap.get(spnLista.getSelectedItemPosition());
                String logIn =  spnLista.getSelectedItem().toString();
                ESTMONEDERO = txtClave.getText().toString();


                if(estudianteModelo.probarConexion()) {
                    int intValidacion = estudianteModelo.comprobarDatos(ESTMONEDERO, "0", "0", "0",
                            "0", "0", "0", "0", "0");
                    if (intValidacion == 1) {
                        mensajeActualizar("Agregar Créditos", "Está seguro que desea agregar " + ESTMONEDERO + " créditos al estudiante " + logIn + "?");
                    } else {
                        if (intValidacion == 0) {
                            mensaje("Advertencia", "LLene todos los campos");
                        }
                        if (intValidacion == 2) {
                            mensaje("Cambiar LogIn", "LogIn no se encuentra registrado");
                        }
                    }
                }
                else{
                    mensaje("Advertencia", "No se encuentra conectado, intentelo nuevamente!");
                }
            }
        });

        estilos.estiloBotones(btnEstudiante);
    }
     public  void cargarSpinner(){
         cargarAdaptador();
         estudianteModelo.cargarEstudiantes();
     }
    public void cargarAdaptador() {
        // Petición GET
        VolleySingleton.
                getInstance(this).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Conexion.GET_EST,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.toString());
                                    }
                                }

                        )
                );
    }


    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO
                    // Obtener array "estudiante" Json
                    JSONArray mensaje = response.getJSONArray("estudiante");
                    // Parsear con Gson
                    Estudiante[] estudiante = gson.fromJson(mensaje.toString(), Estudiante[].class);

                    /*****/
                    String[] spinnerArray = new String[estudiante.length];
                    for (int i = 0; i < estudiante.length; i++)
                    {
                        spinnerMap.put(i,estudiante[i].getESTCODIGO());
                        spinnerArray[i] = estudiante[i].getESTLOGIN();
                    }

                    ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnLista.setAdapter(adapter);
                    /*****/

                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getApplicationContext(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }

    public void mensajeActualizar(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                estudianteModelo.actualizarCreditos(ESTMONEDERO,id);
                mensajeCerrar("Éxito", "Créditos Ingresados Exitosamente");
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void mensaje(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void mensajeCerrar(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
