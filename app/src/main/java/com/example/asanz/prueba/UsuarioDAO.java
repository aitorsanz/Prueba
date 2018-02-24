package com.example.asanz.prueba;


import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;



public class UsuarioDAO {

    /**
     * Función que comprueba si los datos de acceso son correctos
     * @param correo
     * @param pass
     * @param callBack
     * @param firstTime
     */
    public void accesoUsuario (final String correo,final String pass, final ServerCallBack callBack, final boolean firstTime){

        //String url = "https://quiet-lowlands-92391.herokuapp.com/api/registro/";
        String url = "http://10.0.2.2:3000/api/registro/acceso/";
        //String url = "http://192.168.1.117:3000/api/registro/";
        url = url + correo + pass;

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
                    accesoUsuario(correo, pass, callBack, false);
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
