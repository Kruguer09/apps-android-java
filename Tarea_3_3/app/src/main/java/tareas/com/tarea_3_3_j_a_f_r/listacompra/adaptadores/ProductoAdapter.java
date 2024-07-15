package tareas.com.tarea_3_3_j_a_f_r.listacompra.adaptadores;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tareas.com.tarea_3_3_j_a_f_r.listacompra.modelos.Producto;

public class ProductoAdapter extends ArrayAdapter<Producto> {
    private ArrayList<Producto> mList;
    private Context mContext;
    private int resourceLayout;
    public ProductoAdapter(@NonNull Context context, int resource, int textViewResourceId, ArrayList<Producto> objects) {
        super(context, resource, textViewResourceId, objects);
        this.mList=objects;
        this.mContext=context;
        this.resourceLayout=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.layout_elemento,parent,false);
        }
        return view;
    }
}
