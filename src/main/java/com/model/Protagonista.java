package com.model;

public class Protagonista extends Personaje {


    public Protagonista(String nombre, int defensa, int fuerza, int danio, int puntosVida, int fila, int columna) {
        super(nombre, defensa, fuerza, danio, puntosVida);
        this.fila = fila;
        this.columna = columna;
    }

    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }



    

    

}
