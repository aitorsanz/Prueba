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


public class UsuarioDAO {

    /**
     * Función que comprueba si los datos de acceso son correctos
     * @param correo
     * @param pass
     * @param callBack
     * @param firstTime
     */
    public void accesoUsuario (final String correo, final String pass, final ServerCallBack callBack, final boolean firstTime) throws JSONException {

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://api.initech.local/oauth/token";
        //String url = "http://192.168.1.117:3000/api/registro/";
        final StringRequest req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("respuesta", response);
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
                            try {
                                accesoUsuario(correo, pass, callBack, false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                    params.put("grant_type", "client_credentials");
                    params.put("client_id", correo.trim());
                    params.put("client_secret", pass.trim());
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    /**
     * Función que comprueba si los datos de acceso son correctos
     * @param correo
     * @param token
     * @param callBack
     * @param firstTime
     */
    public void obtenerInfoUsuario (final String correo, final String token, final ServerCallBack callBack, final boolean firstTime) throws JSONException {

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://api.initech.local/login/impersonate";
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
                            try {
                                obtenerInfoUsuario(correo, token, callBack, false);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                params.put("email", correo.trim());
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
