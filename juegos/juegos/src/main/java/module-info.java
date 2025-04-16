module es.ies.puerto {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.graphics;
    requires java.sql;

    // Exportaciones básicas
    exports es.ies.puerto;
    exports es.ies.puerto.servicio;
    exports es.ies.puerto.controller;
    exports es.ies.puerto.model;
    
    // Exportación del nuevo paquete para TicTacToe
    exports es.ies.puerto.controller.tictactoe;
    
    // Apertura de paquetes a JavaFX FXML
    opens es.ies.puerto to javafx.fxml;
    opens es.ies.puerto.controller to javafx.fxml;
    opens es.ies.puerto.controller.tictactoe to javafx.fxml;
}