package com.example.asanz.prueba;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.widget.RadioGroup;


/**
 * Created by asanz on 17/04/2017.
 */

public class SettingsActivity extends BaseActivity {

    private CheckBox recibirEmail;
    private CheckBox mostrarImagen;
    private CheckBox mostrarCorreo;
    private RadioGroup mostrarNombre;
    private EditText nickName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Resources res = getResources();
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        final SettingsDAO settingsDAO = new SettingsDAO();
        settingsDAO.obtenerPreferencias(idAlumno, token, new ServerCallBack() {
            @Override
            public void onSuccess(JSONArray result) {
                //TODO Mostrar las preferencias del usuario
                String response = null;
                try {
                    response = result.getString(0);
                    JSONObject respuesta = new JSONObject(response);
                    JSONArray array = respuesta.getJSONArray("data");
                    for(int i = 0 ; i < array.length() ; i++){
                        String preferencia = array.getJSONObject(i).getString("preferencia");
                        String valor = array.getJSONObject(i).getString("valor");
                        if(preferencia.equals("receive-copy-email")){
                            boolean val = false;
                            if(valor.equals("1")){
                               val = true;
                            }
                            recibirEmail = (CheckBox) findViewById(R.id.receive_copy_email);
                            recibirEmail.setChecked(val);
                        }else if(preferencia.equals("show-email")){
                            boolean val = false;
                            if(valor.equals("1")){
                                val = true;
                            }
                            mostrarCorreo = (CheckBox) findViewById(R.id.show_email);
                            mostrarCorreo.setChecked(val);
                        }else if(preferencia.equals("show-name")){
                            int val = 0;
                            if(valor.equals("usuario")){
                                val = 1;
                            }else if(valor.equals("anonimo")){
                                val = 2;
                            }
                            mostrarNombre = (RadioGroup) findViewById(R.id.show_name);
                            ((RadioButton)mostrarNombre.getChildAt(val)).setChecked(true);
                        }else if(preferencia.equals("show-image")){
                            boolean val = false;
                            if(valor.equals("1")){
                                val = true;
                            }
                            mostrarImagen = (CheckBox) findViewById(R.id.show_imagen);
                            mostrarImagen.setChecked(val);
                        }else if(preferencia.equals("nickname")){
                            if(valor == null){
                                valor = "";
                            }
                            nickName = (EditText)findViewById(R.id.nickname);
                            nickName.setText(valor, TextView.BufferType.EDITABLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onError() {
                CharSequence text = "Imposible cargar las preferencias";
                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);
                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
            }
        }, true);
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

    /**
     * Evento que guarda las preferencias del usuario
     * @param view
     */
    public void clickGuardarPreferencias(View view) {
        AppController global = ((AppController)getApplicationContext());
        String token = global.getToken();
        String idAlumno = global.getIdAlumno();
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        recibirEmail = (CheckBox)findViewById(R.id.receive_copy_email);
        mostrarImagen = (CheckBox)findViewById(R.id.show_imagen);
        mostrarCorreo = (CheckBox)findViewById(R.id.show_email);
        mostrarNombre = (RadioGroup)findViewById(R.id.show_name);
        nickName = (EditText) findViewById(R.id.nickname);
        boolean receiveEmail = recibirEmail.isChecked();
        boolean showImage = mostrarImagen.isChecked();
        boolean showMail = mostrarCorreo.isChecked();
        int showName = mostrarNombre.getCheckedRadioButtonId();
        String showNameCadena = "completo";
        if(showName == 1){
            showNameCadena = "usuario";
        }else if(showName == 2){
            showNameCadena = "anonimo";
        }
        String nickname = nickName.getText().toString();
        JSONObject preferencias = new JSONObject();
        try {
            preferencias.put("receive-copy-email", receiveEmail);
            preferencias.put("show-email", showMail);
            preferencias.put("show-image", showImage);
            preferencias.put("show-name", showNameCadena);
            preferencias.put("nickname", nickname);
            preferencias.put("idAlumno", idAlumno);
            final SettingsDAO settingsDAO = new SettingsDAO();
            settingsDAO.guardarPreferencias(preferencias, token, new ServerCallBack() {
                @Override
                public void onSuccess(JSONArray result) {
                    CharSequence text = "Preferencias guardadas";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
                @Override
                public void onError() {
                    CharSequence text = "Imposible guardar las preferencias";
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

}
