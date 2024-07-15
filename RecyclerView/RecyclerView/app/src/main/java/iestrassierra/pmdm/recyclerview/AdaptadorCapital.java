package iestrassierra.pmdm.recyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorCapital extends RecyclerView.Adapter {

    private ArrayList<Capital> datos;
    Context contexto;

    public AdaptadorCapital(Context contexto,ArrayList<Capital> datos) {
        this.datos = datos;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemcapital,parent,false);
        CapitalViewHolder capital = new CapitalViewHolder(item);
        return capital;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Asignamos el dato del array correspondiente a la posición actual al
        //objeto ViewHolder, de forma que se represente en el RecyclerView.
        ((CapitalViewHolder) holder).bindCapital(datos.get(position));
    }

    @Override
    public int getItemCount() {
        //Devolvemos el tamaño de array de datos de Capitales
        return datos.size();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    public class CapitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView capital;
        private TextView habitantes;
        private CheckBox favorita;

        //Método constructor
        public CapitalViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            capital = itemView.findViewById(R.id.tvCapital);
            habitantes = itemView.findViewById(R.id.tvHabitantes);
            favorita = itemView.findViewById(R.id.cbFavorita);
        }

        //Método que nos permitirá dar valores a cada campo del objeto ViewHolder y que
        //el mismo pueda ser mostrado en el RecyclerView
        public void bindCapital(Capital c) {
            capital.setText(c.getCapital());
            habitantes.setText(c.getHabitantes());
            favorita.setChecked(c.isFavorita());
        }

        //Método para manejar el evento click en un elemento del RecyclerView
        @Override
        public void onClick(View v) {
            //Extraemos los valores de los campos de la fila en la que hemos hecho click
            String resultado = "La capital seleccionada es " + (((CheckBox) v.findViewById(R.id.cbFavorita)).isChecked() ? "" : "no ")
                    + "favorita: " + ((TextView) v.findViewById(R.id.tvCapital)).getText() +" con "
                    + ((TextView) v.findViewById(R.id.tvHabitantes)).getText() + " habitantes.";
            //Mostramos la información en un diálogo.
            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
            builder.setTitle("Información");
            builder.setMessage(resultado);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }
}
