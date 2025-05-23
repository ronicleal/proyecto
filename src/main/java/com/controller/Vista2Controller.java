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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Vista2Controller implements Observer {
    // Recibir el objeto Protagonista

    private Image protagonistaArriba;
    private Image protagonistaAbajo;
    private Image protagonistaIzquierda;
    private Image protagonistaDerecha;

    private Image enemigoArriba;
    private Image enemigoAbajo;
    private Image enemigoIzquierda;
    private Image enemigoDerecha;

    private Image imgSuelo;// Imagen para representar el suelo
    private Image imgPared;// Imagen para representar la pared

    @FXML
    private HBox root;// Contenedor principal de la vista HBox para que el contenedor contenga
                      // informacion vertical

    private GridPane mainGridPane;// La cuadricula donde se dibuja el mapa

    private static final int ANCHO_CELDA = 60; // Tamaño de ancho de la celda del mapa

    private static final int ALTO_CELDA = 60; // Tamaño de alto de la celda

    private ImageView protagonistaImageView; // Imagen del protagonista

    private ImageView enemigoImageView; // Imagen del enemigo

    /**
     * El método setProtagonista tiene como objetivo recibir un objeto Protagonista
     * desde otra clase
     * (probablemente desde Vista1Controller o el SceneManager) y
     * realizar dos acciones principales:
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

        enemigoArriba = new Image(getClass().getResourceAsStream("/imagen/zombito_arriba.png"));
        enemigoAbajo = new Image(getClass().getResourceAsStream("/imagen/zombito_abajo.png"));
        enemigoIzquierda = new Image(getClass().getResourceAsStream("/imagen/zombito_izquierda.png"));
        enemigoDerecha = new Image(getClass().getResourceAsStream("/imagen/zombito_derecha.png"));

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

        enemigoImageView = new ImageView(enemigoIzquierda);
        enemigoImageView.setFitWidth(ANCHO_CELDA);
        enemigoImageView.setFitHeight(ALTO_CELDA);

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
        if (enemigoArriba.isError() || enemigoAbajo.isError() ||
                enemigoDerecha.isError() || enemigoIzquierda.isError()) {
            System.err.println("Error al cargar la imagen del protagonista.");
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
        root.getChildren().clear(); // Se coloca de primero para que limpie el contenedor principal antes de cargar
                                    // los datos

        // Reconstruye el mapa cada vez que el protagonista se mueve.
        // Dibuja la imagen del protagonista en su posición actual.
        // El resto de celdas se dibujan como suelo (.) o pared (#).
        // Después de redibujar, el GridPane vuelve a escuchar el teclado y se le da el
        // foco para que puedas seguir moviendo al protagonista.

        Protagonista protagonista = Proveedor.getInstance().getProtagonista();

        try {

            LectorEscenario lectorEscenario = new LectorEscenario("/dataUrl/mapas.txt");

            // Obtén el gestor de enemigos desde el Proveedor
            GestorEnemigo gestor = Proveedor.getInstance().getGestorEnemigo();
            gestor.moverEnemigos(protagonista, lectorEscenario); // Movemos a los enemigos

            int filas = lectorEscenario.getAlto();
            int columnas = lectorEscenario.getAncho();

            mainGridPane = new GridPane();
            mainGridPane.setMaxSize(GridPane.USE_PREF_SIZE, GridPane.USE_PREF_SIZE);

            root.setAlignment(javafx.geometry.Pos.TOP_LEFT);
            // HBox.setMargin(mainGridPane, new Insets(20, 0, 20, 0));

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
                ImageView enemigoImageVer = new ImageView(enemigoAbajo);
                enemigoImageVer.setFitWidth(ANCHO_CELDA);
                enemigoImageVer.setFitHeight(ALTO_CELDA);

                // Agregaremos la ImageView del enemigo al GridPane
                mainGridPane.add(enemigoImageVer, c, f);
            }

            VBox datosJugador = new VBox(10);// Espacio vertical entre los label
            datosJugador.setPadding(new Insets(10));
            if (protagonista != null) {
                Label lblNombre = new Label("Nombre del protagonista " + protagonista.getNombre());
                Label lblVida = new Label("Puntos de vida: " + protagonista.getPuntosVida());
                Label lblDefensa = new Label("Defensa: " + protagonista.getDefensa());
                Label lblFuerza = new Label("Fuerza: " + protagonista.getFuerza());
                Label lblPosicion = new Label(
                        "Protagonista en fila: " + protagonista.getFila() + ", columna: " + protagonista.getColumna());

                Font fuente = Font.font("Arial", 20);
                lblNombre.setFont(fuente);
                lblVida.setFont(fuente);
                lblDefensa.setFont(fuente);
                lblFuerza.setFont(fuente);
                lblPosicion.setFont(fuente);

                datosJugador.getChildren().addAll(lblNombre, lblVida, lblDefensa, lblFuerza, lblPosicion);
            }

            // Se agrega al nodo raiz el gridPane y los datos del jugdor
            root.getChildren().addAll(mainGridPane, datosJugador);

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
