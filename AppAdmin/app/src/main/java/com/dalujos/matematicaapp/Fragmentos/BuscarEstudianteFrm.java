package com.dalujos.matematicaapp.Fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.CapaVista.ActualizarEstudianteVista;
import com.dalujos.matematicaapp.utils.Estudiante;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class BuscarEstudianteFrm extends Fragment {

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = BuscarEstudianteFrm.class.getSimpleName();


    private TextView nombre;
    private TextView fecha;
    private TextView genero;
    private TextView logIn;
    private TextView id;
    private TextView correo;
    private TextView clave;
    private TextView monedero;
    private ImageButton editButton;
    private String extra;
    private Gson gson = new Gson();
    private static String tipo1;

    public BuscarEstudianteFrm() {
    }

    public static BuscarEstudianteFrm createInstance(String idMeta, String tipo) {
        BuscarEstudianteFrm detailFragment = new BuscarEstudianteFrm();
        Bundle bundle = new Bundle();
        bundle.putString(Conexion.EXTRA_ID, idMeta);
        bundle.putString("Tipo", tipo);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detalle, container, false); // fragmento detalle layout

        nombre = (TextView) v.findViewById(R.id.nomUsuario);
        fecha = (TextView) v.findViewById(R.id.fechaNac);
        genero = (TextView) v.findViewById(R.id.genero);
        logIn = (TextView) v.findViewById(R.id.logIn);
        clave = (TextView) v.findViewById(R.id.contrasenia);
        monedero = (TextView) v.findViewById(R.id.monedero);
        id = (TextView) v.findViewById(R.id.id);
        correo = (TextView) v.findViewById(R.id.correo);
        editButton = (ImageButton) v.findViewById(R.id.fab);

        // Obtener extra del intent de envío
        extra = getArguments().getString(Conexion.EXTRA_ID);
        tipo1 = getArguments().getString("Tipo");
        // BOTON PARA ACTUALIZAR REGISTRO
        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Iniciar actividad de actualización
                        Intent i = new Intent(getActivity(), ActualizarEstudianteVista.class);
                        i.putExtra(Conexion.EXTRA_ID, extra);
                        i.putExtra("Tipo", tipo1);
                        getActivity().startActivityForResult(i, Conexion.CODIGO_ACTUALIZACION);
                    }
                }
        );

        // Cargar datos desde el web service
        cargarDatos();

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        // Añadir parámetro a la URL del web service
        String newURL = Conexion.GET_BY_ID_EST + "?id=" + extra;

        // Realizar petición GET_BY_ID
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(
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
                    Estudiante estudiante = gson.fromJson(object.toString(), Estudiante.class);

                    // Seteando valores en los views
                    nombre.setText(estudiante.getESTNOMBRE());
                    fecha.setText(estudiante.getESTFECHANAC());
                    genero.setText(estudiante.getESTGENERO());
                    logIn.setText(estudiante.getESTLOGIN());
                    clave.setText(estudiante.getESTCLAVE());
                    monedero.setText(estudiante.getESTMONEDERO());
                    id.setText(estudiante.getESTIDENTIFICA());
                    correo.setText(estudiante.getESTCORREO());

                    break;

                case "2":
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;

                case "3":
                    String mensaje3 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje3,
                            Toast.LENGTH_LONG).show();
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
