package iestrassierra.jlcamunas.grabador_reproductor.ui.foto;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FotoViewModel extends ViewModel {

    private final MutableLiveData<Uri> mFoto;

    public FotoViewModel() {
        mFoto = new MutableLiveData<>();
    }

    public LiveData<Uri> getFoto() {
        return mFoto;
    }
    public void setFoto(Uri foto){
        mFoto.setValue(foto);
    }
}