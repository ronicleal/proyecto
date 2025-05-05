package com.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestorEnemigo {
    private List<Enemigo> enemigos;
    

    public GestorEnemigo() {
        enemigos = new ArrayList<>();
        cargarEnemigos("/data/enemigos.txt");
    }

    private void cargarEnemigos(String ruta) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(ruta)))) {
            String linea;
            br.readLine();

            while ((linea = br.readLine()) != null) {

                String[] partes = linea.split(",");

                String tipo = partes[0];
                int x = Integer.parseInt(partes[1]);
                int y = Integer.parseInt(partes[2]);
                int vida = Integer.parseInt(partes[3]);
                int fuerza = Integer.parseInt(partes[4]);
                int defensa = Integer.parseInt(partes[5]);
                int velocidad = Integer.parseInt(partes[6]);
                int percepcion = Integer.parseInt(partes[7]);

                Enemigo enemigo = new Enemigo(tipo, x, y, vida, fuerza, defensa, velocidad, percepcion);
                enemigos.add(enemigo);
            }

        } catch (Exception e) {

        }

    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    public void moverEnemigos(Protagonista protagonista, LectorEscenario escenario) {

        Random rm = new Random();
        
        int filaProta = protagonista.getFila();
        int columProta = protagonista.getColumna();

        for (Enemigo enemigo : enemigos) {
           
            int filaEnemigo = enemigo.getFila();
            int columEnemigo = enemigo.getColumna();

            boolean cercaDelProta = (filaProta >= filaEnemigo - enemigo.getPercepcion()
                    && filaProta <= filaEnemigo + enemigo.getPercepcion()) &&
                    (columProta >= columEnemigo - enemigo.getPercepcion()
                            && columProta <= columEnemigo + enemigo.getPercepcion());

            int nvaFila = filaEnemigo;
            int nvaColum = columEnemigo;

            if (cercaDelProta) {
                // Mover hacia el protagonista
                if (filaProta < filaEnemigo) {
                    nvaFila--; // Moverse hacia arriba
                } else if (filaProta > filaEnemigo) {
                    nvaFila++; // Moverse hacia abajo
                }

                if (columProta < columEnemigo) {
                    nvaColum--; // Moverse a la izquierda
                } else if (columProta > columEnemigo) {
                    nvaColum++; // Moverse a la derecha
                }

                // Verificar que el enemigo no se mueva a la posición del protagonista
                if (nvaFila == filaProta && nvaColum == columProta) {
                    //System.out.println("¡El enemigo ha alcanzado al protagonista!");
                    continue; // esto es ejemplo para q el enemigo no se mueva a la posición del protagonista
                   //Luego Aquí vamos a agregar lógica para manejar el combate por ahora solo lo mostrara y pasara por encima del enemigo
                }

            } else {

                int[][] direcciones = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
                int index = rm.nextInt(direcciones.length); // Seleccionar un índice aleatorio

                int f = filaEnemigo + direcciones[index][0];
                int c = columEnemigo + direcciones[index][1];

                if (f >= 0 && f < escenario.getAlto() && c >= 0 && c < escenario.getAncho()) {
                    if (escenario.getCelda(f, c).getTipo() != TipoCelda.PARED) {
                        if (f == filaProta && c == columProta) {// Verificar que el enemigo no se mueva a la posición
                                                                // del protagonista
                            continue;// No mover al enemigo a la posición del protagonista
                        }
                        nvaFila = f;
                        nvaColum = c;
                    }
                }
            }

            enemigo.setPosicion(nvaFila, nvaColum);

        }

    }


}
