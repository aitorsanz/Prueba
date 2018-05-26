package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by asanz on 17/04/2017.
 */
// TODO: 25/05/2017 Conexión con campues para enviar mensaje
public class CreateMessageActivity extends BaseActivity {
    final HashMap<Integer, String> detinatarios = new HashMap<Integer, String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        String bandeja = getIntent().getStringExtra("bandeja");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmessage);
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        final LinearLayout gradesLayout = (LinearLayout) this.findViewById(R.id.bandejalayout);
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final MessagesDAO messagesDAO = new MessagesDAO();
        messagesDAO.obtenerDestinatarios(token, idAlumno, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                //TODO mostras listado de mensajes del usuario
                try {
                    String response = null;
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = null;
                    array = respuesta.getJSONArray("data");
                    int tam = array.length();
                    final Spinner dest = (Spinner) findViewById(R.id.destinatario);
                    String[] spinnerArray = new String[tam];
                    for(int i = 0; i < tam; i++){
                        String name = array.getJSONObject(i).getString("firstName");
                        String ape1 = array.getJSONObject(i).getString("firstLastname");
                        String ape2 = array.getJSONObject(i).getString("secondLastname");
                        //int id = Integer.parseInt(array.getJSONObject(i).getString("id"));
                        String id = array.getJSONObject(i).getString("id");
                        String nombre = name + " " + ape1 + " " + ape2;
                        detinatarios.put(i, id);
                        spinnerArray[i] = nombre;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, spinnerArray);
                    //adapter.add(detinatarios);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    dest.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar los usuarios";
                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);
                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
            }
        }, true);
    }

    public void clickBotonEnviarMensaje (View view) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();

        final Spinner dest = (Spinner) findViewById(R.id.destinatario);

        EditText asunto = (EditText)findViewById(R.id.asunto);
        EditText mensaje = (EditText)findViewById(R.id.mensaje);

        String asun = asunto.getText().toString();
        String mensa = mensaje.getText().toString();
        String name = dest.getSelectedItem().toString();
        String id = detinatarios.get(dest.getSelectedItemPosition());
        Log.d("nombre", name);
        Log.d("id", id);

        JSONObject message = new JSONObject();
        try {
            message.put("asunto", asun);
            message.put("mensaje", mensa);
            message.put("fkIdPersona", idAlumno);
            message.put("fkIdPersonaReceptora", id);
            message.put("idAlumno", idAlumno);
            final Intent intent = new Intent(this, MessagesActivity.class);
            final Context context = getApplicationContext();
            final int duration = Toast.LENGTH_SHORT;
            LayoutInflater inflater = getLayoutInflater();
            final View layout = inflater.inflate(R.layout.custom_toast,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            final MessagesDAO messagesDAO = new MessagesDAO();
            messagesDAO.crearMensaje(message, token, new ServerCallBack() {
                @Override
                public void onSuccess(JSONArray result) {
                    CharSequence text = "Mensaje enviado";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                    startActivity(intent);
                }
                @Override
                public void onError() {
                    CharSequence text = "Sucedió un problema enviando el mensaje";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
            }, true);
        } catch (JSONException e) {
            e.printStackTrace();
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
}
