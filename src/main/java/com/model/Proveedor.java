package com.model;

/**
 * El Proveedor podria ser un Singleton q establece una única instancia de Protagonista,gestorEnemigo
asegurando acceso global en la aplicación,
facilitando la gestión de recursos sin duplicación,
promoviendo consistencia y simplificando la interacción,
ideal para casos donde un objeto único es la solución.
 */

public class Proveedor {

    private static Proveedor instance;
    private Protagonista protagonista;
    private GestorEnemigo gestorEnemigo;

    private Proveedor(){
        gestorEnemigo = new GestorEnemigo();
    
    }

    public static Proveedor getInstance(){
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    public void setProtagonista(Protagonista protagonista){
        this.protagonista = protagonista;
    }

    public Protagonista getProtagonista(){
        return protagonista;
    }

    public GestorEnemigo getGestorEnemigo(){
        return gestorEnemigo;
    }

   


}
