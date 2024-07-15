package tareas.com.tarea_3_3_j_a_f_r.listacompra.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Producto implements Parcelable {
    private String sNombre;
    private int iCantidad;
    private int id;

    public Producto()  {
    }
    public Producto(int id, String sNombre, int iCantidad) {
        this.id=id;
        this.sNombre = sNombre;
        this.iCantidad = iCantidad;
    }

    protected Producto(Parcel in) {
        sNombre = in.readString();
        iCantidad = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setsNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public void setiCantidad(int iCantidad) {
        this.iCantidad = iCantidad;
    }

    public String getsNombre() {
        return sNombre;
    }

    public int getiCantidad() {
        return iCantidad;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(sNombre);
        parcel.writeInt(iCantidad);
        parcel.writeInt(id);
    }
}
