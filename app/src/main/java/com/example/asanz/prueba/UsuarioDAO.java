package com.example.asanz.prueba;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UsuarioDAO {

    /**
     * Función que comprueba si los datos de acceso son correctos
     * @param correo
     * @param pass
     * @param callBack
     * @param firstTime
     */
    public void accesoUsuario (final String correo,final String pass, final ServerCallBack callBack, final boolean firstTime) throws JSONException {

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://api.initech.local/oauth/token";
        //String url = "http://192.168.1.117:3000/api/registro/";
        url = url + correo + pass;
        JSONObject peticion = new JSONObject();
        peticion.put("grant_type", "client_credentials");
        peticion.put("client_id", correo);
        peticion.put("client_secret", pass);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, peticion,
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
                });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

}
