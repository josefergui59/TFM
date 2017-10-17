package com.dalujos.matematicaapp.CapaVista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.dalujos.matematicaapp.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EnviarEjercicioVista extends AppCompatActivity {

    int PICKER = 1;
    EditText txtTitulo, txtDesc;
    TextView txtCredito;
    Button btnEnviarEje;
    SharedPreferences prefs ;
    private static final String TAG = EnviarEjercicioVista.class.getSimpleName();
    private Gson gson = new Gson();
    private EstudianteModelo estudianteModelo;
    ImageView imgV;
    Bitmap photobmp;
    String imagen64;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_ejercicio);
        cargar();
        cargarLogIn();
        cargarCredito();
    }
    public void cargar(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        txtTitulo = (EditText) findViewById(R.id.txtTitulo);
        txtDesc = (EditText) findViewById(R.id.txtDesc);
        txtCredito= (TextView) findViewById(R.id.txtCredito);
        btnEnviarEje  =  (Button) findViewById(R.id.btnActualizar);
        imgV = (ImageView)findViewById(R.id.imageView3);
        imagen64 = null;
        estudianteModelo = new EstudianteModelo(this);
        // photobmp = new Bitmap();
        btnEnviarEje.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enviarEjercicio();
            }
        });
        imgV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });

    }

    public void cargarCredito(){

        final Handler handler=new Handler();
        final String logIn = prefs.getString("logIn", "Sin Conexión");
        final Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                // upadte textView here
                Double credito = Double.parseDouble(estudianteModelo.getCredito(logIn));
               // mensaje("", credito + "");
               // i+=1;
                if(credito >= 0) {
                    txtCredito.setText("Créditos Disponibles: " + credito );
                }
                handler.postDelayed(this, 1500);
            }
        };
        handler.post(myRunnable);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKER && resultCode == RESULT_OK ) {

            Uri uri = data.getData();
            imgV.setImageURI(uri);
            photobmp = ((BitmapDrawable)imgV.getDrawable()).getBitmap();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photobmp.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
            byte[] imageBytes = baos.toByteArray();
            imagen64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        }

    }
    public String getPathImg(Uri uri){
        Cursor cursor = null;
        try {
            String[] filePath = {MediaStore.Images.Media.DATA};
            cursor = getApplicationContext().getContentResolver().query(uri, filePath, null, null, null);
            int columna = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columna);
        }finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public void enviarEjercicio(){
        if(estudianteModelo.probarConexion()) {
            String titulo = txtTitulo.getText().toString();
            String descripcion = txtDesc.getText().toString();
            String logIn = prefs.getString("logIn", "Sin Conexión");
            String idEst = estudianteModelo.getID(logIn);
            Double credito = Double.parseDouble(estudianteModelo.getCredito(logIn));
            int intValidacion = estudianteModelo.comprobarDatos(titulo, descripcion, ". - .", idEst,
                    titulo, descripcion, logIn, idEst, idEst);
            if (intValidacion == 1) {
                if(credito > 0) {
                    if(imagen64 != null) {
                        //Enviar Ejercicio
                        mensajeActualizar("Enviar Ejercicio","Seguro que desea enviar el ejercicio?");
                    }else
                        mensaje("Advertencia", "Seleccione una imagen");
                }else
                    mensaje("Advertencia", "Créditos Insuficientes");

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
    public void cargarLogIn(){
        estudianteModelo.cargarEstudiantes();
    }

    public void mensajeActualizar(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                String titulo = txtTitulo.getText().toString();
                String descripcion = txtDesc.getText().toString();
                String logIn = prefs.getString("logIn", "Sin Conexión");
                String idEst = estudianteModelo.getID(logIn);

                estudianteModelo.guardarImagen(idEst, imagen64, titulo, descripcion);
                estudianteModelo.actualizarCreditosEjer("1", idEst);
                estudianteModelo.actualizarPago(idEst, "Ejercicio Enviado", "1",logIn, "Débito");
                mensajeCerrar("Éxito", "Ejercicio enviado, espere su respuesta");

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
