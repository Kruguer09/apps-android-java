package com.example.android.fragmentoviewmodel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentoUno extends Fragment {

    private CompartirViewModel compartirViewModel;
    private TextInputEditText enviarTexto;
    private MaterialButton botonEnviar;

    public FragmentoUno() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        compartirViewModel = new ViewModelProvider(requireActivity()).get(CompartirViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vistaRaiz = inflater.inflate(R.layout.fragmento_uno, container, false);

        botonEnviar = vistaRaiz.findViewById(R.id.bt_enviar);
        enviarTexto = vistaRaiz.findViewById(R.id.campo_enviar);

        enviarTexto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compartirViewModel.setNombre(enviarTexto.getText().toString());
            }
        });

        enviarTexto.setText(compartirViewModel.getNombre().getValue());

        botonEnviar.setOnClickListener(view -> {
            compartirViewModel.setNombre(enviarTexto.getText().toString());
            Toast.makeText(requireContext(), "¡Enviado!", Toast.LENGTH_SHORT).show();
        });

        return vistaRaiz;
    }
}
