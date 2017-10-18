package com.dalujos.matematicaapp.Fragmentos;

import android.os.Bundle;
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
import com.dalujos.matematicaapp.Adapter.AdaptadorEstudiante;
import com.dalujos.matematicaapp.Adapter.AdaptadorProfesor;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.Estudiante;
import com.dalujos.matematicaapp.utils.Profesor;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class ListarProfesorFrm extends Fragment {


    private static final String TAG = ListarProfesorFrm.class.getSimpleName();

    private AdaptadorProfesor adapter;

    private RecyclerView lista;

    private RecyclerView.LayoutManager lManager;

    private Gson gson = new Gson();
    private String tipo;

    public ListarProfesorFrm(String tipo) {
        this.tipo = tipo;
    }

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

    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void cargarAdaptador() {
        // Petición GET
        VolleySingleton.
                getInstance(getActivity()).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Conexion.GET_PRO,
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
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("profesor");
                    // Parsear con Gson
                    Profesor[] profesor = gson.fromJson(mensaje.toString(), Profesor[].class);
                    // Inicializar adaptador
                    adapter = new AdaptadorProfesor(Arrays.asList(profesor), getActivity(), tipo);
                    // Setear adaptador a la lista
                    lista.setAdapter(adapter);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            getActivity(),
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d(TAG, e.getMessage());
        }

    }


}
