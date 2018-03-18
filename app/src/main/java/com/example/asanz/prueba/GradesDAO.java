package com.example.asanz.prueba;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aitor on 24/02/2018.
 */

public class GradesDAO {
    /**
     * Función que obtiene las notas del usuario
     * @param callBack
     * @param firstTime
     */
    public void obtenerNotas (final String token, final String idAlumno, final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://api.initech.local/grades/general";
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
                            obtenerNotas(token, idAlumno, callBack, false);
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
     * Función que obtiene las notas del usuario
     * @param callBack
     * @param firstTime
     */
    public void obtenerNotasAsignatura (final String token, final String idAlumno, final String subject, final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://api.initech.local/grades/subject";
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
                            obtenerNotasAsignatura(token, idAlumno, subject, callBack, false);
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
}
