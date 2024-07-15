package com.example.lienzo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphicView extends View {
    public Paint p;
    private static final int RADIO = 30 ;
    private int centroX;
    private int centroY;
    private int velocidadX = 55 ; 
    private int velocidadY = 55 ;
    private List<Figura> listaFiguras;
    private List<Path> listaPath;

    public GraphicView(Context context, AttributeSet attr) {
        super(context, attr);
        p = new Paint();
        p.setColor(Color.RED);
        listaFiguras = new ArrayList<>();
        listaPath = new ArrayList<>();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        centroX = w / 2;
        centroY = h / 2;
        rellenaLista();
    }

    protected void onDraw(Canvas c) {
    	// Anchura
//        int w = getWidth();
//        // Altura
//        int h = getHeight();
//
//        centroX += velocidadX ;
//        centroY += velocidadY ;
//
//        // Límites de pantalla
//        int limiteDerecha = w - RADIO ;
//        int limiteInferior = h - RADIO ;
//
//        // Comprobar si invertir si llegamos al límite
//        if (centroX >= limiteDerecha) {
//            centroX = limiteDerecha ;
//            velocidadX *= -1 ;
//        }
//        if (centroX <= RADIO) {
//        	centroX = RADIO ;
//            velocidadX *= -1 ;
//        }
//        if (centroY >= limiteInferior) {
//            centroY = limiteInferior ;
//            velocidadY *= -1 ;
//        }
//        if (centroY <= RADIO) {
//        	centroY = RADIO ;
//            velocidadY *= -1 ;
//        }
//
//        // Dibujar el círculo
//        c.drawCircle(centroX, centroY, RADIO, p);
//        postInvalidateDelayed(100);
        for (Figura figura : listaFiguras) {
            figura.dibujar(c, p);
            postInvalidateDelayed(100);
        }

        if (listaFiguras.size() > 0 ) {
            for (Figura figura : listaFiguras) {
                new Thread(() -> {
                    while (true) {
                        figura.actualizarPosicion(getWidth(), getHeight());
                        postInvalidate();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            //isThreadRunning = true;
        }

    }
    public void rellenaLista(){
        Path circulo1=new Path();
        listaPath.add(circulo1);
        Path circulo2=new Path();
        listaPath.add(circulo2);
        Path circulo3=new Path();
        listaPath.add(circulo3);
        Path cuadrado1=new Path();
        listaPath.add(cuadrado1);
        Path cuadrado2=new Path();
        listaPath.add(cuadrado2);
        Path cuadrado3=new Path();
        listaPath.add(cuadrado3);
        Path triangulo1=new Path();
        listaPath.add(triangulo1);
        Path triangulo2=new Path();
        listaPath.add(triangulo2);
        Path triangulo3=new Path();
        listaPath.add(triangulo3);
        Path estrella1=new Path();
        listaPath.add(estrella1);
        Path estrella2=new Path();
        listaPath.add(estrella2);
        Path estrella3=new Path();
        listaPath.add(estrella3);

        int w = getWidth();

        int h = getHeight();

        int limiteDerecha = w - RADIO ;
        int limiteInferior = h - RADIO ;
        int limiteIzquierda = 0 + RADIO;
        int limiteSuperior = 0 + RADIO;
        int radio=100;

        for(Path figura:listaPath){
            // Límites de pantalla

            Random random = new Random();
            centroX=random.nextInt(limiteDerecha - limiteIzquierda + 1) + limiteIzquierda;
            centroY=random.nextInt(limiteInferior - limiteSuperior + 1) + limiteSuperior;
            if(listaPath.indexOf(figura)<3){
                figura.addCircle(centroX,centroY,100,Path.Direction.CW);
                Figura temp=new Figura(figura,centroX,centroY,velocidadX,velocidadY);
                listaFiguras.add(temp);
            }else if(listaPath.indexOf(figura)<6){
                figura.addRect(centroX,centroY,centroX+100,centroY+100,Path.Direction.CW);
                Figura temp=new Figura(figura,centroX,centroY,velocidadX,velocidadY);
                listaFiguras.add(temp);
            }else if(listaPath.indexOf(figura)<9){
                figura=creaEstrella(centroX,centroY,100,5);
                Figura temp=new Figura(figura,centroX,centroY,velocidadX,velocidadY);
                listaFiguras.add(temp);
            }else{
                figura=creaPoligono(centroX,centroY,100,3);
                Figura temp=new Figura(figura,centroX,centroY,velocidadX,velocidadY);
                listaFiguras.add(temp);
            }

        }
        for (Figura figura2 : listaFiguras) {
            new Thread(() -> {
                while (true) {
                    figura2.actualizarPosicion(getWidth(), getHeight());
                    postInvalidate();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
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
