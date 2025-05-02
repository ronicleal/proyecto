package com.model;

public class Protagonista extends Personaje {

    private int fila;
    private int columna;

    

    public Protagonista(String nombre, int defensa, int fuerza, int danio, int puntosVida, int fila, int columa){
        super(nombre, defensa, fuerza, danio, puntosVida);
        this.fila = fila;
        this.columna = columa;

        

    }



    public int getFila() {
        return fila;
    }



    public void setFila(int fila) {
        this.fila = fila;
    }



    public int getColumna() {
        return columna;
    }



    public void setColumna(int columna) {
        this.columna = columna;
    }


    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }




    

    

}
