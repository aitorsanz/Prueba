package com.example.asanz.prueba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;


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
                String user = usuario.getText().toString();
                String password = pass.getText().toString();
                //Constructores TOAST
                final Context context = getApplicationContext();
                final int duration = Toast.LENGTH_SHORT;
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                final UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.accesoUsuario(user, password, new ServerCallBack() {
                    @Override
                    public void onSuccess(JSONArray result) {
                        if (result.length() == 0){
                            startActivity(new Intent(getApplicationContext(),SecondActivity.class));
                        }
                    }
                    @Override
                    public void onError() {
                        CharSequence text = "Usuario o contraseña inválidos";
                        TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                        textToast.setText(text);
                        Toast toast = new Toast(context);
                        toast.setDuration(duration);
                        toast.setView(layout);
                        toast.show();
                    }
                }, true);
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
