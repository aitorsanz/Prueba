package com.example.asanz.prueba;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceFeedbackActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_texto);
        this.showInfo();
    }
    // TODO: 25/05/2017 Conexión con campus para obtener recurso scorm
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
        return super.onCreateOptionsMenu(menu);
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
        //return true;
    }
}
