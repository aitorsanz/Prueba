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

public class MessagesDAO {
    /**
     * Función que obtiene los mensajes de entrada
     * @param callBack
     * @param firstTime
     */
    public void obtenerMensajesEntrada (final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://10.0.2.2:3000/api/messages/entrada";
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
                    obtenerMensajesEntrada(callBack, false);
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
     * Función que obtiene los mensajes de salida
     * @param callBack
     * @param firstTime
     */
    public void obtenerMensajesSalida (final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://10.0.2.2:3000/api/messages/entrada";
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
                    obtenerMensajesSalida(callBack, false);
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
     * Función que crea mensaje
     * @param callBack
     * @param firstTime
     */
    public void crearMensaje (final JSONObject mensaje, final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://10.0.2.2:3000/api/messages/nuevo";
        //String url = "http://192.168.1.117:3000/api/registro/";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, mensaje,
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
                            crearMensaje(mensaje, callBack, false);
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
     * Función que obtiene los eventos del usuario
     * @param callBack
     * @param firstTime
     */
    public void borrarMensaje (final String id, final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://10.0.2.2:3000/api/messages/borrar";
        //String url = "http://192.168.1.117:3000/api/registro/";
        url = url + id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
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
                            borrarMensaje(id, callBack, false);
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
