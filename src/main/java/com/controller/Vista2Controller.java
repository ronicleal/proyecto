package com.controller;

import com.interfaces.Observer;
import com.model.Celda;
import com.model.Enemigo;
import com.model.GestorEnemigo;
import com.model.LectorEscenario;
import com.model.Protagonista;
import com.model.Proveedor;
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

    private Image protagonistaArriba;
    private Image protagonistaAbajo;
    private Image protagonistaIzquierda;
    private Image protagonistaDerecha;
    private Image imgEnemigo; // Imagen para los enemigos
    private Image imgSuelo;// Imagen para representar el suelo
    private Image imgPared;// Imagen para representar la pared

    @FXML
    private VBox root; // Contenedor principal de la vista

    private GridPane mainGridPane; // La cuadricula donde se dibuja el mapa

    private static final int ANCHO_CELDA = 50; // Tamaño de cada celda del mapa

    private static final int ALTO_CELDA = 50;

    private ImageView protagonistaImageView; // Imagen del protagonista

    /**
     * Este método se llama desde la vista anterior para recibir el protagonista
     * y luego llama a reproduce() para mostrarlo en el mapa.
     * 
     * @param protagonista
     */
    public void setProtagonista(Protagonista protagonista) {
        Proveedor.getInstance().setProtagonista(protagonista);
        reproduce();
    }

    @FXML
    public void initialize() {

        // carga las imagenes para el protagonista

        protagonistaArriba = new Image(getClass().getResourceAsStream("/imagen/protagonista_arriba.png"));
        protagonistaAbajo = new Image(getClass().getResourceAsStream("/imagen/protagonista_abajo.png"));
        protagonistaIzquierda = new Image(getClass().getResourceAsStream("/imagen/protagonista_izquierda.png"));
        protagonistaDerecha = new Image(getClass().getResourceAsStream("/imagen/protagonista_derecha.png"));

        imgEnemigo = new Image(getClass().getResourceAsStream("/imagen/zombie_derecha.png"));
        imgSuelo = new Image(getClass().getResourceAsStream("/imagen/suelo.jpg"));
        imgPared = new Image(getClass().getResourceAsStream("/imagen/pared.jpg"));

        // Verificar la carga de imágenes
        verificarCargaImagenes();

        // Cuando se carga la vista, se carga la imagen del protagonista y se ajusta a
        // un tamaño adecuado.
        // Por defecto, muestra mirando abajo (o la que prefieras)
        protagonistaImageView = new ImageView(protagonistaAbajo);
        protagonistaImageView.setFitWidth(ANCHO_CELDA);
        protagonistaImageView.setFitHeight(ALTO_CELDA);

    }

    // MENSAJE SI NO SE CARGA LAS IMAGENES
    private void verificarCargaImagenes() {
        if (protagonistaArriba.isError() || protagonistaAbajo.isError() ||
                protagonistaDerecha.isError() || protagonistaIzquierda.isError()) {
            System.err.println("Error al cargar la imagen del protagonista.");
        }
        if (imgSuelo.isError()) {
            System.err.println("Error al cargar la imagen del suelo.");
        }
        if (imgPared.isError()) {
            System.err.println("Error al cargar la imagen de la pared.");
        }
        if (imgEnemigo.isError()) {
            System.err.println("Error al cargar la imagen del enemigo.");
        }
    }

    private void manejarMovimiento(KeyEvent event) {

        Protagonista protagonista = Proveedor.getInstance().getProtagonista();

        // Detecta la tecla pulsada y calcula la nueva posición del protagonista.
        // Llama a esPosicionValida para ver si puede moverse ahí.
        // Si es válido, actualiza la posición y redibuja el mapa.

        int nuevaFila = protagonista.getFila();
        int nuevaColumna = protagonista.getColumna();

        // Cambia la fila o columna según la tecla pulsada
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
            nuevaFila--; // Mover hacia arriba
            protagonistaImageView.setImage(protagonistaArriba); // En esta linea asocio la imagen que quiero a la tecla

        } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
            nuevaFila++; // Mover hacia abajo
            protagonistaImageView.setImage(protagonistaAbajo); // En esta linea asocio la imagen que quiero a la tecla

        } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
            nuevaColumna--; // Mover hacia la izquierda
            protagonistaImageView.setImage(protagonistaIzquierda); // En esta linea asocio la imagen que quiero a la
                                                                   // tecla

        } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
            nuevaColumna++; // Mover hacia la derecha
            protagonistaImageView.setImage(protagonistaDerecha); // En esta linea asocio la imagen que quiero a la tecla
        }

        // Comprueba si la nueva posición es válida (no es pared ni está fuera del mapa)
        if (esPosicionValida(nuevaFila, nuevaColumna)) {
            protagonista.setPosicion(nuevaFila, nuevaColumna);
            reproduce(); // Redibuja el mapa con el protagonista en la nueva posición
        }
    }

    private boolean esPosicionValida(int fila, int columna) {
        // Evita que el protagonista salga del mapa o entre en una pared (#).
        // Muestra en consola el tipo de celda a la que se quiere mover (útil para
        // depurar).

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

        // Reconstruye el mapa cada vez que el protagonista se mueve.
        // Dibuja la imagen del protagonista en su posición actual.
        // El resto de celdas se dibujan como suelo (.) o pared (#).
        // Después de redibujar, el GridPane vuelve a escuchar el teclado y se le da el
        // foco para que puedas seguir moviendo al protagonista.

        Protagonista protagonista = Proveedor.getInstance().getProtagonista();
        if (protagonista != null) {
            System.out.println("Nombre del protagonista " + protagonista.getNombre());
            System.out.println("Puntos de vida: " + protagonista.getPuntosVida());
            System.out.println(
                    "Protagonista en fila: " + protagonista.getFila() + ", columna: " + protagonista.getColumna());
        }

        try {

            LectorEscenario lectorEscenario = new LectorEscenario("/dataUrl/mapas.txt");

            // Obtén el gestor de enemigos desde el Proveedor
            GestorEnemigo gestor = Proveedor.getInstance().getGestorEnemigo();
            gestor.moverEnemigos(protagonista, lectorEscenario); // Movemos a los enemigos

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
                    ImageView celdaImageView;

                    switch (celda.getTipo()) {
                        case SUELO:
                            celdaImageView = new ImageView(imgSuelo);
                            break;
                        case PARED:
                            celdaImageView = new ImageView(imgPared);
                            break;
                        default:
                            continue;
                    }

                    celdaImageView.setFitWidth(ANCHO_CELDA);
                    celdaImageView.setFitHeight(ALTO_CELDA);
                    mainGridPane.add(celdaImageView, c, f);

                    // Si la celda es la del protagonista, muestra la imagen
                    // if (protagonista != null && f == protagonista.getFila() && c ==
                    // protagonista.getColumna()) {

                    // mainGridPane.add(protagonistaImageView, c, f); // Agregar el ImageView del
                    // protagonista

                    // // Si no está el protagonista, simplemente se muetra la celda que se crea
                    // aqui
                    // // en el switch
                    // } else {
                    // Label label = new Label();
                    // switch (celda.getTipo()) {
                    // case SUELO:
                    // // label.setText(".");
                    // // label.setTextFill(Color.RED);

                    // break;
                    // case PARED:
                    // label.setText("#");
                    // label.setTextFill(Color.DARKGRAY);
                    // break;
                    // // default:
                    // // break;
                    // }

                    // label.setFont(Font.font("Consolas", 18));
                    // mainGridPane.add(label, c, f);// He cambiado la posicion de f,c a c,f para
                    // que el mapa.txt
                    // // aparezca tal cual segun el

                    // }

                }
            }

            // Agregar el protagonista en su posición

            int filaProtagonista = protagonista.getFila();
            int columnaProtagonista = protagonista.getColumna();
            protagonistaImageView.setFitWidth(ANCHO_CELDA);
            protagonistaImageView.setFitHeight(ALTO_CELDA);
            mainGridPane.add(protagonistaImageView, columnaProtagonista, filaProtagonista); 


            // Dibujamos los enemigos con sus imágenes
            for (Enemigo enemigo : gestor.getEnemigos()) {
                int f = enemigo.getFila();
                int c = enemigo.getColumna();

                // Crearemos un ImageView para el enemigo
                ImageView enemigoImageVer = new ImageView(imgEnemigo);
                enemigoImageVer.setFitWidth(ANCHO_CELDA);
                enemigoImageVer.setFitWidth(ALTO_CELDA);

                // Agregaremos la ImageView del enemigo al GridPane
                mainGridPane.add(enemigoImageVer, c, f);
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
