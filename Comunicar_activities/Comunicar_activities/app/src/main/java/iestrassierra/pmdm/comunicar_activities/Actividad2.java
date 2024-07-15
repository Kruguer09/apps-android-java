package iestrassierra.pmdm.comunicar_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Actividad2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad2);

        WebView webView1 = findViewById(R.id.wb1);
        Bundle bundle = getIntent().getExtras();
        String direccion = bundle.getString("direccion");
        webView1.setWebViewClient(new WebViewClient());
        webView1.loadUrl("https://" + direccion);
    }
    public void finalizar (View view){
        finishAffinity();
    }
    public void volver(View view){
        Intent intentVolver = new Intent();
        intentVolver.putExtra("Resultado","Venimos de la segunda pantalla");
        setResult(RESULT_OK, intentVolver);
        finish();
    }

}