package com.ronic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = SceneManager.getInstance();
        
        

        sceneManager.setScene(SceneId.VISTABIENVENIDA,"VistaBienvenida");
        sceneManager.setScene(SceneId.VISTA1,"Vista1");
        sceneManager.setScene(SceneId.VISTA2,"Vista2");

        sceneManager.init(stage);
        sceneManager.loadScene(SceneId.VISTABIENVENIDA);
        stage.setTitle("Juego_Mazmorras");
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}