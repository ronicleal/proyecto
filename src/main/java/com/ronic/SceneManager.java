package com.ronic;

import java.io.IOException;
import java.util.HashMap;

import com.controller.Vista2Controller;
import com.model.Protagonista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
     private static SceneManager instance;
    private Stage stage;
    private HashMap<SceneId, Scene> scenes;
    private Protagonista protagonista;

    private SceneManager() {
        scenes = new HashMap<>();
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void init(Stage stage) {
        this.stage = stage;
    }

    public void setScene(SceneId sceneID, String fxml) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            scene.setUserData(fxmlLoader);//Almacena el FXMLLoader en el UserData
            scenes.put(sceneID, scene); // Almacena la escena en el mapa con el identificador correspondiente
        } catch (IOException e) {
            e.printStackTrace(); // En caso de error al cargar el FXML
        }
    }




    public void loadScene(SceneId sceneID, Protagonista protagonista) {
        this.protagonista = protagonista;
        if (scenes.containsKey(sceneID)){
            stage.setScene(scenes.get(sceneID)); // Establece la escena en la ventana principal
            stage.show(); // Muestra la ventana con la nueva escena
        }

        //Pasar el protagonista al controlador de la nueva escena
        if(scenes.get(sceneID).getUserData() instanceof FXMLLoader){
            FXMLLoader loader = (FXMLLoader) scenes.get(sceneID).getUserData();
            Vista2Controller controller = loader.getController();
            if (controller != null){//Verificar que el controlador no sea nulo
                controller.setProtagonista(protagonista); //Pasa el protagonista al controlador
            }
        }
    }


    

    //Este metodo se añade para cargar la primera vista de bienvenida
    public void loadScene(SceneId sceneID) {
        if (scenes.containsKey(sceneID)) {
            stage.setScene(scenes.get(sceneID));
            stage.show();
        }
    }



    /**
     * Esto metod sirve para verificar si una escena ya está cargada antes de intentar mostrarla o manipularla.
     * @param id
     * @return
     */
    public Scene getScene(SceneId id) {
        return scenes.get(id);
    }
    
    


}
