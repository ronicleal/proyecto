package com.model;

public class Celda {
    
    public static final String TIPO = null;
    private TipoCelda tipo;
    private boolean ocupado;

    public Celda(TipoCelda tipo){
        this.tipo = tipo;
        this.ocupado = false;
    }


    public TipoCelda getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }

    public boolean isOcupado() {
        return this.ocupado;
    }

    public boolean getOcupado() {
        return this.ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public boolean esTransitable(){
        return tipo == TipoCelda.SUELO && !ocupado;
    }
    
}
