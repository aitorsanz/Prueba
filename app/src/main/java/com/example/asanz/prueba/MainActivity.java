package com.example.asanz.prueba;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button botonEnviar = (Button) findViewById(R.id.access);
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText usuario = (EditText)findViewById(R.id.login);
                EditText pass = (EditText)findViewById(R.id.pass);
                final String user = usuario.getText().toString();
                final String password = pass.getText().toString();
                //Constructores TOAST
                final Context context = getApplicationContext();
                final int duration = Toast.LENGTH_SHORT;
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                final UsuarioDAO usuarioDAO = new UsuarioDAO();
                try {
                    usuarioDAO.accesoUsuario(user, password, new ServerCallBack() {
                        @Override
                        public void onSuccess(JSONArray result) {
                            try {
                                String response = result.getString(0);
                                JSONObject respuesta = new JSONObject(response);
                                String token = respuesta.getString("access_token");
                                if (token != null){
                                   AppController global = ((AppController)getApplicationContext());
                                   global.setToken(token);
                                    usuarioDAO.obtenerInfoUsuario(user, token, new ServerCallBack() {
                                        @Override
                                        public void onSuccess(JSONArray result) {
                                            try {
                                                String response = result.getString(0);
                                                JSONObject respuesta = new JSONObject(response);
                                                String tmp = respuesta.getString("result");
                                                respuesta = new JSONObject(tmp);
                                                String idAlumno = respuesta.getString("idAlumno");
                                                if (idAlumno != null){
                                                    AppController global = ((AppController)getApplicationContext());
                                                    global.setIdAlumno(idAlumno);
                                                    startActivity(new Intent(getApplicationContext(),SecondActivity.class));
                                                }
                                            } catch (JSONException e) {
                                                CharSequence text = "Algo ha fallado al loguearse";
                                                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                                                textToast.setText(text);
                                                Toast toast = new Toast(context);
                                                toast.setDuration(duration);
                                                toast.setView(layout);
                                                toast.show();
                                                e.printStackTrace();
                                            }


                                        }
                                        @Override
                                        public void onError() {
                                            CharSequence text = "Ha ocurrido un problema en la conexión";
                                            TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                                            textToast.setText(text);
                                            Toast toast = new Toast(context);
                                            toast.setDuration(duration);
                                            toast.setView(layout);
                                            toast.show();
                                        }
                                    }, true);
                                }
                            } catch (JSONException e) {
                                CharSequence text = "Usuario o contraseña inválidos";
                                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                                textToast.setText(text);
                                Toast toast = new Toast(context);
                                toast.setDuration(duration);
                                toast.setView(layout);
                                toast.show();
                                e.printStackTrace();
                            }


                        }
                        @Override
                        public void onError() {
                            CharSequence text = "Ha ocurrido un problema en la conexión";
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
                /*if(user.equals("aitor") && password.equals("aitor")){
                    startActivity(new Intent(getApplicationContext(),SecondActivity.class));
                }else{
                    CharSequence text = "Usuario o contraseña inválidos";
                    TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                    textToast.setText(text);
                    Toast toast = new Toast(context);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }*/
            }
        });
    }

}
