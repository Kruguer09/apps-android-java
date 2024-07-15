package com.example.lienzo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Figura {
    public Path path;
    public int x, y;
    public int velocidadX, velocidadY;

    public Figura(Path path, int x, int y, int velocidadX, int velocidadY) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
    }

    public void actualizarPosicion(int limiteX, int limiteY) {
        x += velocidadX;
        y += velocidadY;

        if (x < 0 || x > limiteX) {
            velocidadX *= -1;
        }
        if (y < 0 || y > limiteY) {
            velocidadY *= -1;
        }
    }

    public void dibujar(Canvas c, Paint p) {
        c.drawPath(path, p);
    }
}
