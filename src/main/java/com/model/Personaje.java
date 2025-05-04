package com.model;

abstract class Personaje {
    
    protected String nombre;
    protected int puntosVida;
    protected int defensa;
    protected int fuerza;
    protected int danio;
    protected int fila ;
    protected int columna;

  

    Personaje(String nombre, int defensa, int fuerza, int danio, int puntosVida){
        this.nombre = nombre;
        this.defensa = defensa;
        this.fuerza = fuerza;
        this.danio = danio;
        this.puntosVida = puntosVida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFila() {
        return this.fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return this.columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int danio) {
        this.danio = danio;
    }

    public void CalcularPuntosVida(int danio) {
        int danioReal = danio - defensa;
        if (danioReal < 0) {
            danioReal = 0; // Si el daño es menor que 0, se convierte en 0
        }
        puntosVida -= danioReal; // Resta el daño real de los puntos de vida
        if (puntosVida < 0) {
            puntosVida = 0; // Asegura que los puntos de vida no sean negativos
        }
    }

    public boolean estaVivo(){ //por ejemplo para cuando un enemigo muere... si su vida se queda a cero... "vivo" retorna a falso
        boolean vivo = true;
        if(puntosVida > 0){
            return vivo;
        }else{
            return false;
        }
    }


    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", puntosVida=" + puntosVida + ", defensa=" + defensa + ", fuerza="
                + fuerza + ", danio=" + danio + "]";
    }

    


        
    }
