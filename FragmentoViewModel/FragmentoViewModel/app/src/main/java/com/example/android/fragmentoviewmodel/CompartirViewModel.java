package com.example.android.fragmentoviewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompartirViewModel extends ViewModel {

    private MutableLiveData<String> nombre;

    public void setNombre(String nomb) {
        nombre.setValue(nomb);
        //nombre.postValue(nomb); //para llamadas desde hilos en background
    }

    public MutableLiveData<String> getNombre() {
        if (nombre == null) {
            nombre = new MutableLiveData<>();
        }
        return nombre;
    }
}
