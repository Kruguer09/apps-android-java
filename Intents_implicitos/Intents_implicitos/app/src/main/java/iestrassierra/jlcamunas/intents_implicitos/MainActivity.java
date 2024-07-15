package iestrassierra.jlcamunas.intents_implicitos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CALL_PHONE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView tv1;
    private ImageView im1;
    private Intent intent = null;

    private Button bt1, bt2, bt3, bt4, bt5, bt6, bt7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.textView1);
        im1 = findViewById(R.id.imageView1);

        bt1 = findViewById(R.id.button1);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);
        bt4 = findViewById(R.id.button4);
        bt5 = findViewById(R.id.button5);
        bt6 = findViewById(R.id.button6);
        bt7 = findViewById(R.id.button7);

        bt1.setOnClickListener(this::llamarIntent);
        bt2.setOnClickListener(this::llamarIntent);
        bt3.setOnClickListener(this::llamarIntent);
        bt4.setOnClickListener(this::llamarIntent);
        bt5.setOnClickListener(this::llamarIntent);
        bt6.setOnClickListener(this::llamarIntent);
        bt7.setOnClickListener(this::llamarIntent);
    }

    public void llamarIntent(View vista) {
        // En función del botón pulsado, lanzamos un Intent implícito
        // que será respondido por la aplicación adecuada del sistema
        int id = vista.getId();
        if (id == R.id.button1) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.iestrassierra.com/"));
            startActivity(intent);
        } else if (id == R.id.button2) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                // No tenemos permiso.¿Debemos mostrar una explicación para pedir el permiso?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CALL_PHONE)) {
                    // Muestra una explicación del porqué pedir el permiso.
                    Toast.makeText(this, "Se necesita permiso para realizar llamadas",
                            Toast.LENGTH_SHORT).show();
                }
                // Solicitamos el permiso.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PHONE);
            } else {
                //Tenemos permiso.
                llamar();
            }
        } else if (id == R.id.button3) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:(+34)555900800"));
            startActivity(intent);
        } else if (id == R.id.button4) {
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:37.8847,-4.77915?z=13"));
            startActivity(intent);
        } else if (id == R.id.button5) {
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=IES Trassierra"));
            startActivity(intent);
        } else if (id == R.id.button6) {//Al ser el acceder a la cámara un permiso arriesgado o peligroso
            //se deben pedir permisos en tiempo de ejecución
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // No tenemos permiso.¿Debemos mostrar una explicación para pedir el permiso?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
                    // Muestra una explicación del por qué de perdir el permiso.
                    Toast.makeText(this, "Se necesita permiso para acceder a la cámara.",
                            Toast.LENGTH_SHORT).show();
                }
                // Solicitamos el permiso.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_IMAGE_CAPTURE);
            } else {
                //Tenemos permiso.
                capturar();
            }
        } else if (id == R.id.button7) {
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("content://contacts/people/"));
            startActivity(intent);
        }
    }

    ActivityResultLauncher<Intent> lanzadorActividades = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        assert result.getData() != null;
                        String resultado = result.getData().toUri(0);
                        tv1.setText(resultado);
                        // Mostramos la foto de la cámara en el ImageView
                        // Los objetos son pasados entre las Actividades mediante
                        // objetos de la clase Parcelable, de forma parecida a como
                        // se pasan los tipos básicos mediante objetos de la clase
                        // Bundle.
                        im1.setImageBitmap((Bitmap) result.getData().getParcelableExtra("data"));
                    }
                }
            });

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    // Permiso aceptado y procedemos a realizar la llamada
                    llamar();
                else
                    // Permiso denegado
                    Toast.makeText(this, "No se ha aceptado el permiso", Toast.LENGTH_SHORT).show();
                return;
            // Gestionar el resto de permisos
            case REQUEST_IMAGE_CAPTURE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    // Permiso aceptado y procedemos a realizar la llamada
                    capturar();
                else
                    // Permiso denegado
                    Toast.makeText(this, "No se ha aceptado el permiso", Toast.LENGTH_SHORT).show();
                return;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void capturar() {
        intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // Lanzamos la actividad y esperamos su resultado
        lanzadorActividades.launch(intent);
    }

    private void llamar() {
        intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)555900800"));
        startActivity(intent);
    }
}