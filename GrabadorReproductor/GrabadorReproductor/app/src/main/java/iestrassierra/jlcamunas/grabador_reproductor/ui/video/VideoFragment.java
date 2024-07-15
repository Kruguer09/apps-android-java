package iestrassierra.jlcamunas.grabador_reproductor.ui.video;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import iestrassierra.jlcamunas.grabador_reproductor.databinding.FragmentVideoBinding;

public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;
    private VideoViewModel videoViewModel;
    private VideoView contenedorVideo;
    private Handler handler;
    private Executor executor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        binding = FragmentVideoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ProgressBar progressBar = binding.progressBar;

        contenedorVideo = binding.contenedorVideo;
        contenedorVideo.setVisibility(View.INVISIBLE);

        //Handler para comunicar con el hilo principal
        handler = new Handler(Looper.getMainLooper());
        //Executor para ejecutar en hilos secundarios
        executor = Executors.newSingleThreadExecutor();

        //Con el liveData cada vez que se cargue una Uri en el ViewModel
        //situamos el vídeo en el VideoView
        videoViewModel.getVideo().observe(getViewLifecycleOwner(), Uri -> {
            contenedorVideo.setVideoURI(Uri);
                progressBar.setMax(contenedorVideo.getDuration());
                contenedorVideo.setVisibility(View.VISIBLE);
        });

        final Button btVideo = binding.btVideo;
        btVideo.setOnClickListener(v -> {
            //Quitamos la foto del contenedor
            contenedorVideo.setVideoURI(null);
            //Creamos un intent para ir a la carpeta de videos
            Intent aVideos = new Intent();
            aVideos.setType("video/*");
            aVideos.setAction(Intent.ACTION_GET_CONTENT);
            //Creamos otro intent para ir a la aplicación de la cámara de vídeo
            Intent aCamaraVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            //Creamos un tercer intent que permitirá abrir un cuadro de diálogo para que
            //el usuario elija ir a la carpeta (opción principal) o la cámara de vídeo (opción secundaria)
            Intent chooser = new Intent(Intent.ACTION_CHOOSER);
            chooser.putExtra(Intent.EXTRA_TITLE, "Vídeos"); //Título del diálogo
            chooser.putExtra(Intent.EXTRA_INTENT, aVideos); //Opción principal
            Intent[] intentarray= {aCamaraVideo}; //Opciones secundarias, hay 1 sola
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,intentarray);
            lanzadorCamaraVideo.launch(chooser);
        });

        final ImageButton ibPlay = binding.ibReproducir;
        ibPlay.setOnClickListener(v -> {
            if (videoViewModel.getVideo().getValue() != null){
                contenedorVideo.seekTo(0);
                //En un hilo secundario reproducimos el video
                executor.execute(()->{
                    if(!contenedorVideo.isPlaying())
                        contenedorVideo.start();
                });
                //En otro hilo secundario avanzamos el ProgressBar
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (videoViewModel.getVideo().getValue() != null) {
                            int currentPosition = contenedorVideo.getCurrentPosition();
                            progressBar.setProgress(currentPosition);
                        }
                        handler.postDelayed(this, 500);
                    }
                });
            }else{
                Toast.makeText(getContext(), "No hay vídeo para reproducir", Toast.LENGTH_SHORT).show();
            }
        });

        final ImageButton ibPause = binding.ibPausar;
        ibPause.setOnClickListener(v -> {
            if (videoViewModel.getVideo().getValue() != null) {
                if (contenedorVideo.isPlaying()) {
                    contenedorVideo.pause();
                } else {
                    contenedorVideo.start(); // Reanudar la reproducción
                }
            }
        });

        final ImageButton ibRecord = binding.ibParar;
        ibRecord.setOnClickListener(v -> {
            if (videoViewModel.getVideo().getValue() != null) {
                contenedorVideo.stopPlayback();
                contenedorVideo.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }

    ActivityResultLauncher<Intent> lanzadorCamaraVideo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Uri video = o.getData() != null ? o.getData().getData() : null;
                    if (video != null)
                        //Metemos en el ViewModel la Uri del audio
                        videoViewModel.setVideo(video);
                }
            }
    );

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}