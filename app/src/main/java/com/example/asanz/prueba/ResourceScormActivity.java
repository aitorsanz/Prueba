package com.example.asanz.prueba;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by asanz on 11/05/2017.
 */

public class ResourceScormActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_scorm);
        this.showScorm();
    }
    // TODO: 25/05/2017 Conexión con campus para obtener recurso scorm
    /**
     * Muestra un tipo de recurso Scorm
     * */
    public void showScorm(){
        //No reproduce swf hay que probar con scorm
        String url ="file://192.168.56.1/C:/Users/asanz/Downloads/Prueba/app/src/main/files/Ejemplo.swf";
        WebViewClient webViewClient = new WebViewClient();
        WebView webView = new WebView(getApplicationContext());
        webView.setWebViewClient(webViewClient);
        webView.clearCache(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.clearHistory();
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
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
