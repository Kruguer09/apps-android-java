package com.example.tarea_3_4_a.ui.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Producto {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="_id")
    public int uid;
    @ColumnInfo(name="producto")
    private String producto;
    @ColumnInfo(name="cantidad")
    private double cantidad;

    public Producto(String producto, double cantidad) {

        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto() {
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
