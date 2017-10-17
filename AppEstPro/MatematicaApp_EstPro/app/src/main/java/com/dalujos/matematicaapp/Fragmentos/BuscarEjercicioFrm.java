package com.dalujos.matematicaapp.Fragmentos;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dalujos.matematicaapp.CapaDatos.Conexion;
import com.dalujos.matematicaapp.CapaDatos.VolleySingleton;
import com.dalujos.matematicaapp.R;
import com.dalujos.matematicaapp.utils.Ejercicio;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class BuscarEjercicioFrm extends Fragment {

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = BuscarEjercicioFrm.class.getSimpleName();


    private TextView nombre;
    private TextView descripcion;
    private TextView respuesta;
    private TextView fechaRes;
    private ImageView imgEnvi, imgRes;

    //private ImageButton editButton;
    private String extra;
    private Gson gson = new Gson();
    private static String tipo1;

    public BuscarEjercicioFrm() {
    }

    public static BuscarEjercicioFrm createInstance(String idMeta, String tipo) {
        BuscarEjercicioFrm detailFragment = new BuscarEjercicioFrm();
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
        descripcion = (TextView) v.findViewById(R.id.descrip);
        respuesta = (TextView) v.findViewById(R.id.respuesta);
        fechaRes = (TextView) v.findViewById(R.id.fecRespues);
        imgEnvi = (ImageView) v.findViewById(R.id.imageView3);
        imgRes = (ImageView) v.findViewById(R.id.imageView4);


        // Obtener extra del intent de envío
        extra = getArguments().getString(Conexion.EXTRA_ID);
        tipo1 = getArguments().getString("Tipo");

        // Cargar datos desde el web service
        cargarDatos();

        return v;
    }

    /**
     * Obtiene los datos desde el servidor
     */
    public void cargarDatos() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //mensaje(prefs.getString("logIn", "Sin Conexión"), prefs.getString("id_EstLog", "-1"));
        String newURL = Conexion.GET_IMGENV_ID + "?id=" + extra;

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
                    Ejercicio ejercicio = gson.fromJson(object.toString(), Ejercicio.class);

                    // Seteando valores en los views
                    nombre.setText(ejercicio.getEJENOMBRE());
                    descripcion.setText(ejercicio.getEJEDESCRIPCION());
                    respuesta.setText(ejercicio.getEJETEXTORES());
                    fechaRes.setText(ejercicio.getEJEFECHARESUELTO());

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
