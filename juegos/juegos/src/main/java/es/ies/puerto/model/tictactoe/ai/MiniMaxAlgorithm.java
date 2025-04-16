package es.ies.puerto.model.tictactoe.ai;

import es.ies.puerto.model.tictactoe.TicTacToeGame;

public class MiniMaxAlgorithm {
    
    private TicTacToeGame game;
    
    public MiniMaxAlgorithm(TicTacToeGame game) {
        this.game = game;
    }
    
    public int[] findBestMove() {
        char[][] board = game.getBoard();
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == TicTacToeGame.EMPTY) {
                    // Make the move
                    board[i][j] = TicTacToeGame.PLAYER_O;
                    
                    // Compute evaluation function for this move
                    int moveValue = minimax(board, 0, false);
                    
                    // Undo the move
                    board[i][j] = TicTacToeGame.EMPTY;
                    
                    // If the value of current move is better than best value, update best
                    if (moveValue > bestValue) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }
    
    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);
        
        // If Maximizer or Minimizer has won the game, return evaluated score
        if (score == 10 || score == -10) {
            return score;
        }
        
        // If there are no more moves and no winner, it's a tie
        if (!isMovesLeft(board)) {
            return 0;
        }
        
        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == TicTacToeGame.EMPTY) {
                        board[i][j] = TicTacToeGame.PLAYER_O;
                        best = Math.max(best, minimax(board, depth+1, !isMaximizing));
                        board[i][j] = TicTacToeGame.EMPTY;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == TicTacToeGame.EMPTY) {
                        board[i][j] = TicTacToeGame.PLAYER_X;
                        best = Math.min(best, minimax(board, depth+1, !isMaximizing));
                        board[i][j] = TicTacToeGame.EMPTY;
                    }
                }
            }
            return best;
        }
    }
    
    private int evaluate(char[][] board) {
        // Check for victory in rows
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == TicTacToeGame.PLAYER_O)
                    return 10;
                else if (board[row][0] == TicTacToeGame.PLAYER_X)
                    return -10;
            }
        }
        
        // Check for victory in columns
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == TicTacToeGame.PLAYER_O)
                    return 10;
                else if (board[0][col] == TicTacToeGame.PLAYER_X)
                    return -10;
            }
        }
        
        // Check for victory in diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == TicTacToeGame.PLAYER_O)
                return 10;
            else if (board[0][0] == TicTacToeGame.PLAYER_X)
                return -10;
        }
        
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == TicTacToeGame.PLAYER_O)
                return 10;
            else if (board[0][2] == TicTacToeGame.PLAYER_X)
                return -10;
        }
        
        // No winner
        return 0;
    }
    
    private boolean isMovesLeft(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == TicTacToeGame.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
}