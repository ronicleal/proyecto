package com.controller;

import com.interfaces.Observer;
import com.model.Celda;
import com.model.LectorEscenario;
import com.model.Protagonista;
import com.model.TipoCelda;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Vista2Controller implements Observer {
    // Recibir el objeto Protagonista
    private Protagonista protagonista; // Personaje principal del juego

    @FXML
    private VBox root; // Contenedor principal de la vista

    private GridPane mainGridPane; // La cuadricula donde se dibuja el mapa

    private static final int TAMANO_CELDA = 30; // Tamaño de cada celda del mapa

    private ImageView protagonistaImageView; // Imagen del protagonista

    /**
     * Este método se llama desde la vista anterior para recibir el protagonista
     * y luego llama a reproduce() para mostrarlo en el mapa.
     * @param protagonista
     */
    public void setProtagonista(Protagonista protagonista) {
        this.protagonista = protagonista;
        reproduce();
    }


    @FXML
    public void initialize() {

        // Cargar la imagen del protagonista

        Image protagonistaImage = new Image(getClass().getResourceAsStream("/imagen/protagonista.gif")); // Ruta de la
                                                                                                         // imagen
        if (protagonistaImage.isError()) {
            System.err.println("Error al cargar la imagen del protagonista.");
        }

        // Cuando se carga la vista, se carga la imagen del protagonista y se ajusta a
        // un tamaño adecuado.

        protagonistaImageView = new ImageView(protagonistaImage);
        protagonistaImageView.setFitWidth(TAMANO_CELDA);
        protagonistaImageView.setFitHeight(TAMANO_CELDA);

    }

    private void manejarMovimiento(KeyEvent event) {

        //Detecta la tecla pulsada y calcula la nueva posición del protagonista.
        //Llama a esPosicionValida para ver si puede moverse ahí.
        //Si es válido, actualiza la posición y redibuja el mapa.

        int nuevaFila = protagonista.getFila();
        int nuevaColumna = protagonista.getColumna();

        // Cambia la fila o columna según la tecla pulsada
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
            nuevaFila--; // Mover hacia arriba

        } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
            nuevaFila++; // Mover hacia abajo

        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
            nuevaColumna--; // Mover hacia la izquierda

        } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
            nuevaColumna++; // Mover hacia la derecha
        }


        // Comprueba si la nueva posición es válida (no es pared ni está fuera del mapa)
        if (esPosicionValida(nuevaFila, nuevaColumna)) {
            protagonista.setPosicion(nuevaFila, nuevaColumna);
            reproduce(); // Redibuja el mapa con el protagonista en la nueva posición
        }
    }



    

    private boolean esPosicionValida(int fila, int columna) {
        // Evita que el protagonista salga del mapa o entre en una pared (#).
        // Muestra en consola el tipo de celda a la que se quiere mover (útil para depurar).
       

        try {
            LectorEscenario lector = new LectorEscenario("/dataUrl/mapas.txt");
            // Comprueba que la posición esté dentro del mapa
            if (fila < 0 || fila >= lector.getAlto() || columna < 0 || columna >= lector.getAncho()) {
                return false;
            }

            // Comprobar si la celda es una pared
            Celda celda = lector.getCelda(fila, columna);
            System.out.println("Celda actual: " + celda.getTipo());
            return celda.getTipo() != TipoCelda.PARED;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    private void reproduce() {
        
        //Reconstruye el mapa cada vez que el protagonista se mueve.
        //Dibuja la imagen del protagonista en su posición actual.
        //El resto de celdas se dibujan como suelo (.) o pared (#).
        //Después de redibujar, el GridPane vuelve a escuchar el teclado y se le da el foco para que puedas seguir moviendo al protagonista.

        if (protagonista != null) {
            System.out.println("Nombre del protagonista " + protagonista.getNombre());
            System.out.println("Puntos de vida: " + protagonista.getPuntosVida());
        }

        try {
            
            LectorEscenario lectorEscenario = new LectorEscenario("/dataUrl/mapas.txt");

            mainGridPane = new GridPane();
            mainGridPane.setPadding(new Insets(10));

            int filas = lectorEscenario.getAlto();
            int columnas = lectorEscenario.getAncho();
            double porcentaje = 100.0 / columnas;


            // Configura las columnas del GridPane
            for (int i = 0; i < columnas; i++) {
                ColumnConstraints col = new ColumnConstraints();
                col.setPercentWidth(porcentaje);
                col.setHgrow(Priority.ALWAYS);
                col.setHalignment(HPos.CENTER);
                mainGridPane.getColumnConstraints().add(col);
            }

            // Dibuja cada celda del mapa
            for (int f = 0; f < filas; f++) {
                for (int c = 0; c < columnas; c++) {
                    Celda celda = lectorEscenario.getCelda(f, c);

                    // Si la celda es la del protagonista, muestra la imagen
                    if (protagonista != null && f == protagonista.getFila() && c == protagonista.getColumna()) {

                        mainGridPane.add(protagonistaImageView, c, f); // Agregar el ImageView del protagonista

                        // Si no está el protagonista, simplemente se muetra la celda que se crea aqui
                        // en el switch
                    } else {
                        Label label = new Label();
                        switch (celda.getTipo()) {
                            case SUELO:
                                label.setText(".");
                                label.setTextFill(Color.RED);
                                break;
                            case PARED:
                                label.setText("#");
                                label.setTextFill(Color.DARKGRAY);
                                break;
                            // default:
                            // break;
                        }

                        label.setFont(Font.font("Consolas", 18));
                        mainGridPane.add(label, c, f);// He cambiado la posicion de f,c a c,f para que el mapa.txt
                                                      // aparezca tal cual segun el

                    }

                }
            }

            // Limpia la vista y añade el nuevo mapa
            root.getChildren().clear();
            root.getChildren().add(mainGridPane);

            // Aquí es donde se asegura que el GridPane escuche el teclado
            mainGridPane.setOnKeyPressed(event -> manejarMovimiento(event));
            mainGridPane.requestFocus(); // Asegurarse de que el VBox tenga el foco

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onChange() {
        reproduce();
    }

}
