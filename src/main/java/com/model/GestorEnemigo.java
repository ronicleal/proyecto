package com.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

        System.out.println("Total enemigos cargados: " + enemigos.size());

    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    /**
     * Un HashSet es una estructura de datos que se utiliza para almacenar elementos
     * únicos y permite búsquedas rápidas. En este caso, lo usamos para rastrear
     * las posiciones ocupadas por los enemigos en el mapa.
     * 
     * 
     * @param protagonista
     * @param escenario
     */

    public void moverEnemigos(Protagonista protagonista, LectorEscenario escenario) {

        Random rm = new Random();

        int filaProta = protagonista.getFila();
        int columProta = protagonista.getColumna();

        /**
         * creamos un conjunto vacío para almacenar las posiciones ocupadas por los
         * enemigos.
         * Registrar las posiciones iniciales de los enemigos
         */
        Set<String> posicionesOcupadasEnemigo = new HashSet<>();

        /**
         * El bucle for recorre la lista de enemigos.
         * Para cada enemigo, se obtiene su posición (fila y columna) y se
         * convierte en una cadena en el formato "fila,columna".
         * Esa cadena se agrega al HashSet usando add(). Si la posición ya está en el
         * conjunto,
         * no se agrega de nuevo (evitando duplicados).
         */
        for (Enemigo enemigo : enemigos) {
            posicionesOcupadasEnemigo.add(enemigo.getFila() + "," + enemigo.getColumna());
        }

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
                    // System.out.println("¡El enemigo ha alcanzado al protagonista!");
                    continue; // esto es ejemplo para q el enemigo no se mueva a la posición del protagonista
                    // Luego Aquí vamos a agregar lógica para manejar el combate por ahora solo lo
                    // mostrara y pasara por encima del enemigo
                }

            } else {
                // Movimiento aleatorio
                int[][] direcciones = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
                int intentos = 0;

                // Intentar encontrar una posición válida
                while (intentos < direcciones.length) {
                    int index = rm.nextInt(direcciones.length); // Seleccionar un índice aleatorio
                    int f = filaEnemigo + direcciones[index][0];
                    int c = columEnemigo + direcciones[index][1];

                    // Verificar que las coordenadas sean válidas y no estén ocupadas
                    if (f >= 0 && f < escenario.getAlto() && c >= 0 && c < escenario.getAncho()
                            && escenario.getCelda(f, c).getTipo() != TipoCelda.PARED
                            && !posicionesOcupadasEnemigo.contains(f + "," + c)) {
                        nvaFila = f;
                        nvaColum = c;
                        break;
                    }
                    intentos++;
                }

            }

            // Actualizar la posición del enemigo si es válida
            if (!posicionesOcupadasEnemigo.contains(nvaFila + "," + nvaColum)) {
                posicionesOcupadasEnemigo.remove(filaEnemigo + "," + columEnemigo); // Eliminar la posición anterior
                posicionesOcupadasEnemigo.add(nvaFila + "," + nvaColum); // Registrar la nueva posición
                enemigo.setPosicion(nvaFila, nvaColum);
            }

        }

    }

}