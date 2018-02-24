package com.example.asanz.prueba;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Aitor on 24/02/2018.
 */

public class SettingsDAO {
    /**
     * Función que obtiene las preferencias del usuario
     * @param callBack
     * @param firstTime
     */
    public void obtenerPreferencias (final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://10.0.2.2:3000/api/settings/settings";
        //String url = "http://192.168.1.117:3000/api/registro/";

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        callBack.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());
                if (firstTime && error instanceof TimeoutError) {
                    // note : may cause recursive invoke if always timeout.
                    obtenerPreferencias(callBack, false);
                }
                else {
                    callBack.onError();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /**
     * Función que guarda las prefenencias del usuario
     * @param preferencias
     * @param callBack
     * @param firstTime
     */
    public void guardarPreferencias (final JSONObject preferencias, final ServerCallBack callBack, final boolean firstTime){
        String url = "http://10.0.2.2:3000/api/settings/guardar";
        //String url = "http://10.0.2.2:3000/api/partidos/";
        //String url = "http://192.168.1.117:3000/api/partidos/";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, url, preferencias,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = new JSONArray();
                        jsonArray.put(jsonObject);
                        callBack.onSuccess(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (firstTime && volleyError instanceof TimeoutError) {
                            // note : may cause recursive invoke if always timeout.
                            guardarPreferencias(preferencias, callBack, false);
                        }
                        else {
                            callBack.onError();
                        }
                    }
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
}
