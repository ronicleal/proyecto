module com.ronic {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.controller; // Asegúrate de exportar este paquete
    exports com.ronic; // Exporta otros paquetes según sea necesario

    opens com.controller to javafx.fxml; // Permite a javafx.fxml acceder a las clases en este paquete
    opens com.ronic to javafx.fxml; // Abre el paquete principal si es necesario
}