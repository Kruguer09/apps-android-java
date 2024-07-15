package tareas.com.tarea_3_3_j_a_f_r.listacompra;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import tareas.com.tarea_3_3_j_a_f_r.listacompra.adaptadores.ListaCompraDatabaseAdapter;
import tareas.com.tarea_3_3_j_a_f_r.listacompra.modelos.Producto;

public class ListaCompraActivity extends AppCompatActivity {
    ListView listaCompra;
    ArrayList<String> listaInformacion;
    ArrayList<Producto> listaProductos;
    ListaCompraDatabaseAdapter conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaCompra=(ListView) findViewById(R.id.lvListaCompra);
        conn=new ListaCompraDatabaseAdapter(getApplicationContext());
        cargarListaCompra();

        ArrayAdapter adaptador=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaInformacion);
        listaCompra.setAdapter(adaptador);

    }

    private void cargarListaCompra() {
        conn.open();
        Producto producto=null;
        listaProductos=new ArrayList<Producto>();
        Cursor cursor=conn.obtenerTodosElementos();
        while (cursor.moveToNext()){
            producto.setId(cursor.getInt(0));
            producto.setsNombre(cursor.getString(1));
            producto.setiCantidad(cursor.getInt(2));
            listaProductos.add(producto);
        }
        obtenerListaInformacion();


    }

    private void obtenerListaInformacion() {
        listaInformacion=new ArrayList<String>();
        for(int i=0;i<listaProductos.size();i++){
            listaInformacion.add(listaProductos.get(i).getsNombre()+" - "+listaProductos.get(i).getiCantidad());
        }
    }
}