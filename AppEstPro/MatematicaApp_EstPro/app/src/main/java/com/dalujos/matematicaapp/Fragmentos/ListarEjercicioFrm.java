package com.dalujos.matematicaapp.Fragmentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.Adapter.AdaptadorEjercicio;
import com.dalujos.matematicaapp.Adapter.AdaptadorEstudiante;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.Modelo.EstudianteModelo;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.Ejercicio;
import com.dalujos.matematicaapp.utils.Estudiante;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;



public class ListarEjercicioFrm extends Fragment {


    private static final String TAG = ListarEjercicioFrm.class.getSimpleName();

    private AdaptadorEjercicio adapter;

    private RecyclerView lista;

    private RecyclerView.LayoutManager lManager;

    private Gson gson = new Gson();

    private String tipo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_listar_registro, container, false);
        lista = (RecyclerView) v.findViewById(R.id.reciclador);
        lista.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getActivity());
        lista.setLayoutManager(lManager);

        tipo = "";
        // Cargar datos en el adaptador
        cargarAdaptador();
        return v;
    }

    public void mensaje(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void cargarAdaptador() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //mensaje(prefs.getString("logIn", "Sin Conexión"), prefs.getString("id_EstLog", "-1"));
        String newURL = Conexion.GET_EJER + "?id=" + prefs.getString("id_EstLog", "-1");
        Log.d(TAG, newURL);
        // Petición GET
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                newURL,
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
            Log.d(TAG, estado);
            switch (estado) {
                case "1": // EXITO
                    // Obtener array "estudiante" Json
                    JSONArray mensaje = response.getJSONArray("mensaje");
                    // Parsear con Gson
                    Ejercicio[] ejercicio = gson.fromJson(mensaje.toString(), Ejercicio[].class);
                    // Inicializar adaptador
                    adapter = new AdaptadorEjercicio(Arrays.asList(ejercicio), getActivity(), tipo);
                    // Setear adaptador a la lista
                    lista.setAdapter(adapter);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    mensajeCerrar("Información", "Sin Registros");
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }
    public void mensajeCerrar(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {
                getActivity().finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}
