package josea_ferre.actividad4_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private int centrox, centroy;
    private Lienzo lienzo;
    private Paint pincel=new Paint();
    private ConstraintLayout fondo;
    private SharedPreferences sharedPreferences;
    private List<Path> figuras = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fondo = (ConstraintLayout) findViewById(R.id.lienzo);
        lienzo = new Lienzo(this);
        lienzo.setOnTouchListener(this);
        pincel.setARGB(255, 255, 255, 255);//Color blanco para que no se vea el circulo al principio
        pincel.setStrokeWidth(4);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(getString(R.string.key_rellenar),false)){
            pincel.setStyle(Paint.Style.FILL);

        }else {
            pincel.setStyle(Paint.Style.STROKE);
        }

        fondo.addView(lienzo);

    }
    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.item_pref){
            startActivity(new Intent(this, PreferenciasFormas.class));

        }else if(id==R.id.item_salir){
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            centrox=(int)motionEvent.getX();
            centroy=(int)motionEvent.getY();
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

            //Elegimos color de las formas segun el swich
            if(!sharedPreferences.getBoolean(getString(R.string.key_color),false)){
                pincel.setARGB(255, (int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));

            }else {
                pincel.setARGB(255, Integer.parseInt(sharedPreferences.getString(getString(R.string.key_rojo),"100")), Integer.parseInt(sharedPreferences.getString(getString(R.string.key_verde),"100")), Integer.parseInt(sharedPreferences.getString(getString(R.string.key_azul),"100")));
            }



            lienzo.invalidate();
            return true;
        }
        return false;
    }

    private class Lienzo extends View{
        public Lienzo(MainActivity context){
            super(context);
        }
        protected void onDraw(Canvas canvas) {
            canvas.drawRGB(255, 255, 255);
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
            if(sharedPreferences.getBoolean(getString(R.string.key_permanencia),false)) {
                for (Path figura : figuras) {
                    canvas.drawPath(figura, pincel);
                }
            }

            //en función de las preferencias, se elige el tipo de figura
            if(sharedPreferences.getString(getString(R.string.key_forma),"2").equals("2")){

                //En función del tamaño elegido damos un valor a radio
                if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("1")){
                    canvas.drawCircle(centrox, centroy, 50, pincel);
                    Path nuevaFigura = new Path();
                    nuevaFigura.addCircle(centrox, centroy, 50, Path.Direction.CW); // Ajusta la dirección según tus necesidades
                    figuras.add(nuevaFigura);

                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("2")){
                    canvas.drawCircle(centrox, centroy, 100, pincel);
                    Path nuevaFigura = new Path();
                    nuevaFigura.addCircle(centrox, centroy, 100, Path.Direction.CW); // Ajusta la dirección según tus necesidades
                    figuras.add(nuevaFigura);
                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("3")){
                    canvas.drawCircle(centrox, centroy, 150, pincel);
                    Path nuevaFigura = new Path();
                    nuevaFigura.addCircle(centrox, centroy, 150, Path.Direction.CW); // Ajusta la dirección según tus necesidades
                    figuras.add(nuevaFigura);
                }
            } else if (sharedPreferences.getString(getString(R.string.key_forma),"2").equals("1")) {
                if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("1")){
                    canvas.drawRect(centrox-50, centroy-50, centrox+50, centroy+50, pincel);
                    Path nuevaFigura = new Path();
                    nuevaFigura.addRect(centrox-50, centroy-50, centrox+50, centroy+50, Path.Direction.CW); // Ajusta la dirección según tus necesidades
                    figuras.add(nuevaFigura);
                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("2")){
                    canvas.drawRect(centrox-100, centroy-100, centrox+100, centroy+100, pincel);
                    Path nuevaFigura = new Path();
                    nuevaFigura.addRect(centrox-100, centroy-100, centrox+100, centroy+100, Path.Direction.CW); // Ajusta la dirección según tus necesidades
                    figuras.add(nuevaFigura);

                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("3")){
                    canvas.drawRect(centrox-150, centroy-150, centrox+150, centroy+150, pincel);
                    Path nuevaFigura = new Path();
                    nuevaFigura.addRect(centrox-150, centroy-150, centrox+150, centroy+150, Path.Direction.CW); // Ajusta la dirección según tus necesidades
                    figuras.add(nuevaFigura);
                }

            } else if (sharedPreferences.getString(getString(R.string.key_forma),"2").equals("3")) {
                if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("1")){
                    canvas.drawPath(creaEstrella(centrox, centroy, 50,5), pincel);
                    //agrego la figura a la lista
                    figuras.add(creaEstrella(centrox, centroy, 50,5));
                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("2")){
                    canvas.drawPath(creaEstrella(centrox, centroy, 100,5), pincel);
                    figuras.add(creaEstrella(centrox, centroy, 100,5));
                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("3")){
                    canvas.drawPath(creaEstrella(centrox, centroy, 150,5), pincel);
                    figuras.add(creaEstrella(centrox, centroy, 150,5));
                }
            } else if (sharedPreferences.getString(getString(R.string.key_forma),"2").equals("4")) {
                if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("1")){
                    canvas.drawPath(creaPoligono(centrox, centroy, 50,5), pincel);
                    figuras.add(creaPoligono(centrox, centroy, 50,5));
                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("2")){
                    canvas.drawPath(creaPoligono(centrox, centroy, 100,5), pincel);
                    figuras.add(creaPoligono(centrox, centroy, 100,5));
                }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("3")){
                    canvas.drawPath(creaPoligono(centrox, centroy, 150,5), pincel);
                    figuras.add(creaPoligono(centrox, centroy, 150,5));
                }
            }else if (sharedPreferences.getString(getString(R.string.key_forma),"2").equals("5")) {
                //En función de las preferencias de formas especiales, se elige el tipo de figura
                if(sharedPreferences.getBoolean(getString(R.string.key_forma_especial),false)){
                    int numLladosPuntas = Integer.parseInt(sharedPreferences.getString(getString(R.string.key_num_lados),"3"));
                    if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("1")){
                        canvas.drawPath(creaPoligono(centrox, centroy, 50,numLladosPuntas), pincel);
                        figuras.add(creaPoligono(centrox, centroy, 50,numLladosPuntas));
                    }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("2")){
                        canvas.drawPath(creaPoligono(centrox, centroy, 100,numLladosPuntas), pincel);
                        figuras.add(creaPoligono(centrox, centroy, 100,numLladosPuntas));
                    }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("3")){
                        canvas.drawPath(creaPoligono(centrox, centroy, 150,numLladosPuntas), pincel);
                        figuras.add(creaPoligono(centrox, centroy, 150,numLladosPuntas));
                    }
                }else{
                    int numPuntas = Integer.parseInt(sharedPreferences.getString(getString(R.string.key_num_lados),"3"));
                    if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("1")){
                        canvas.drawPath(creaEstrella(centrox, centroy, 50,numPuntas), pincel);
                        figuras.add(creaEstrella(centrox, centroy, 50,numPuntas));
                    }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("2")){
                        canvas.drawPath(creaEstrella(centrox, centroy, 100,numPuntas), pincel);
                        figuras.add(creaEstrella(centrox, centroy, 100,numPuntas));
                    }else if(sharedPreferences.getString(getString(R.string.key_tamanyo),"1").equals("3")){
                        canvas.drawPath(creaEstrella(centrox, centroy, 150,numPuntas), pincel);
                        figuras.add(creaEstrella(centrox, centroy, 150,numPuntas));
                    }
                }

            }

        }
    }
    public Path creaEstrella(int x, int y, int radio, int numPuntas) {
        Point centro = new Point(x, y);
        Point[] starP = new Point[numPuntas * 2];

        int anguloInicial = (int) (Math.random() * 360); // Ángulo aleatorio para el primer punto

        for (int i = 0, angulo = anguloInicial; i < starP.length; i++, angulo += 360 / starP.length) {
            if (i % 2 == 0)
                starP[i] = polar2rect(radio, angulo);
            else
                starP[i] = polar2rect(radio / 2, angulo);
        }

        Path star = new Path();
        star.moveTo(starP[0].x + centro.x, starP[0].y + centro.y);
        for (int i = 1; i < starP.length; i++)
            star.lineTo(starP[i].x + centro.x, starP[i].y + centro.y);
        star.lineTo(starP[0].x + centro.x, starP[0].y + centro.y);

        return star;
    }

    public Path creaPoligono(int x, int y, int radio, int numLados) {
        Point centro = new Point(x, y);
        Point[] polyP = new Point[numLados];

        int anguloInicial = (int) (Math.random() * 360); // Ángulo aleatorio para el primer punto

        for (int i = 0, angulo = anguloInicial; i < polyP.length; i++, angulo += 360 / numLados) {
            polyP[i] = polar2rect(radio, angulo);
        }

        Path poly = new Path();
        poly.moveTo(polyP[0].x + centro.x, polyP[0].y + centro.y);
        for (int i = 1; i < polyP.length; i++)
            poly.lineTo(polyP[i].x + centro.x, polyP[i].y + centro.y);
        poly.lineTo(polyP[0].x + centro.x, polyP[0].y + centro.y);

        return poly;
    }

    public Point polar2rect (double radio, int grados){
        double rad = grados * Math.PI / 180; //para pasar el ángulo de grados a radianes
        return new Point(
                (int) Math.round(radio * Math.cos(rad)), //coordenada x
                (int) Math.round(radio * Math.sin(rad))  //coordenada y
        );
    }

}