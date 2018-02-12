package com.example.asanz.prueba;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceEbookActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_ebook);
        this.showLibro();
    }
    // TODO: 25/05/2017 Conexión con campus para obtener recurso ebook
    /**
     * Muestra un tipo de recurso Ebook
     * */
    public void showLibro(){
        TextView textView =(TextView)findViewById(R.id.ebookLink);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://video.seas.es/util/le2/HIB7001/index.html'> Libro electrónico del Módulo I - Programar en Drupal </a>";
        textView.setText(Html.fromHtml(text));
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