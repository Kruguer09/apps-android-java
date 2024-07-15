package iestrassierra.jlcamunas.trasslado.actividades;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.mapbox.core.constants.Constants.PRECISION_6;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import iestrassierra.jlcamunas.trasslado.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Style.OnStyleLoaded , LocationEngineCallback<LocationEngineResult>, Callback<DirectionsResponse> {
    //Elementos del mapa mapbox
    private MapView mapView;
    private MapboxMap mapboxMap;
    private Bitmap marcadorRojo;
    // Elementos de la ubicación
    private Point origen;
    private Point destino;
    private LocationEngine locationEngine;
    private PointAnnotation marcaOrigen;
    private PointAnnotationManager pointAnnotationManager;
    private LocationEngineRequest solicitudUbicacion;
    // gestion de permisos
    private static final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    //Botones de la interfaz
    FloatingActionButton fabGeolocation;
    FloatingActionButton fabDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //Configuración del botón de geolocalización
        fabGeolocation = findViewById(R.id.fab_localizacion_mapbox);
        fabDirections = findViewById(R.id.fab_directions_mapbox);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            //Comprobar si se han otorgado los permisos de localización
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this,
                    ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Si no se han otorgado los permisos, solicitarlos
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
            }else { //Si se han otorgado los permisos
                fabGeolocation.setOnClickListener(view -> actualizarUbicacion());
                //Obtener la ubicación actual
                actualizarUbicacion();
            }
            return insets;
        });
        //Inicializo el mapa
        mapView = findViewById(R.id.mapView);
        mapboxMap = mapView.getMapboxMap();
        // Cargo el estilo del mapa, callejero
        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, this);
        // Marcador para el mapa, usamos el que nos descargamos de la api de mapbox
        marcadorRojo = BitmapFactory.decodeResource(getResources(), R.drawable.red_marker);
        // Establecer el cliente de localización
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        // Solicitud de nueva localización
        solicitudUbicacion = new LocationEngineRequest.Builder(500)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(3000)
                .setFastestInterval(500)
                .build();
        // Accion del boton de direcciones
        fabDirections.setOnClickListener(this::calcularRuta);
    }
    private void calcularRuta(View view) {
        if (origen != null) {
            RouteOptions routeOptions =
                    RouteOptions.builder().baseUrl(Constants.BASE_API_URL)
                            .user(Constants.MAPBOX_USER)
                            .profile(DirectionsCriteria.PROFILE_DRIVING)
                            .steps(true)
                            .coordinatesList(Arrays.asList(origen, destino)).build();
            MapboxDirections directions = MapboxDirections.builder()
                    .routeOptions(routeOptions)
                    .accessToken(getString(R.string.mapbox_access_token)).build();
            directions.enqueueCall(this);
        } else {
            Toast.makeText(this, "Selecciona un punto de origen", Toast.LENGTH_SHORT).show();
        }
        // Define el punto de destino como el Ies Trassierra
        destino = Point.fromLngLat(-4.7791, 37.8846);
        if (destino != null) {
            addMarker(destino, "Destino");
        }
    }
    private void actualizarUbicacion() {
        // Verificar los permisos de ubicación
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationEngine.getLastLocation(new LocationEngineCallback<LocationEngineResult>() {
                @Override
                public void onSuccess(LocationEngineResult result) {
                    Location lastLocation = result.getLastLocation();
                    if (lastLocation != null) {
                        Point newOrigen = Point.fromLngLat(lastLocation.getLongitude(), lastLocation.getLatitude());
                        // Si la ubicación de origen ha cambiado, borra todas las marcas y la ruta existente
                        if (origen != null && !origen.equals(newOrigen)) {
                            borrarMarcas();
                        }
                        origen = newOrigen;
                        setCameraPosition(origen, 10.0);
                        // Agrega un nuevo marcador de origen y guarda la referencia
                        PointAnnotationOptions options = new PointAnnotationOptions()
                                .withPoint(origen)
                                .withIconImage("marcadorRojo")
                                .withTextField("Origen")
                                .withTextOffset(Arrays.asList(0.0, -1.5));
                        marcaOrigen = pointAnnotationManager.create(options);
                    } else {
                        Log.e("MapboxActivity", "No se puede obtener la última ubicación ");
                    }
                }
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("MapboxActivity", "Error al obtener la última ubicación: " + exception.getMessage());
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);
        }
    }
    public void borrarMarcas(){
        pointAnnotationManager.deleteAll();
        mapboxMap.getStyle(style -> {
            style.removeStyleLayer("route-layer-id");
            style.removeStyleSource("route-source-id");
        });
    }
    @Override
    public void onStyleLoaded(@NonNull Style style) {
        // Marcador para el mapa, usamos el que nos descargamos de la api de mapbox
        marcadorRojo = BitmapFactory.decodeResource(getResources(), R.drawable.red_marker);
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
    //Marcadores: objetos de la clase Marker que añadiremos mediante un método:
    private void addMarker(Point point, String title) {
        if (point != null) {
            PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                    .withPoint(point)
                    .withIconImage("marcadorRojo")  // Asegúrate de usar el mismo identificador que en onStyleLoaded
                    .withTextField(title)
                    .withTextOffset(Arrays.asList(0.0, -2.0));
            pointAnnotationManager.create(pointAnnotationOptions);
        } else {
            Log.e("MainActivity", "Error al añadir un marcador, nulo");
        }
    }
    @Override
    public void onSuccess(LocationEngineResult result) {
        //Obtener la ubicación actual
        Location currentLocation = result.getLastLocation();
        if (currentLocation != null) {
            origen = Point.fromLngLat(currentLocation.getLongitude(), currentLocation.getLatitude());
            setCameraPosition(origen, 10.0);
            addMarker(origen, "Origen");
        }else{
            Log.e("MapboxActivity", "No se puede obtener la ubicación actual");
        }
        locationEngine.removeLocationUpdates(this); //Dejar de recibir actualizaciones de ubicación
    }
    @Override
    public void onFailure(@NonNull Exception exception) {
        Log.e("MapboxActivity", "Error en la geolocalización" + exception.getMessage());
        locationEngine.removeLocationUpdates(this); //Dejar de recibir actualizaciones de ubicación
    }
    @Override
    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
        DirectionsRoute mainRoute;
        if (response.body() != null) {
            mainRoute = response.body().routes().get(0);

            mapboxMap.getStyle(style -> {
                LineString routeLine = LineString.fromPolyline(Objects.requireNonNull(mainRoute.geometry()), PRECISION_6);
                GeoJsonSource routeSource = new GeoJsonSource.Builder("route-source-id")
                        .geometry(routeLine)
                        .build();
                LineLayer routeLayer = new LineLayer("route-layer-id", "route-source-id")
                        .lineWidth(3.f)
                        .lineColor(getResources().getColor(R.color.colorPolilinea, getTheme()))
                        .lineOpacity(1f);
                SourceUtils.addSource(style, routeSource);
                LayerUtils.addLayerBelow(style, routeLayer, "mapbox-android-pointAnnotation-layer-1");
                //Añadir un marcador en el destino
                addMarker(destino, "Destino");
                //Gestionar el zoom de la cámara adecuado para mostrar la ruta
                List<Point> routePoints = Arrays.asList(origen, destino);
                CameraOptions cameraOptions = mapboxMap.cameraForCoordinates(
                        routePoints,
                        new EdgeInsets(200.0, 200.0, 200.0, 200.0),
                        0.0,
                        null
                );
                mapboxMap.setCamera(cameraOptions);
                PointAnnotationOptions destinationMarker = new PointAnnotationOptions()
                        .withPoint(destino)
                        .withIconImage("destination-icon-id");
                pointAnnotationManager.create(destinationMarker);
                // Crear info con la distancia y el tiempo de la ruta
                Snackbar snackbarInfo = Snackbar.make(findViewById(R.id.mapView),
                        "Distancia: " + String.format(Locale.getDefault(),"%.2f", (mainRoute.distance() / 1000)) + " km\n" +
                                "Tiempo estimado: " + String.format(Locale.getDefault(),"%.0f", (mainRoute.duration() / 60)) +
                                " minutos", Snackbar.LENGTH_INDEFINITE);
                // Añadir un botón de acción para cerrar el Snackbar
                snackbarInfo.setAction("OK", v -> snackbarInfo.dismiss());
                snackbarInfo.show();

            });
            }else{
                Log.e("MapBoxActivity", "Error en la respuesta");
            }


    }

    @Override
    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
        Log.e("MapBoxActivity", "Fallo al solicitar una ruta", t);

    }
}