package iestrassierra.jlcamunas.grabador_reproductor.ui.foto;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import iestrassierra.jlcamunas.grabador_reproductor.databinding.FragmentFotoBinding;

public class FotoFragment extends Fragment {

    private FragmentFotoBinding binding;
    private FotoViewModel fotoViewModel;
    private ImageView contenedorFoto;
    private Executor executor;
    private Handler handler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fotoViewModel = new ViewModelProvider(this).get(FotoViewModel.class);

        binding = FragmentFotoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        contenedorFoto = binding.contenedorFoto;

        //Handler para comunicar con el hilo principal
        handler = new Handler(Looper.getMainLooper());
        //Executor para ejecutar en hilos secundarios
        executor = Executors.newSingleThreadExecutor();

        /* Obtenemos el tamaño del ImageView */
        int anchoContenedor = contenedorFoto.getWidth();
        int altoContenedor = contenedorFoto.getHeight();

        //Con el liveData cada vez que se cargue una Uri en el ViewModel
        //hacemos una serie de cálculos y cargamos la foto en el ImageView
        fotoViewModel.getFoto().observe(getViewLifecycleOwner(), Uri -> {
            //Ejecutamos en un hilo secundario todos los cálculos
            executor.execute(()->{
                /* Obtenemos el tamaño de la imagen */
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                // Convierte la Uri a una ruta de archivo
                String filePath = obtenerRutaDeArchivoDesdeUri(Uri);
                BitmapFactory.decodeFile(filePath, bmOptions);

                int anchoFoto = bmOptions.outWidth;
                int altoFoto = bmOptions.outHeight;

                /* Calculamos qué lado debe reducirse para que encaje en el contenedor */
                int scaleFactor = 1;
                if ((anchoContenedor > 0) || (altoContenedor > 0)) {
                    scaleFactor = Math.min(anchoFoto/anchoContenedor, altoFoto/altoContenedor);
                }
                /* Seteamos el bitmap para que encaje en el contenedor */
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                // Decodificamos el archivo JPEG en Bitmap
                Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
                //Asociamos el bitmap con el imageView en el hilo principal
                handler.post(()->{
                    contenedorFoto.setImageBitmap(bitmap);
                    contenedorFoto.setVisibility(View.VISIBLE);
                });
            });
        });

        final Button btFoto = binding.btFoto;
        btFoto.setOnClickListener(v -> {
            //Quitamos la foto del contenedor
            contenedorFoto.setImageBitmap(null);
            //Creamos un intent para ir a la carpeta de fotos
            Intent aFotos = new Intent();
            aFotos.setType("image/*");
            aFotos.setAction(Intent.ACTION_GET_CONTENT);
            //Creamos otro intent para ir a la aplicación de la cámara
            Intent aCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Creamos un tercer intent que permitirá abrir un cuadro de diálogo para que
            //el usuario elija ir a la carpeta (opción principal) o la cámara de fotos (opción secundaria)
            Intent chooser = new Intent(Intent.ACTION_CHOOSER);
            chooser.putExtra(Intent.EXTRA_TITLE, "Fotos"); //Título del diálogo
            chooser.putExtra(Intent.EXTRA_INTENT, aFotos); //Opción principal
            Intent[] intentarray= {aCamara}; //Opciones secundarias, hay 1 sola
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS,intentarray);
            lanzadorCamara.launch(chooser);
        });
        return root;
    }

    ActivityResultLauncher<Intent> lanzadorCamara = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Intent intentDevuelto = o.getData();
                    if(intentDevuelto != null){
                        Uri fotoSeleccionada = intentDevuelto.getData();
                        if (fotoSeleccionada != null)
                            //Metemos en el ViewModel la Uri de la foto
                            fotoViewModel.setFoto(fotoSeleccionada);
                        else{
                            Bitmap imagenCapturada = (Bitmap) intentDevuelto.getExtras().get("data");
                            contenedorFoto.setImageBitmap(imagenCapturada);
                            contenedorFoto.setVisibility(View.VISIBLE);
                        }

                    }

                }
            }
    );

    // Método para obtener la ruta de archivo desde la Uri
    private String obtenerRutaDeArchivoDesdeUri(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireContext().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}