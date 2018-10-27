package com.example.asanz.prueba;

/**
 * Created by aitorleti on 17/2/18.
 */


import org.json.JSONArray;

public interface ServerCallBack{
    void onSuccess(JSONArray result);
    void onError();
}