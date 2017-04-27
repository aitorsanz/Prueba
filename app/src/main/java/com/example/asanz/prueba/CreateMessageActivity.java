package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


/**
 * Created by asanz on 17/04/2017.
 */

public class CreateMessageActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createmessage);
    }

    public void clickBotonEnviarMensaje (View view) {
        EditText mensaje = (EditText)findViewById(R.id.editText);
        EditText rec = (EditText)findViewById(R.id.editPara);
        String mensa = mensaje.getText().toString();
        String para = rec.getText().toString();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        if(para.equals("")){
            CharSequence text = "No se puede enviar un mensaje sin destinatario";
            TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
            textToast.setText(text);
            Toast toast = new Toast(context);
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
        }else{
            CharSequence text = "Mensaje Enviado";
            TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
            textToast.setText(text);
            Toast toast = new Toast(context);
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
            Intent intent = new Intent(this, MessagesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salir:
                Toast.makeText(this, "Saliendo", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*
   Método encargado de coordinar la conversión final de un flujo
   con formato JSON
    */
    public List<String> readJsonStream(InputStream in) throws IOException {

        // Nueva instancia de un lector JSON
        JsonReader reader = new JsonReader(
                new InputStreamReader(in, "UTF-8"));

        try {
            return readCommentsArray(reader);
        } finally {
            reader.close();
        }
    }

    /*
    Este método lee cada elemento al interior de un array JSON
     */
    public List<String> readCommentsArray(JsonReader reader) throws IOException {
        List<String> messages = new ArrayList<>();

        // Se dirige al corchete de apertura del arreglo
        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMessage(reader));
        }

        // Se dirige al corchete de cierre
        reader.endArray();
        return messages;
    }

    /*
    Lee los atributos de cada objeto
     */
    public String readMessage(JsonReader reader) throws IOException {

        // Cuerpo del comentario
        String body = null;

        // Se dirige a la llave de apertura del objeto
        reader.beginObject();

        while (reader.hasNext()) {

            // Se obtiene el nombre del atributo
            String name = reader.nextName();

            if (name.equals("body")) {
                body = reader.nextString();
            } else {
                reader.skipValue();
            }
        }

        // Se dirige a la llave de cierre del objeto
        reader.endObject();
        return body;
    }
}
