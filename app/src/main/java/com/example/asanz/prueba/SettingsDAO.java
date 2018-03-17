package com.example.asanz.prueba;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aitor on 24/02/2018.
 */

public class SettingsDAO {
    /**
     * Función que obtiene las preferencias del usuario
     * @param callBack
     * @param firstTime
     */
    public void obtenerPreferencias (final String idAlumno, final String token, final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://api.initech.local/preferences/all";
        //String url = "http://192.168.1.117:3000/api/registro/";

        final StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject respuesta = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray();
                            jsonArray.put(respuesta);
                            callBack.onSuccess(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (firstTime && volleyError instanceof TimeoutError) {
                            // note : may cause recursive invoke if always timeout.
                            obtenerPreferencias(idAlumno, token, callBack, false);
                        }
                        else {
                            callBack.onError();
                        }
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idAlumno", idAlumno.trim());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token.trim());
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /**
     * Función que guarda las prefenencias del usuario
     * @param preferencias
     * @param callBack
     * @param firstTime
     */
    public void guardarPreferencias (final JSONObject preferencias,final String token, final ServerCallBack callBack, final boolean firstTime){
        String url = "http://api.initech.local/preferences/save";
        //String url = "http://10.0.2.2:3000/api/partidos/";
        //String url = "http://192.168.1.117:3000/api/partidos
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, preferencias,
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
                            guardarPreferencias(preferencias, token, callBack, false);
                        }
                        else {
                            callBack.onError();
                        }
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token.trim());
                params.put("Content-Type", "application/json; charset=utf-8");
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
}
