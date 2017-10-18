package com.dalujos.matematicaapp.CapaDatos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.utils.Estudiante;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JoseFernando on 12/06/2017.
 */
public class EstudianteControlador {

    private Gson gson = new Gson();
    private List<Estudiante> estudiante = null;
    private Boolean Boolconexion = false;

    private static Context contexto;
    public EstudianteControlador(Context contexto){
        this.contexto = contexto;
    }

    public boolean guardarEstudiante(
            String ESTMONEDERO          ,
            String ESTFECHACREACION,
            String ESTLOGIN,
            String ESTCLAVE,
            String ESTNOMBRE,
            String ESTFECHANAC,
            String ESTGENERO,
            String ESTIDENTIFICA,
            String ESTCORREO){


        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("ESTLOGIN", ESTLOGIN);
        map.put("ESTCLAVE", ESTCLAVE);
        map.put("ESTFECHANAC", ESTFECHANAC);
        map.put("ESTGENERO", ESTGENERO);
        map.put("ESTNOMBRE", ESTNOMBRE);
        map.put("ESTFECHACREACION", ESTFECHACREACION);
        map.put("ESTMONEDERO", ESTMONEDERO);
        map.put("ESTIDENTIFICA", ESTIDENTIFICA);
        map.put("ESTCORREO", ESTCORREO);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.INSERT_EST,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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
        return true;
    }
    public void actualizarCreditos(String ESTMONEDERO,
                                   String idRegistro){
        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("ESTMONEDERO", ESTMONEDERO);
        map.put("ESTCODIGO", idRegistro);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.UPDATE_CRE,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta del servidor
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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
    public void cargarEstudiantes() {
        // Petición GET
        VolleySingleton.
                getInstance(contexto).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Conexion.GET_EST,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta1(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(
                                                contexto,
                                                error.toString(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }

                        )
                );
    }

    public void eliminarRegistro(String id) {

        HashMap<String, String> map = new HashMap<>();// MAPEO

        map.put("idEstudiante", id);// Identificador

        JSONObject jobject = new JSONObject(map);// Objeto Json

        // Eliminar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.DELETE_EST,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta
                                procesarRespuestaEliminar(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                               /* Toast.makeText(
                                        contexto,
                                        error.getMessage(),
                                        Toast.LENGTH_LONG).show();*/
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

    public void actualizarDatos(String ESTNOMBRE ,
    String ESTCLAVE ,
    String ESTLOGIN ,
    String ESTFECHANAC ,
    String ESTFECHACREACION  ,
    String ESTGENERO,
    String idRegistro,
    String ESTIDENTIFICA,
    String ESTCORREO)
    {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("ESTLOGIN", ESTLOGIN);
        map.put("ESTCLAVE", ESTCLAVE);
        map.put("ESTFECHANAC", ESTFECHANAC);
        map.put("ESTGENERO", ESTGENERO);
        map.put("ESTNOMBRE", ESTNOMBRE);
        map.put("ESTFECHACREACION", ESTFECHACREACION);
        map.put("ESTMONEDERO", " ");
        map.put("ESTIDENTIFICA", ESTIDENTIFICA);
        map.put("ESTCORREO", ESTCORREO);
        map.put("ESTCODIGO", idRegistro);
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.UPDATE_EST,
                        jobject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaActualizar(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Log", "Error Volley: " + error.getMessage());
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
    private void procesarRespuestaActualizar(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            contexto,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            contexto,
                            mensaje,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void procesarRespuestaEliminar(JSONObject response) {

        try {
            // Obtener estado
            String estado = response.getString("estado");
            // Obtener mensaje
            String mensaje = response.getString("mensaje");

            switch (estado) {
                case "1":
                    // Mostrar mensaje
                    Toast.makeText(
                            contexto,
                            mensaje,
                            Toast.LENGTH_LONG).show();

                    break;

                case "2":
                    // Mostrar mensaje
                    Toast.makeText(
                            contexto,
                            mensaje,
                            Toast.LENGTH_LONG).show();

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void llenarEstudiante(Estudiante[] estudiante){
        this.estudiante = Arrays.asList(estudiante);
        Boolconexion = true;
    }

    public boolean probarConexion(){
        return Boolconexion;
    }

    private void procesarRespuesta1(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");

            switch (estado) {
                case "1": // EXITO

                    JSONArray mensaje = response.getJSONArray("estudiante");
                    Estudiante[] estudiante = gson.fromJson(mensaje.toString(), Estudiante[].class);
                    llenarEstudiante(estudiante);

                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            contexto,
                            "No hay conexión con el servidor",
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {

        }

    }

    public List<Estudiante> getEstudiante() {
        return estudiante;
    }
}
