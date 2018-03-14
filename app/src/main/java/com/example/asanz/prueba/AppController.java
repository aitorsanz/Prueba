package com.example.asanz.prueba;

/**
 * Created by aitorleti on 17/2/18.
 */

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by fuste on 21/11/17.
 */

public class AppController extends Application {

    // Atributos
    private static final String TAG = "AppController";

    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private String token;
    private String idAlumno;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Guardar token
     * @param t
     */
    public void setToken(String t){
        this.token = t;
    }
    /**
     * Obtener token
     * @return
     */
    public String getToken(){
        return this.token;
    }
    /**
     * Guardar idAlumno
     * @param i
     */
    public void setIdAlumno(String i){
        this.idAlumno = i;
    }
    /**
     * Obtener idAlumno
     * @return
     */
    public String getIdAlumno(){
        return this.idAlumno;
    }
}