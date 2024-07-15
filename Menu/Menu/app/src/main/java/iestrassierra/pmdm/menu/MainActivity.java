package iestrassierra.pmdm.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        menu.setGroupVisible(R.id.it_group_gestion_noticias,true);
        menu.setGroupVisible(R.id.it_group_principal,true);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.it_insertar_menu:
                Toast.makeText(this, "Pantalla Insertar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.it_eliminar_menu:
                Toast.makeText(this, "Pantalla Eliminar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.preferencias:
                Toast.makeText(this, "Pantalla Preferencias", Toast.LENGTH_SHORT).show();
                break;
            case R.id.it_acercade:
                Toast.makeText(this, "Pantalla AcercaDe", Toast.LENGTH_SHORT).show();
                break;
            case R.id.it_salir:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}