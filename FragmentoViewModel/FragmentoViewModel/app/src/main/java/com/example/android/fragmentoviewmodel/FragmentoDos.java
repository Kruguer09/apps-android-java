package com.example.android.fragmentoviewmodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class FragmentoDos extends Fragment {

    private CompartirViewModel compartirViewModel;
    private TextView textoRecibido;
    private Observer<String> observadorNombre;

    public FragmentoDos() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        compartirViewModel = new ViewModelProvider(requireActivity()).get(CompartirViewModel.class);

        observadorNombre = new Observer<String>() {
            @Override
            public void onChanged(String text) {
                textoRecibido.setText(text);
            }
        };

        compartirViewModel.getNombre().observe(this, observadorNombre);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vistaRaiz = inflater.inflate(R.layout.fragmento_dos, container, false);

        textoRecibido = vistaRaiz.findViewById(R.id.tv_receptor);

        return vistaRaiz;
    }
}
