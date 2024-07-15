package com.example.tarea_3_4_a.ui.listacompra;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarea_3_4_a.basedatos.BaseDatosApp;
import com.example.tarea_3_4_a.databinding.FragmentListaCompraBinding;
import com.example.tarea_3_4_a.adaptadores.AdaptadorProducto;
import com.example.tarea_3_4_a.ui.entidades.Producto;
import com.example.tarea_3_4_a.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListaCompraFragment extends Fragment {

    private FragmentListaCompraBinding binding;
    private ListaCompraViewModel listaCompraViewModel;
    private RecyclerView rv;
    private AdaptadorProducto adaptador;
    private List<Producto> datos = new ArrayList<>();
    private Context contexto;
    private BaseDatosApp baseDatosApp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Obtenemos el contexto de ejecución de la actividad de la aplicación.
        contexto = this.getActivity();

        binding = FragmentListaCompraBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.titulo;

        //Vinculamos el RecyclerView del código al del Layout.
        rv = binding.recyclerView;

        //Si el contenido del RecyclerView es fijo y no variará durante la ejecución
        //de la aplicación, es conveniente poner la propiedad de tamaño fijo a true.
        rv.setHasFixedSize(true);

        //Creamos el adaptador
        adaptador = new AdaptadorProducto(contexto, datos);

        //Asignamos el adaptador al RecyclerView
        rv.setAdapter(adaptador);

        //Asignamos un LinearLayout vertical al RecyclerView de forma que los datos se vean en
        //formato lista.
        rv.setLayoutManager( new LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL,false));
        //Registramos al RecyclerView como destino del menú contextual.
        registerForContextMenu(rv);

        listaCompraViewModel = new ViewModelProvider(this).get(ListaCompraViewModel.class);
        //Creamos un observador para el objeto LiveData de ListaCompraViewModel y si se modifica
        //se hace que la lista de productos del adaptador sea igual al contenido del LiveData.
        listaCompraViewModel.getProductos().observe(getViewLifecycleOwner(), adaptador::setDatos);

        return root;
    }

    //Método en el que tratamos las acciones correspondientes a la opción de menú contextual seleccionada
    //por el usuario.
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int posicion = -1;
        try {
            posicion = adaptador.getPosicion();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }
        if(item.getItemId() == R.id.mi1){
            Snackbar.make(this.rv, "Se ha elegido borrar el elemento "+posicion, Snackbar.LENGTH_LONG)
                    .show();
            //Se recupera el objeto a borrar desde la lista del adaptador.
            Producto producto = adaptador.getDatos().get(posicion);

            baseDatosApp = BaseDatosApp.getInstance(contexto);
            Executor executor = Executors.newSingleThreadExecutor();
            //Creamos un objeto de la clase BorrarProducto que realiza el borrado en un hilo aparte
            executor.execute(new BorrarProducto(producto));
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class BorrarProducto implements Runnable {

        private Producto producto;

        public BorrarProducto(Producto producto) {
            this.producto = producto;
        }

        @Override
        public void run() {
            baseDatosApp.productoDAO().delete(producto);
        }
    }
}