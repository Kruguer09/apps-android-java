package iestrassierra.jlcamunas.trasslado.actividades;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.mapbox.core.constants.Constants.PRECISION_6;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.core.constants.Constants;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.LayerUtils;
import com.mapbox.maps.extension.style.layers.generated.LineLayer;
import com.mapbox.maps.extension.style.sources.SourceUtils;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.GesturesPlugin;
import com.mapbox.maps.plugin.gestures.GesturesUtils;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;

import android.os.Looper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import android.Manifest;

import iestrassierra.jlcamunas.trasslado.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Style.OnStyleLoaded, LocationEngineCallback<LocationEngineResult>, Callback<DirectionsResponse> {
    //Configuración del mapa MapBox
    MapView mapView;
    MapBoxMap mapboxMap;
    Point punto;
    PointAnnotationManager pointAnnotationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Configuración del mapa MapBox

        mapView = findViewById(R.id.mapView);
        mapboxMap = mapView.getMapboxMap();
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
        punto = Point.fromLngLat(location.getLongitude(), location.getLatitude());
        pointAnnotationManager = PointAnnotationManagerKt.
                createPointAnnotationManager(annotationPlugin, annotationConfig);
        //Comprobar si se han otorgado los permisos de localización
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Si no se han otorgado los permisos, solicitarlos
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
        } else { //Si se han otorgado los permisos
            fabGeolocation.setOnClickListener(v -> actualizarUbicacion());
            //Obtener la ubicación actual
            actualizarUbicacion();
        }


    }

    @Override
    public void onStyleLoaded(@NonNull Style style) {
        // Crear un marcador personalizado
        Bitmap marcadorRojo = BitmapFactory.decodeResource(getResources(), R.drawable.red_marker);
        style.addImage("marcadorRojo", marcadorRojo);
    }

    //Método para establecer la posición de la cámara
    private void setCameraPosition(Point point, double zoom) {
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .center(point)
                .pitch(0.0)
                .zoom(zoom)
                .build();
        mapboxMap.setCamera(cameraPosition);
    }
//Nota 1: El parámetro pitch es la inclinación de la cámara respecto de la perpendicular al mapa, por defecto 0.0 creará una vista bidimensional.

    //Nota 2: El parámetro bearing (no utilizado) es la rotación visual del mapa (rumbo), medida en grados en sentido antihorario desde el norte.
//Un rumbo de 0° orienta el mapa de modo que el Norte esté arriba, un rumbo de 90° orienta el mapa de modo que el Este está arriba.
    private void addMarker(Point point, String title) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point).withIconImage("marcadorRojo").withTextField(title)
                .withTextOffset(Arrays.asList(0.0, -2.0));
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Si el código de solicitud es el de permisos de localización
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_LOCATION) {
            // Comprobar si se han otorgado los permisos de localización
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Los permisos fueron otorgados, geolocalizar
                geolocalizar();
            } else {
                // Los permisos no fueron otorgados, muestra un mensaje de error.
                Toast.makeText(this, "Permiso denegado para acceder a la localización", Toast.LENGTH_SHORT).show();
            }
        }
    }
}