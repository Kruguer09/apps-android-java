package com.example.tarea_3_4_a.basedatos;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tarea_3_4_a.daos.ProductoDao;
import com.example.tarea_3_4_a.ui.entidades.Producto;

@Database(entities={Producto.class},version=1, exportSchema = false)
public abstract class BaseDatosApp extends RoomDatabase {
    //Usando el patrón SINGLETON, nos aseguramos que solo haya una instancia de la
    //base de datos creada en nuestra aplicación.
    private static BaseDatosApp INSTANCIA;
    public static BaseDatosApp getInstance(Context context) {
        if (INSTANCIA == null) {
            INSTANCIA = Room.databaseBuilder(
                            context.getApplicationContext(),
                            BaseDatosApp.class,
                            "dbCompra")
                    .build();
        }
        return INSTANCIA;
    }

    public static void destroyInstance() {
        INSTANCIA = null;
    }

    //Método para construir el objeto ProductoDAO con el que accederemos
    //a la base de datos.
    public abstract ProductoDao productoDAO();
}
