package com.example.tarea_3_4_a.ui.altaproducto;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tarea_3_4_a.R;
import com.example.tarea_3_4_a.basedatos.BaseDatosApp;
import com.example.tarea_3_4_a.databinding.FragmentAltaProductoBinding;
import com.example.tarea_3_4_a.ui.entidades.Producto;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AltaProductoFragment extends Fragment {

    private EditText etProducto, etCantidad;
    private Button btAceptar;
    private FragmentAltaProductoBinding binding;
    private BaseDatosApp baseDatosApp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAltaProductoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        etProducto = binding.etProducto;
        etCantidad = binding.etCantidad;

        //Creamos/obtenemos la instancia de la base de datos Room
        baseDatosApp = BaseDatosApp.getInstance(getActivity().getApplicationContext());

        btAceptar = binding.btAceptar;
        //Método que desencadena el guardado de los datos del formulario en la base de datos.
        btAceptar.setOnClickListener(v -> {
            //Método que desencadena el guardado de los datos del formulario en la base de datos.
            //Creamos el objeto que vamos a insertar
            Producto producto = new Producto(etProducto.getText().toString(), Double.parseDouble(etCantidad.getText().toString()));
            //Creamos un objeto de la clase que realiza la inserción en un hilo aparte Executor
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new InsertarProducto(producto));

            //Método para esconder el teclado virtual
            InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etCantidad.getWindowToken(), 0);
            //Ponemos los editText en blanco
            etProducto.setText("");
            etCantidad.setText("");
            Snackbar.make(etCantidad, "Se ha insertado un nuevo registro.", Snackbar.LENGTH_LONG).show();
        });

        return root;
    }
    //Clase que inserta un objeto producto en la base de datos usando un hilo diferente al principal.
    class InsertarProducto implements Runnable {

        private Producto producto;

        public InsertarProducto(Producto producto) {
            this.producto = producto;
        }

        @Override
        public void run() {
            baseDatosApp.productoDAO().insertAll(producto);
        }
    }

}