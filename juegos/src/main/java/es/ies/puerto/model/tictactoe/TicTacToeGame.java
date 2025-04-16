package es.ies.puerto.model.tictactoe;

public class TicTacToeGame {
    public static final char EMPTY = ' ';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';
    
    private char[][] board;
    private char currentPlayer;
    private boolean gameOver;
    private char winner;
    
    public TicTacToeGame() {
        board = new char[3][3];
        reset();
    }
    
    public void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
        currentPlayer = PLAYER_X;
        gameOver = false;
        winner = EMPTY;
    }
    
    public boolean makeMove(int row, int col) {
        if (gameOver || row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != EMPTY) {
            return false;
        }
        
        board[row][col] = currentPlayer;
        checkForWinner();
        
        if (!gameOver) {
            currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
        }
        
        return true;
    }
    
    private void checkForWinner() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                gameOver = true;
                winner = board[i][0];
                return;
            }
        }
        
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != EMPTY && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                gameOver = true;
                winner = board[0][j];
                return;
            }
        }
        
        // Check diagonals
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            gameOver = true;
            winner = board[0][0];
            return;
        }
        
        if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            gameOver = true;
            winner = board[0][2];
            return;
        }
        
        // Check for draw
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    isDraw = false;
                    break;
                }
            }
        }
        
        if (isDraw) {
            gameOver = true;
        }
    }
    
    // Getters
    public char[][] getBoard() {
        return board;
    }
    
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public char getWinner() {
        return winner;
    }
    
    public boolean isDraw() {
        return gameOver && winner == EMPTY;
    }
}