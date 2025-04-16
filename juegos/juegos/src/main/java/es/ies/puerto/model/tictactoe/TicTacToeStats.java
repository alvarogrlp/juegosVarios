package es.ies.puerto.model.tictactoe;

public class TicTacToeStats {
    private String userEmail; // Cambiado de userId (int) a userEmail (String)
    private int partidasTotales;
    private int partidasGanadas;
    private int partidasEmpatadas;
    private int partidasPerdidas;
    private int puntuacionTotal;
    private int rachaActual;
    private int rachaMaxima;
    
    public TicTacToeStats() {
        this.partidasTotales = 0;
        this.partidasGanadas = 0;
        this.partidasEmpatadas = 0;
        this.partidasPerdidas = 0;
        this.puntuacionTotal = 0;
        this.rachaActual = 0;
        this.rachaMaxima = 0;
    }
    
    public TicTacToeStats(String userEmail) {
        this();
        this.userEmail = userEmail;
    }

    /**
     * Actualiza las estadísticas según el resultado del juego
     * @param victoria True si el usuario ganó
     * @param empate True si hubo empate
     */
    public void actualizarEstadisticas(boolean victoria, boolean empate) {
        partidasTotales++;
        
        if (victoria) {
            partidasGanadas++;
            rachaActual++;
            puntuacionTotal += 3; // 3 puntos por victoria
            
            if (rachaActual > rachaMaxima) {
                rachaMaxima = rachaActual;
            }
        } else if (empate) {
            partidasEmpatadas++;
            puntuacionTotal += 1; // 1 punto por empate
            // En un empate la racha no se reinicia pero tampoco aumenta
        } else {
            partidasPerdidas++;
            rachaActual = 0; // Se reinicia la racha al perder
        }
    }
    
    // Getters y setters
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public int getPartidasTotales() {
        return partidasTotales;
    }
    
    public void setPartidasTotales(int partidasTotales) {
        this.partidasTotales = partidasTotales;
    }
    
    public int getPartidasGanadas() {
        return partidasGanadas;
    }
    
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }
    
    public int getPartidasEmpatadas() {
        return partidasEmpatadas;
    }
    
    public void setPartidasEmpatadas(int partidasEmpatadas) {
        this.partidasEmpatadas = partidasEmpatadas;
    }
    
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }
    
    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }
    
    public int getPuntuacionTotal() {
        return puntuacionTotal;
    }
    
    public void setPuntuacionTotal(int puntuacionTotal) {
        this.puntuacionTotal = puntuacionTotal;
    }
    
    public int getRachaActual() {
        return rachaActual;
    }
    
    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }
    
    public int getRachaMaxima() {
        return rachaMaxima;
    }
    
    public void setRachaMaxima(int rachaMaxima) {
        this.rachaMaxima = rachaMaxima;
    }
}