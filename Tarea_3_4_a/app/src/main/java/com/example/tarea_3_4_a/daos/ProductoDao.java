package com.example.tarea_3_4_a.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tarea_3_4_a.ui.entidades.Producto;

import java.util.List;

@Dao
public interface ProductoDao {
    //Anotación que permite realizar una consulta de todos los productos de la lista
    @Query("SELECT * FROM producto")
    //Método que realiza la consulta anterior
    //En este caso haremos que esta consulta se regenere cada vez que se produzcan cambios
    //en la base de datos mediante un objeto LiveData.
    LiveData<List<Producto>> getAll();

    //Anotación que permite realizar una consulta para los productos con uns ids determinados
    @Query("SELECT * FROM producto WHERE _id IN (:prodIds)")
    //Método que realiza la consulta anterior
    List<Producto> loadAllByIds(int[] prodIds);

    //Anotación que permite realizar una consulta para un producto para un nombre determinado
    @Query("SELECT * FROM producto WHERE producto LIKE :producto LIMIT 1")
    //Método que realiza la consulta anterior
    Producto findByProducto(String producto);

    //Anotación que permite realizar la inserción de una relación de productos
    @Insert
    //Método que realiza la inserción anterior
    void insertAll(Producto... productos);

    //Anotación que permite realizar el borrado de un producto
    @Delete
    //Método que realiza el borrado anterior
    void delete(Producto producto);
}

