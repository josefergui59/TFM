package com.dalujos.matematicaapp.CapaVista;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.Adapter.Estilos;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.Encriptacion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.Modelo.EstudianteModelo;
import com.dalujos.matematicaapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CambiarClaveVista extends AppCompatActivity  {

    private EstudianteModelo estudianteModelo;
    private Estilos estilos;
    SharedPreferences prefs ;
    private static final String TAG = CambiarClaveVista.class.getSimpleName();

    Button  btnCrearEstudiante;
    TextView txtLogIn;
    EditText  txtClave, txtClaveNueva1, txtClaveNueva2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_c_vista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializar();
        cargarEstilos();
        cargarBotonGuardar();
        cargarLogIn();
    }

    private void inicializar(){
        estudianteModelo = new EstudianteModelo(this);
        estilos = new Estilos(getAssets());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //EditText

        txtLogIn = (TextView)findViewById(R.id.txtLogIn);
        txtClave = (EditText)findViewById(R.id.txtClaveAnt);
        txtClaveNueva1 =(EditText)findViewById(R.id.txtClaveNueva1);
        txtClaveNueva2 = (EditText)findViewById(R.id.txtClaveNueva2);

        //Button
        btnCrearEstudiante = (Button) findViewById(R.id.btnCrearEstudiante);

        txtLogIn.setText(prefs.getString("logIn", "Sin Conexión"));
    }

    public void cargarLogIn(){
        estudianteModelo.cargarEstudiantes();
    }

    public void cargarEstilos(){
        estilos.estiloBotones(btnCrearEstudiante);
    }

    public void cargarBotonGuardar(){
        Button btnEstudiante = (Button) findViewById(R.id.btnCrearEstudiante);
        btnEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");//dd/MM/yyyy
                Date now = new Date();

                String ESTCLAVE = null;
                String ESTCLAVEN = null;
                String ESTCLAVEN1 = null;

                try {
                    ESTCLAVE = Encriptacion.convertirSHA256(txtClave.getText().toString());
                    ESTCLAVEN = Encriptacion.convertirSHA256(txtClaveNueva1.getText().toString());
                    ESTCLAVEN1 = Encriptacion.convertirSHA256(txtClaveNueva2.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if(estudianteModelo.probarConexion()) {
                    int intValidacion = estudianteModelo.comprobarDatos(ESTCLAVE, ESTCLAVEN, ESTCLAVEN1, ESTCLAVE,
                            txtClave.getText().toString(), txtClaveNueva1.getText().toString(), txtClaveNueva2.getText().toString(), ESTCLAVEN1, ESTCLAVEN1);
                    if (intValidacion == 1  ) {
                        if(prefs.getString("usuClave", "-1").equals(ESTCLAVE)) {
                            if (ESTCLAVEN.equals(ESTCLAVEN1)) {
                                cambiarClave(0,ESTCLAVEN, Conexion.UPDATE_CLAVE_EST);
                                cambiarClave(1,ESTCLAVEN, Conexion.UPDATE_CLAVE_PRO);
                                prefs.edit().putString("usuClave", ESTCLAVEN).commit();
                                mensajeCerrar("Éxito", "Contraseña Cambiada");
                            } else {
                                mensaje("Advertencia", "Contraseña Nueva No es Igual");
                            }
                        }else {
                            mensaje("Advertencia", "Contraseña Anterior Incorrecta");
                        }

                       /* if (estudianteModelo.guardarEstudiante(ESTMONEDERO, ESTFECHACREACION, ESTLOGIN, ESTCLAVE,
                                ESTNOMBRE, ESTFECHANAC, ESTGENERO, ESTIDENTIFICA, ESTCORREO)) {
                            mensajeCerrar("Éxito", "Estudiante Creado");
                        }*/
                    } else {
                        if (intValidacion == 0) {
                            mensaje("Advertencia", "LLene todos los campos");
                        }
                        if (intValidacion == 2) {
                            mensaje("Cambiar LogIn", "LogIn ya se encuentra en uso, intente con uno diferente");
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

    public void cambiarClave(final int tipoUsuario, final String clave, String conexion) {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        if(tipoUsuario==0) {
            map.put("ESTLOGIN", prefs.getString("logIn", "Sin Conexión"));
            map.put("ESTCLAVE", clave);
        }
        else
        {
            map.put("PROLOGIN", prefs.getString("logIn", "Sin Conexión"));
            map.put("PROCLAVE", clave);
        }
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        conexion,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuesta(response,tipoUsuario, clave);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Log", "Error 1A Volley: " + error.getMessage());
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Accept", "application/json");
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8" + getParamsEncoding();
                    }
                }
        );
    }

    private void procesarRespuesta(JSONObject response, int tipoUsuario, String clave) {
        try {
            // Obtener atributo "estado"
            txtLogIn.setText(response.toString());
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO USUARIO ENCONTRADO
                    String mensaje1 = response.getString("mensaje");
                    prefs.edit().putString("usuClave", clave).commit();
                    mensajeCerrar("Éxito", "Contraseña Cambiada");
                    break;
                case "2": // FALLIDO USUARIO INVÁLIDO
                    if(tipoUsuario==0)
                        cambiarClave(1, clave, "");
                    else {
                        String mensaje2 = response.getString("mensaje");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setMessage("Usuario/Contraseña Incorrecta");
                        alertDialogBuilder.show();
                        break;
                    }
            }

        } catch (JSONException e) {
            Log.d(TAG, "Error 1: " + e.getMessage());
        }

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



}
