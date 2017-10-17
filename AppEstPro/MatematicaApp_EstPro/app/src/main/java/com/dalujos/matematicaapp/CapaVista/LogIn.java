package com.dalujos.matematicaapp.CapaVista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.Encriptacion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.Modelo.EstudianteModelo;
import com.dalujos.matematicaapp.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class LogIn extends AppCompatActivity {
    EditText usu;
    EditText con;
    Button btnIngreso;
    SharedPreferences prefs ;
    private static final String TAG = LogIn.class.getSimpleName();
    private Gson gson = new Gson();
    private EstudianteModelo estudianteModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cargar();
        validarRegistro();
        cargarLogIn();
    }

    public void cargarLogIn(){
        estudianteModelo.cargarEstudiantes();
    }

    private void validarRegistro() {
        Boolean yourLocked = prefs.getBoolean("register", false);
        int intTipoUsuario = prefs.getInt("tipoUsuario", 2);
        if(yourLocked){
            abrirMenu(intTipoUsuario);
        }
    }

    public void cargar(){
        TextView tituloBar = (TextView) findViewById(R.id.titleBar);
        usu = (EditText) findViewById(R.id.input_email);
        con = (EditText) findViewById(R.id.input_password);
        btnIngreso = (Button) findViewById(R.id.btn_login);
        Typeface fontRobo = Typeface.createFromAsset(this.getAssets(), "font/calibri.ttf");
        tituloBar.setTypeface(fontRobo);
        usu.setTypeface(fontRobo);
        con.setTypeface(fontRobo);
        estudianteModelo = new EstudianteModelo(this);
        btnIngreso.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                validar();
            }
        });
    }
    public void validar(){
        if(estudianteModelo.probarConexion()) {
            verificarLogIn(0); // 0 estudiante // 1 profesor
        }
            else{
                mensaje("Advertencia", "No se encuentra conectado, intentelo nuevamente!");
            }
    }
    public void abrirMenu(int tipoUsuario){
        Intent intent;
        if(tipoUsuario == 0) {
            intent = new Intent(this, MenuEstudiante.class); //ESTUDIANTE
        }
        else
            intent = new Intent(this, MenuProfesor.class); // PROFESOR
        startActivity(intent);
        finish();
    }

    public void verificarLogIn(final int tipoUsuario) {
        // Petición GET
        String idRegistro = usu.getText().toString().trim();
        String clave = "" ;
        try {
            clave = Encriptacion.convertirSHA256(con.getText().toString().trim());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        prefs.edit().putString("logIn", idRegistro).commit();
        prefs.edit().putString("usuClave", clave).commit();

        String newURL;
        if(tipoUsuario == 0) // estudiante
            newURL = Conexion.GET_EST_LOGIN + "?id=" + idRegistro + "&clave=" + clave;
        else
            newURL = Conexion.GET_PRO_LOGIN + "?id=" + idRegistro + "&clave=" + clave;

        VolleySingleton.getInstance(this).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                newURL,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response, tipoUsuario);
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

    private void procesarRespuesta(JSONObject response, int tipoUsuario) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO USUARIO ENCONTRADO
                        prefs.edit().putBoolean("register", true).commit();
                        prefs.edit().putInt("tipoUsuario", tipoUsuario).commit();
                    if(tipoUsuario == 0) {
                        prefs.edit().putString("id_EstLog", estudianteModelo.getID(prefs.getString("logIn", "-1"))).commit();
                    }
                        abrirMenu(tipoUsuario);
                    break;
                case "2": // FALLIDO USUARIO INVÁLIDO
                    if(tipoUsuario==0)
                        verificarLogIn(1);
                    else {
                        String mensaje2 = response.getString("mensaje");
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setMessage("Usuario/Contraseña Incorrecta");
                        alertDialogBuilder.show();
                        break;
                    }
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }
}
