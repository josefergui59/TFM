package com.dalujos.matematicaapp.CapaDatos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.utils.Profesor;
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
public class ProfesorControlador {

    private Gson gson = new Gson();
    private List<Profesor> profesor = null;
    private Boolean Boolconexion = false;

    private static Context contexto;
    public ProfesorControlador(Context contexto){
        this.contexto = contexto;
    }

    public boolean guardarProfesor(
            String PROEJERCICIORES          ,
            String PROFECHACREACION,
            String PROLOGIN,
            String PROCLAVE,
            String PRONOMBRE,
            String PROFECHANAC,
            String PROGENERO,
            String PROIDENTIFICA,
            String PROCORREO) {


        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("PROLOGIN", PROLOGIN);
        map.put("PROCLAVE", PROCLAVE);
        map.put("PROFECHANAC", PROFECHANAC);
        map.put("PROGENERO", PROGENERO);
        map.put("PRONOMBRE", PRONOMBRE);
        map.put("PROFECHACREACION", PROFECHACREACION);
        map.put("PROEJERCICIORES", PROEJERCICIORES);
        map.put("PROIDENTIFICA", PROIDENTIFICA);
        map.put("PROCORREO", PROCORREO);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);


        // Actualizar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.INSERT_PRO,
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

    public void cargarProfesor() {
        // Petición GET
        VolleySingleton.
                getInstance(contexto).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                Conexion.GET_PRO,
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
                                                "Error 1 " + error.toString(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                }

                        )
                );
    }

    public void eliminarRegistro(String id) {

        HashMap<String, String> map = new HashMap<>();// MAPEO

        map.put("idProfesor", id);// Identificador

        JSONObject jobject = new JSONObject(map);// Objeto Json

        // Eliminar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.DELETE_PRO,
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

    public void actualizarDatos(String PRONOMBRE ,
                                String PROCLAVE,
                                String PROLOGIN,
                                String PROFECHANAC ,
                                String PROFECHACREACION  ,
                                String PROGENERO,
                                String idRegistro,
                                String PROIDENTIFICA,
                                String PROCORREO)
    {

        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("PROLOGIN", PROLOGIN);
        map.put("PROCLAVE", PROCLAVE);
        map.put("PROFECHANAC", PROFECHANAC);
        map.put("PROGENERO", PROGENERO);
        map.put("PRONOMBRE", PRONOMBRE);
        map.put("PROFECHACREACION", PROFECHACREACION);
        map.put("PROEJERCICIORES", " ");
        map.put("PROIDENTIFICA", PROIDENTIFICA);
        map.put("PROCORREO", PROCORREO);
        map.put("PROCODIGO", idRegistro);
        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.UPDATE_PRO,
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

    private void llenarProfesor(Profesor[] profesor){
        this.profesor = Arrays.asList(profesor);
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

                    JSONArray mensaje = response.getJSONArray("profesor");
                    Profesor[] profesor = gson.fromJson(mensaje.toString(), Profesor[].class);
                    llenarProfesor(profesor);

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
    public boolean guardarImagen(
            String PROCODIGO,
            String EJEIMAGENRES,
            String EJETEXTORES,
            String EJECODIGO){


        HashMap<String, String> map = new HashMap<>();// Mapeo previo

        map.put("PROCODIGO", PROCODIGO);
        map.put("EJEIMAGENRES", EJEIMAGENRES);
        map.put("EJETEXTORES", EJETEXTORES);
        map.put("EJECODIGO", EJECODIGO);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.INSERT_EJER_RES,
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

    public void actualizarEjerPro(String PROCODIGO) {
        HashMap<String, String> map = new HashMap<>();// Mapeo previo
        map.put("PROCODIGO", PROCODIGO);

        // Crear nuevo objeto Json basado en el mapa
        JSONObject jobject = new JSONObject(map);

        // Actualizar datos en el servidor
        VolleySingleton.getInstance(contexto).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.POST,
                        Conexion.UPDATE_PRO_EJER,
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

    public List<Profesor> getProfesor() {
        return profesor;
    }
}
