package com.example.asanz.prueba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;



/**
 * Created by asanz on 17/04/2017.
 */

public class BandejaActivity extends BaseActivity {
    private static final int ELIMINAR = 1;

    TextView tv[];
    protected void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        String bandeja = getIntent().getStringExtra("bandeja");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandeja);
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        final LinearLayout gradesLayout = (LinearLayout) this.findViewById(R.id.bandejalayout);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final MessagesDAO messagesDAO = new MessagesDAO();
        if(bandeja.equals("salida")){
            messagesDAO.obtenerMensajesSalida(token, idAlumno, new ServerCallBack() {
                @Override
                public void onSuccess(JSONArray result) {
                    //TODO mostras listado de mensajes del usuario
                    try {
                        String response = null;
                        response = result.getString(0);
                        JSONObject respuesta = new JSONObject(response);
                        JSONArray array = null;
                        array = respuesta.getJSONArray("data");
                        Log.d("Mensaje", array.toString());
                        int tam = array.length();
                        tv = new TextView[tam];
                        for (int i = 0; i < tam; i++) {
                            String nombre = array.getJSONObject(i).getString("receiverName");
                            String asunto = array.getJSONObject(i).getString("asunto");
                            String fecha = array.getJSONObject(i).getString("fecha");
                            String mensaje = nombre + " - " +  asunto + " - " + fecha;
                            tv[i] = new TextView(getApplicationContext());
                            tv[i].setText(mensaje);
                            tv[i].setId(i);
                            gradesLayout.addView(tv[i]);
                            tv[i].setOnClickListener(onclicklistener);
                            tv[i].setOnLongClickListener(onLongClickListener);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onError() {
                    CharSequence text = "Imposible cargar los eventos";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
            }, true);

        }else{
            messagesDAO.obtenerMensajesEntrada(token, idAlumno, new ServerCallBack() {
                @Override
                public void onSuccess(JSONArray result) {
                    try {
                        String response = null;
                        response = result.getString(0);
                        JSONObject respuesta = new JSONObject(response);
                        JSONArray array = null;
                        array = respuesta.getJSONArray("data");
                        int tam = array.length();
                        tv = new TextView[tam];
                        for (int i = 0; i < tam; i++) {
                            String nombre = array.getJSONObject(i).getString("senderName");
                            String apellidos = array.getJSONObject(i).getString("senderSurname");
                            String asunto = array.getJSONObject(i).getString("asunto");
                            String fecha = array.getJSONObject(i).getString("fecha");
                            String mensaje = nombre + " " + apellidos + " - " + asunto + " " + fecha;
                            tv[i] = new TextView(getApplicationContext());
                            tv[i].setText(mensaje);
                            tv[i].setId(i);
                            gradesLayout.addView(tv[i]);
                            tv[i].setOnClickListener(onclicklistener);
                            tv[i].setOnLongClickListener(onLongClickListener);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onError() {
                    CharSequence text = "Imposible cargar los eventos";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
            }, true);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
        //return true;
    }

    View.OnClickListener onclicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v == tv[0]){
                Log.d("entra", tv[0].toString());
                //do whatever you want....
            }
        }
    };
    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            // TODO Auto-generated method stub
            if(v == tv[0]){
                Log.d("entra2", tv[0].toString());
                //do whatever you want....
                registerForContextMenu(tv[0]);
            }
            return false;
        }
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, ELIMINAR, Menu.NONE, "Eliminar mensaje");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case ELIMINAR:
                final MessagesDAO messagesDAO = new MessagesDAO();
                /*messagesDAO.borrarMensaje(token, idAlumno, new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        //TODO mostras listado de mensajes del usuario

                    }
                    @Override
                    public void onError() {
                        CharSequence text = "Imposible cargar los eventos";
                        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                        textToast.setText(text);
                        Toast toast = new Toast(context);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();
                    }
                }, true);*/
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
