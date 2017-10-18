package com.dalujos.matematicaapp.Fragmentos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.Encriptacion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.Modelo.EstudianteModelo;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.CapaVista.MenuAdministrador;
import com.dalujos.matematicaapp.utils.Estudiante;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


/**
 * Fragmento con formulario para actualizar la meta
 */
public class ActualizarEstudianteFrm extends Fragment {
    /*
    Etiqueta de depuración
     */
    private static final String TAG = ActualizarEstudianteFrm.class.getSimpleName();

    /*
    Controles
    */
    private EditText nomEstudiante;
    private EditText clave;
    private EditText logIn , txtIdentifica, txtCorreo;
    private TextView fecha_text;
    RadioButton rbMasculino, rbFemenino;
    private Button btnActualizar, btnEliminar;
    private EstudianteModelo estudianteModelo ;


    private String idRegistro;

    /**
     * Es la meta obtenida como respuesta de la petición HTTP
     */
    private Estudiante estudiante;

    /**
     * Instancia Gson para el parsing Json
     */
    private Gson gson = new Gson();


    public ActualizarEstudianteFrm() {

    }


    public static Fragment createInstance(String extra) {
        ActualizarEstudianteFrm detailFragment = new ActualizarEstudianteFrm();
        Bundle bundle = new Bundle();
        bundle.putString(Conexion.EXTRA_ID, extra);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflando layout del fragmento
        View v = inflater.inflate(R.layout.fragment_actualizar, container, false);

        // Obtención de instancias controles
        nomEstudiante = (EditText) v.findViewById(R.id.txtNombreEstudiante);
        clave = (EditText) v.findViewById(R.id.txtClave);
        logIn = (EditText) v.findViewById(R.id.txtLogIn);
        txtIdentifica = (EditText)v.findViewById(R.id.txtID);
        txtCorreo = (EditText)v.findViewById(R.id.txtMail);
        fecha_text = (TextView) v.findViewById(R.id.fecha_ejemplo_text);
        rbMasculino=(RadioButton) v.findViewById(R.id.rbMas);
        rbFemenino =(RadioButton) v.findViewById(R.id.rbFem);
        btnActualizar =(Button) v.findViewById(R.id.btnActualizar);
        btnEliminar =(Button) v.findViewById(R.id.btnEliminar);

        estudianteModelo = new EstudianteModelo(getActivity());

        // boton Actualizar registro
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ESTNOMBRE = nomEstudiante.getText().toString();
                String hashClave = "";
                try {
                    if(clave.getText().toString().length()< 60)
                        hashClave = Encriptacion.convertirSHA256(clave.getText().toString());
                    else
                        hashClave = clave.getText().toString();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final String ESTCLAVE = hashClave;
                final String ESTLOGIN =logIn.getText().toString();
                final String ESTFECHANAC = fecha_text.getText().toString();
                final String ESTFECHACREACION = fecha_text.getText().toString();
                final String ESTIDENTIFICA = txtIdentifica.getText().toString();
                final String ESTCORREO = txtCorreo.getText().toString();
                final String ESTGENERO;
                if (rbMasculino.isChecked())
                    ESTGENERO = rbMasculino.getText().toString();
                else
                    ESTGENERO = rbFemenino.getText().toString();

                int intValidacion = estudianteModelo.comprobarDatos("0", ESTFECHACREACION, ESTLOGIN, ESTCLAVE,
                        ESTNOMBRE, ESTFECHANAC, ESTGENERO, ESTIDENTIFICA, ESTCORREO);
                if (intValidacion == 1 || intValidacion == 2) {

                    Toast.makeText(
                            getActivity(),
                            "Actualizado",
                            Toast.LENGTH_LONG).show();
                    estudianteModelo.actualizarDatos(ESTNOMBRE ,
                            ESTCLAVE,
                            ESTLOGIN,
                            ESTFECHANAC ,
                            ESTFECHACREACION  ,
                            ESTGENERO,
                            idRegistro,
                            ESTIDENTIFICA,
                            ESTCORREO);
                    mensajeCerrar("Exito", "Actualizacion Correcta");

                } else {
                    if (intValidacion == 0) {
                        mensaje("Advertencia", "LLene todos los campos");
                    }
                }


            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajeEliminar("Eliminar", "Esta seguro que desea elimnar?");
            }
        });

        fecha_text.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment picker = new DatePickerFragment();
                        picker.show(getFragmentManager(), "datePicker");

                    }
                }
        );



        // Obtener valor extra
        idRegistro = getArguments().getString(Conexion.EXTRA_ID);

        if (idRegistro != null) {
            cargarDatos();
        }

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    private void cargarDatos() {
        // Añadiendo idMeta como parámetro a la URL
        String newURL = Conexion.GET_BY_ID_EST + "?id=" + idRegistro;

        // Consultar el detalle de la meta
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        newURL,
                        null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesa la respuesta GET_BY_ID
                                procesarRespuestaGet(response);
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

    /**
     * Procesa la respuesta de obtención obtenida desde el sevidor     *
     */
    private void procesarRespuestaGet(JSONObject response) {

        try {
            String estado = response.getString("estado");

            switch (estado) {
                case "1":
                    JSONObject meta = response.getJSONObject("mensaje");
                    // Guardar instancia
                    estudiante = gson.fromJson(meta.toString(), Estudiante.class);
                    // Setear valores de la meta
                    cargarDatosEstudiante(estudiante);
                    break;

                case "2":
                    String mensaje = response.getString("mensaje");
                    // Mostrar mensaje
                    Toast.makeText(
                            getActivity(),
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    // Enviar código de falla
                    getActivity().setResult(Activity.RESULT_CANCELED);
                    // Terminar actividad
                    getActivity().finish();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // CARGA LOS DATOS PARA EDITAR
    private void cargarDatosEstudiante(Estudiante estudiante) {
        // Cargar valores
        nomEstudiante.setText(estudiante.getESTNOMBRE());
        logIn.setText(estudiante.getESTLOGIN());
        fecha_text.setText(estudiante.getESTFECHANAC());
        clave.setText(estudiante.getESTCLAVE());
        txtIdentifica.setText(estudiante.getESTIDENTIFICA());
        txtCorreo.setText(estudiante.getESTCORREO());
        if (estudiante.getESTGENERO().equals("Masculino"))
        rbMasculino.setChecked(true);
        else
        rbFemenino.setChecked(true);
    }


    public boolean validarCambios() {
        return true;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Contribución a la AB
    }

    public void mensaje(String titulo, String mensaje){
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getActivity(), MenuAdministrador.class);
                startActivity(i);
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {
                Intent i = new Intent(getActivity(), MenuAdministrador.class);
                startActivity(i);
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }







    public void actualizarFecha(int ano, int mes, int dia) {
        // Setear en el textview la fecha
        fecha_text.setText(ano + "-" + (mes + 1) + "-" + dia);
    }


    public void mensajeEliminar(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                estudianteModelo.eliminarEstudiantes(idRegistro);
                mensajeCerrar("Registro Eliminado", "Se ha eliminado el registro");
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
