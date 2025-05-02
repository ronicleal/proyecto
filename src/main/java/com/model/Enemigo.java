package com.model;

public class Enemigo extends Personaje{
    int percepcion;

    Enemigo(String nombre, int defensa, int fuerza, int danio, int puntosVida, int percepcion) {

        super(nombre, defensa, fuerza, danio, puntosVida);

        this.percepcion = percepcion;

    }

    public int getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

}
