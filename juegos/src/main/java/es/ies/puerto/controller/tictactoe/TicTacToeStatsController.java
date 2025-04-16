package es.ies.puerto.controller.tictactoe;

import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.UsuarioSesion;
import es.ies.puerto.model.tictactoe.TicTacToeStats;
import es.ies.puerto.model.tictactoe.TicTacToeServiceModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TicTacToeStatsController extends AbstractController {
    
    @FXML
    private Label partidasTotalesLabel;
    
    @FXML
    private Label victoriasLabel;
    
    @FXML
    private Label empatesLabel;
    
    @FXML
    private Label derrotasLabel;
    
    @FXML
    private Label puntuacionLabel;
    
    @FXML
    private Label rachaActualLabel;
    
    @FXML
    private Label mejorRachaLabel;
    
    @FXML
    private Button closeButton;
    
    @FXML
    public void initialize() {
        try {
            // Obtener el usuario actual usando el email como identificador
            String userEmail = UsuarioSesion.getInstancia().getUsuario().getEmail();
            
            // Usar la ruta de base de datos definida en AbstractController
            String dbPath = PATH_DB;
            
            // Crear conexión a la BD y obtener estadísticas reales
            TicTacToeServiceModel serviceModel = new TicTacToeServiceModel(dbPath);
            
            // Asegurarse que la tabla existe
            serviceModel.crearTablaEstadisticas();
            
            // Obtener las estadísticas desde la BD
            TicTacToeStats stats = serviceModel.obtenerEstadisticasUsuario(userEmail);
            
            // Actualiza las etiquetas con los datos
            partidasTotalesLabel.setText(String.valueOf(stats.getPartidasTotales()));
            victoriasLabel.setText(String.valueOf(stats.getPartidasGanadas()));
            empatesLabel.setText(String.valueOf(stats.getPartidasEmpatadas()));
            derrotasLabel.setText(String.valueOf(stats.getPartidasPerdidas()));
            puntuacionLabel.setText(String.valueOf(stats.getPuntuacionTotal()));
            rachaActualLabel.setText(String.valueOf(stats.getRachaActual()));
            mejorRachaLabel.setText(String.valueOf(stats.getRachaMaxima()));
            
            // Para debugging
            System.out.println("Estadísticas cargadas: " + 
                "Partidas totales=" + stats.getPartidasTotales() + 
                ", Ganadas=" + stats.getPartidasGanadas() + 
                ", Empatadas=" + stats.getPartidasEmpatadas() + 
                ", Perdidas=" + stats.getPartidasPerdidas());
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar estadísticas: " + e.getMessage());
            // Manejar errores apropiadamente
        }
    }
    
    @FXML
    private void onCloseClick() {
        // Cierra la ventana actual
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
