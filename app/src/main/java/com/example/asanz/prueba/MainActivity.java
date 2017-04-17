package com.example.asanz.prueba;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button botonEnviar = (Button) findViewById(R.id.access);
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                URL url;
                EditText usuario =(EditText)findViewById(R.id.login);
                EditText pass = (EditText)findViewById(R.id.pass);
                String user = usuario.getText().toString();
                String password = pass.getText().toString();
                if(user.equals("aitor") && password.equals("aitor")){
                    startActivity(new Intent(getApplicationContext(),SecondActivity.class));
                }else{
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario o Contraseña inválidos", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                /*
                try {
                    // Creando un objeto URL
                    url = new URL("http://moodle.local/login/token.php?username="+user+"&password="+password+"&service=moodle_mobile_app");
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                    // Realizando la petición GET
                    URLConnection con = url.openConnection();
                    /*con.connect();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(con.getContent().toString());
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    // Leyendo el resultado
                    //con.getInputStream();
                    //BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String linea;
                    /*while ((linea = in.readLine()) != null) {
                        System.out.println(linea);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }*/
            }
        });
    }

}
