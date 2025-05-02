package com.controller;

import com.model.LectorEscenario;
import com.model.Protagonista;
import com.model.TipoCelda;
import com.ronic.SceneId;
import com.ronic.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Vista1Controller {
     @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPuntosVida;

    @FXML
    private TextField txtDefensa;

    @FXML
    private TextField txtFuerza;

    @FXML
    private Button btnIniciar;

    @FXML
    private AnchorPane rootVista1;

    @FXML
    public void initialize(){

        String rutaImg = "/imagen/fondoMazmorra.png";
        rootVista1.setStyle("-fx-background-image: url('" + getClass().getResource(rutaImg) + "'); -fx-background-size: cover;");

        //stage.getIcons().add(new Image(App.class.getResource("images/logo.png").toExternalForm()));

        //Recoger datos del jugador
        //Para recoger los datos del jugador en la primera vista se debe declaras las variables con sus atributos
        //luego se iguala al id que hemos puesto en el Scene Builder de cada campo de texto y se llama al metodo get.Text();
        //En caso de que el campo sea TextField y que el dato que ingresamos sea un numero, tenemos que convertir ese texto
        //en numero, por eso se llama a Interger.parseInt(mentemos el id del TextField con el metodo getText())
         btnIniciar.setOnAction(event ->{
        try{
        String nombre = txtNombre.getText();
        int puntosVida = Integer.parseInt(txtPuntosVida.getText());
        int defensa = Integer.parseInt(txtDefensa.getText());
        int fuerza = Integer.parseInt(txtFuerza.getText());
        int danio = Integer.parseInt(txtFuerza.getText());





        // En esta parte se llama a LectorEscenario para buscar la primera celda de suelo
        //Abre el archivo del mapa.txt, recorre todas las filas y columnas del mapa y busca la primera celda que sea suelo
        //(.) en el mapa, TipoCelda.SUELO en el codigo y guarda la posicion (fila, columna) de esa celda.

                LectorEscenario lector = new LectorEscenario("/dataUrl/mapas.txt");
                int fila = 0;
                int columna = 0;
                boolean encontrado = false;

            for (int f = 0; f < lector.getAlto() && !encontrado; f++) {
                    for (int c = 0; c < lector.getAncho() && !encontrado; c++) {
                        if (lector.getCelda(f, c).getTipo() == TipoCelda.SUELO) {
                            fila = f;
                            columna = c;
                            encontrado = true;
                        }
                    }
                }

                //Crea un objeto Protagonista con los datos del usuario y la posición inicial encontrada en el mapa.
                
                Protagonista protagonista = new Protagonista (nombre, defensa, fuerza, danio, puntosVida, fila, columna);

                //Pasar el protagonista a la siguiente vista
                SceneManager.getInstance().loadScene(SceneId.VISTA2, protagonista);

        }catch (Exception e) {
            e.printStackTrace();
        }
        

        

        });
    }

   



}
