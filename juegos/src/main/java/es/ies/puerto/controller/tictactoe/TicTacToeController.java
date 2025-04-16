package es.ies.puerto.controller.tictactoe;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.UsuarioSesion;
import es.ies.puerto.model.tictactoe.TicTacToeGame;
import es.ies.puerto.model.tictactoe.TicTacToeServiceModel;
import es.ies.puerto.model.tictactoe.TicTacToeStats;
import es.ies.puerto.model.tictactoe.ai.MiniMaxAlgorithm;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class TicTacToeController extends AbstractController {
    @FXML
    private GridPane gameBoard;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Button restartButton;
    
    @FXML
    private Button statsButton;
    
    @FXML
    private Button backButton;
    
    private TicTacToeGame game;
    private Button[][] buttons;
    private MiniMaxAlgorithm ai;
    
    @FXML
    public void initialize() {
        game = new TicTacToeGame();
        ai = new MiniMaxAlgorithm(game);
        buttons = new Button[3][3];
        
        // Inicializa el tablero de botones
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setMinSize(80, 80);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                
                final int row = i;
                final int col = j;
                
                button.setOnAction(event -> handleButtonClick(row, col));
                
                gameBoard.add(button, j, i);
                buttons[i][j] = button;
            }
        }
        
        updateUI();
    }
    
    private void handleButtonClick(int row, int col) {
        if (game.isGameOver() || game.getCurrentPlayer() != TicTacToeGame.PLAYER_X) {
            return;
        }
        
        if (game.makeMove(row, col)) {
            updateUI();
            
            // Si el juego no ha terminado, la IA hace su movimiento
            if (!game.isGameOver()) {
                // Pequeña pausa para que la IA parezca que está "pensando"
                new Thread(() -> {
                    try {
                        Thread.sleep(500);
                        Platform.runLater(() -> makeAIMove());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                handleGameOver();
            }
        }
    }
    
    private void makeAIMove() {
        if (!game.isGameOver()) {
            int[] bestMove = ai.findBestMove();
            game.makeMove(bestMove[0], bestMove[1]);
            updateUI();
            
            if (game.isGameOver()) {
                handleGameOver();
            }
        }
    }
    
    private void updateUI() {
        char[][] board = game.getBoard();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == TicTacToeGame.PLAYER_X) {
                    buttons[i][j].setText("X");
                } else if (board[i][j] == TicTacToeGame.PLAYER_O) {
                    buttons[i][j].setText("O");
                } else {
                    buttons[i][j].setText("");
                }
            }
        }
        
        if (game.isGameOver()) {
            if (game.isDraw()) {
                statusLabel.setText("¡Empate!");
            } else {
                char winner = game.getWinner();
                if (winner == TicTacToeGame.PLAYER_X) {
                    statusLabel.setText("¡Has ganado!");
                } else {
                    statusLabel.setText("¡Has perdido!");
                }
            }
        } else {
            if (game.getCurrentPlayer() == TicTacToeGame.PLAYER_X) {
                statusLabel.setText("Tu turno (X)");
            } else {
                statusLabel.setText("Turno de la IA (O)");
            }
        }
    }
    
    private void handleGameOver() {
        boolean victoria = game.getWinner() == TicTacToeGame.PLAYER_X;
        boolean empate = game.isDraw();
        
        try {
            // Obtener el email del usuario actual
            String userEmail = UsuarioSesion.getInstancia().getUsuario().getEmail();
            
            // Usar la misma ruta de base de datos que el resto de la aplicación
            String dbPath = PATH_DB;  // Esta variable está definida en AbstractController
            
            // Crear una conexión a la BD
            TicTacToeServiceModel serviceModel = new TicTacToeServiceModel(dbPath);
            
            // Asegurarnos que la tabla existe
            serviceModel.crearTablaEstadisticas();
            
            // Obtener las estadísticas actuales
            TicTacToeStats stats = serviceModel.obtenerEstadisticasUsuario(userEmail);
            
            // Actualizar las estadísticas según el resultado
            stats.actualizarEstadisticas(victoria, empate);
            
            // Guardar las estadísticas actualizadas
            if (!serviceModel.actualizarEstadisticas(stats)) {
                System.err.println("Error al guardar las estadísticas");
            } else {
                System.out.println("Estadísticas actualizadas con éxito");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al actualizar las estadísticas: " + e.getMessage());
        }
    }
    
    @FXML
    private void onRestartClick() {
        game.reset();
        updateUI();
    }
    
    @FXML
    private void onStatsClick() {
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("estadisticasTicTacToe.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Estadísticas del Tres en Raya");
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void onBackClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("perfil.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 450, 760);
            stage.setTitle("Perfil de Usuario");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}