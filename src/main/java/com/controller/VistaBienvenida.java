package com.controller;

import com.ronic.SceneId;
import com.ronic.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class VistaBienvenida {
     @FXML
    private Button btnEmpezar;

    @FXML
    private AnchorPane ancho;

    @FXML
    public void initialize() {
        String rutaImg = "/imagen/Bienvenida.jpg";
        ancho.setStyle("-fx-background-image: url('" + getClass().getResource(rutaImg) + "'); -fx-background-size: cover;");


        btnEmpezar.setOnAction(event -> {
            // Aquí cambias a la vista del formulario
            // Por ejemplo:
            SceneManager.getInstance().loadScene(SceneId.VISTA1);
        });
    }
    
}
