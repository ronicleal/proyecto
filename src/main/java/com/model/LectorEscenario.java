package com.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ronic.App;

public class LectorEscenario {
    private Celda[][] mapa;

    public LectorEscenario(String rutaRelativa) throws IOException {
        leerDesdeArchivo(rutaRelativa);
    }

    private void leerDesdeArchivo(String path) throws IOException {
       
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(App.class.getResource("/dataUrl/mapas.txt").toURI()))))) {
            List<String> lineas = new ArrayList<>();
            String linea;

            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }

            lineas.removeIf(l -> l.trim().isEmpty());

            if (lineas.isEmpty()) {
                throw new IOException("El archivo del mapa está vacío o solo contiene líneas en blanco.");
            }

            int filas = lineas.size();
            int columnas = lineas.get(0).length();
            mapa = new Celda[filas][columnas];

            for (int y = 0; y < filas; y++) {
                String fila = lineas.get(y);

                if (fila.length() != columnas) {
                    throw new IOException("Las líneas del mapa no tienen el mismo largo. Línea " + y
                            + " tiene longitud " + fila.length());
                }
                for (int x = 0; x < columnas; x++) {
                    char simbolo = fila.charAt(x);

                    TipoCelda tipo;
                    switch (simbolo) {
                        case '.':
                            tipo = TipoCelda.SUELO;
                            break;
                        case '#':
                            tipo = TipoCelda.PARED;
                            break;
                        default:
                            throw new IllegalArgumentException("Símbolo no reconocido: " + simbolo);
                    }

                    mapa[y][x] = new Celda(tipo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error leyendo el mapa: " + e.getMessage(), e);
        }
    }

    public Celda getCelda(int fila, int columna) {
        return mapa[fila][columna];
    }

    public int getAlto() {
        return mapa.length;
    }

    public int getAncho() {
        return mapa[0].length;
    }


}
