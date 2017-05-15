package com.example.asanz.prueba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceTextoActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_texto);
        this.showInfo();
    }
    /**
     * Muestra un tipo de recurso Info
     * */
    public void showInfo(){
        TextView vidView = (TextView)findViewById(R.id.myText);
        vidView.setText("El Curso básico de Drupal comprende los elementos mínimos que debe conocer todo desarrollador de aplicaciones web para usar el CMS Drupal en su versión 7." +
                " La curva de aprendizaje de Drupal suele ser bastante compleja al principio para todo aquel que quiera aprender a usarlo, debido principalmente a su filosofía de funcionamiento y los conceptos en los que el sistema se basa.\n" +
                "\n" +
                "Sin embargo, el conocimiento de los conceptos básicos de Drupal es solo el principio de todo lo que ofrece este singular CMS. " +
                "Después de aprender lo más básico del mismo, hay que saber también utilizar los cientos de módulos existentes que extienden su funcionalidad y como moverse a través del ecosistema formado por los miles de desarrolladores que trabajan y contribuyen en este sistema. " +
                "Por último, hay que conocer los secretos de la programación de esos módulos y la capacidad que nos ofrece Drupal para hacer nuestros propios módulos con nuestras propias funcionalidades.");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salir:
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                CharSequence text = "Saliendo";
                TextView textToast = (TextView) layout.findViewById(R.id.text_toast);
                textToast.setText(text);
                Toast toast = new Toast(context);
                toast.setDuration(duration);
                toast.setView(layout);
                toast.show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
