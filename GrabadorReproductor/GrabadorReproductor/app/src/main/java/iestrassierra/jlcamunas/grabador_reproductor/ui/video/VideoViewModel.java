package iestrassierra.jlcamunas.grabador_reproductor.ui.video;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VideoViewModel extends ViewModel {

    private final MutableLiveData<Uri> mVideo;

    public VideoViewModel() {
        mVideo = new MutableLiveData<>();
    }

    public LiveData<Uri> getVideo() {
        return mVideo;
    }
    public void setVideo(Uri video){
        mVideo.setValue(video);
    }
}