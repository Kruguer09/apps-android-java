package iestrassierra.pmdm.listview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AdaptadorCapitales extends ArrayAdapter<Object> {
    private Activity context;
    private Capital[] datos;
    AdaptadorCapitales(Activity context, Capital[] datos) {
        super(context, R.layout.elemento_lista, datos);
        this.context = context;
        this.datos = datos;
    }
    static class ViewHolder {
        TextView capital;
        TextView habitantes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        ViewHolder holder = new ViewHolder();
        if(item == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            item = inflater.inflate(R.layout.elemento_lista, null);
            holder.capital = item.findViewById(R.id.tvcapital);
            holder.habitantes = item.findViewById(R.id.tvhabitantes);
            item.setTag(holder);
        }else{
            holder = (ViewHolder) item.getTag();
        }
        holder.capital.setText(datos[position].getCapital());
        holder.habitantes.setText(datos[position].getHabitantes());
        return (item);
    }
}
