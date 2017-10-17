package com.dalujos.matematicaapp.CapaVista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.Modelo.EstudianteModelo;
import com.dalujos.matematicaapp.Modelo.ProfesorModelo;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.Ejercicio;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class DetResolverEjerVista extends AppCompatActivity {

    int PICKER = 1;
    private static final String TAG = DetResolverEjerVista.class.getSimpleName();

    private TextView nombre, idEjercicio;
    private TextView descripcion;
    private EditText respuesta;
    private ImageView imgEnvi, imgRes;
    private Button editButton;
    SharedPreferences prefs ;

    private Gson gson = new Gson();
    private EstudianteModelo estudianteModelo;
    private ProfesorModelo profesorModelo;
    Bitmap photobmp;
    String imagen64;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_resolver_ejer);

        if (getSupportActionBar() != null) {
            // Dehabilitar titulo de la actividad
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Setear ícono "X" como Up button
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        cargar();
        // Cargar datos desde el web service
        cargarDatos();
        cargarLogIn();


    }
    public void cargarLogIn(){
        estudianteModelo.cargarEstudiantes();
        profesorModelo.cargarProfesores();
    }
    public void cargar(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        nombre = (TextView) findViewById(R.id.nomUsuario);
        idEjercicio = (TextView) findViewById(R.id.txtOcualto);
        descripcion = (TextView) findViewById(R.id.descrip);
        respuesta = (EditText) findViewById(R.id.respuesta);
        imgEnvi = (ImageView) findViewById(R.id.imageView3);
        imgRes = (ImageView) findViewById(R.id.imageView4);
        editButton = (Button) findViewById(R.id.btnActualizar);

        estudianteModelo = new EstudianteModelo(this);
        profesorModelo = new ProfesorModelo(this);
        // Setear escucha para el fab
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        guardarRespuesta();

                    }
                }
        );
        imgRes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });
    }
    public void  guardarRespuesta(){
        if(estudianteModelo.probarConexion()) {
            String strRespuesta = respuesta.getText().toString();
            String logIn = prefs.getString("logIn", "Sin Conexión");
            String idPro = profesorModelo.getID(logIn);
            int intValidacion = estudianteModelo.comprobarDatos(logIn, idPro, ". - .", idPro,
                    strRespuesta, strRespuesta, logIn, idPro, idPro);
            if (intValidacion == 1) {
                    if(imagen64 != null) {
                        //Enviar Ejercicio
                        mensajeActualizar("Resolver Ejercicio","Seguro que desea enviar el ejercicio?");
                    }else
                        mensaje("Advertencia", "Seleccione una imagen");

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
    private void getImageFromAlbum(){
        try{
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "No cuenta con admin"), PICKER);

        }catch(Exception exp){
            Log.i("Error", exp.toString());
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          if (requestCode == PICKER && resultCode == RESULT_OK ) {

            Uri uri = data.getData();
            imgRes.setImageURI(uri);
            photobmp = ((BitmapDrawable)imgRes.getDrawable()).getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photobmp.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
            byte[] imageBytes = baos.toByteArray();
            imagen64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);

         }

    }
    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Conexion.GET_EJER_ENV + "?id=" + 1;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar respuesta Json
                                procesarRespuesta(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "Error Volley: " + error.getMessage());
                            }
                        }
                )
        );
    }

    private void procesarRespuesta(JSONObject response) {

        try {
            // Obtener atributo "mensaje"
            String mensaje = response.getString("estado");

            switch (mensaje) {
                case "1":
                    // Obtener objeto "meta"
                    JSONObject object = response.getJSONObject("mensaje");

                    //Parsear objeto
                    Ejercicio ejercicio = gson.fromJson(object.toString(), Ejercicio.class);

                    // Seteando valores en los views
                    nombre.setText(ejercicio.getEJENOMBRE());
                    descripcion.setText(ejercicio.getEJEDESCRIPCION());
                    respuesta.setText(ejercicio.getEJETEXTORES());
                    idEjercicio.setText(ejercicio.getEJECODIGO());

                    if(ejercicio.getEJEIMAGEN().length()>0) {
                        byte[] decodedString = Base64.decode(ejercicio.getEJEIMAGEN(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imgEnvi.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, imgEnvi.getWidth(), imgEnvi.getHeight(), false));
                    }
                    if(ejercicio.getEJEIMAGENRES().length()>0) {
                        byte[] decodedString1 = Base64.decode(ejercicio.getEJEIMAGENRES(), Base64.DEFAULT);
                        Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                        imgRes.setImageBitmap(Bitmap.createScaledBitmap(decodedByte1, imgRes.getWidth(), imgRes.getHeight(), false));
                    }

                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            this,
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    mensajeCerrar("Información", "No hay ejercicios para resolver");
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            this,
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
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
    public void mensajeActualizar(String titulo, final String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                String strRespuesta = respuesta.getText().toString();
                String logIn = prefs.getString("logIn", "Sin Conexión");
                String idPro = profesorModelo.getID(logIn);
                String idEjer = idEjercicio.getText().toString();

                profesorModelo.guardarImagen(idPro, imagen64, strRespuesta, idEjer);
                profesorModelo.actualizarEjerPro(idPro);
                mensajeCerrar("Éxito", "Ejercicio enviado!");

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
