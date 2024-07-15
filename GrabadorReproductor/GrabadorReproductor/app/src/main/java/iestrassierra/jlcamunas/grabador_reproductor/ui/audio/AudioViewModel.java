package iestrassierra.jlcamunas.grabador_reproductor.ui.audio;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AudioViewModel extends ViewModel {

    private final MutableLiveData<Uri> mAudio;

    public AudioViewModel() {
        mAudio = new MutableLiveData<>();
    }

    public LiveData<Uri> getAudio() {
        return mAudio;
    }
    public void setAudio(Uri audio){
        mAudio.setValue(audio);
    }

}