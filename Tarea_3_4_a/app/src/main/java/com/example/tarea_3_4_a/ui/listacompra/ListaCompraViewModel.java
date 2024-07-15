package com.example.tarea_3_4_a.ui.listacompra;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tarea_3_4_a.basedatos.BaseDatosApp;
import com.example.tarea_3_4_a.ui.entidades.Producto;

import java.util.List;

public class ListaCompraViewModel extends AndroidViewModel {

    private final LiveData<List<Producto>> productos;
    public ListaCompraViewModel(@NonNull Application application) {
        super(application);
        //Inicializamos el contenido de la lista, al de la tabla de la base de datos
        productos = BaseDatosApp
                .getInstance(application)
                .productoDAO().getAll();
    }

    public LiveData<List<Producto>> getProductos() {
        return productos;
    }
}