package com.model;

public class Enemigo extends Personaje{
    private int velocidad;
    private int percepcion;

    public Enemigo(String tipo, int x, int y, int vida, int fuerza, int defensa, int velocidad, int percepcion) {
        super(tipo, defensa, fuerza, 0, vida);
        this.velocidad = velocidad;
        this.percepcion = percepcion;
        this.fila = y;
        this.columna = x;


    }
    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }


    @Override
    public String toString() {
        return "Enemigo [nombre=" + nombre + ", vida=" + puntosVida + ", pos=(" + fila + "," + columna + "), fuerza=" + fuerza +
                ", defensa=" + defensa + ", velocidad=" + velocidad + ", percepcion=" + percepcion + "]";
    }

}
