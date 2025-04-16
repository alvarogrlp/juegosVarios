package es.ies.puerto.model.tictactoe;

import es.ies.puerto.model.abtrastas.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TicTacToeServiceModel extends Conexion {
    
    public TicTacToeServiceModel(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }
    
    /**
     * Obtiene las estadísticas de un usuario para el Tres en Raya
     * @param userEmail Email del usuario
     * @return TicTacToeStats Estadísticas del usuario
     */
    public TicTacToeStats obtenerEstadisticasUsuario(String userEmail) {
        TicTacToeStats stats = new TicTacToeStats(userEmail);
        
        try {
            String sql = "SELECT * FROM tictactoe_stats WHERE user_email = ?";
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, userEmail);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                stats.setPartidasTotales(rs.getInt("partidas_totales"));
                stats.setPartidasGanadas(rs.getInt("partidas_ganadas"));
                stats.setPartidasEmpatadas(rs.getInt("partidas_empatadas"));
                stats.setPartidasPerdidas(rs.getInt("partidas_perdidas"));
                stats.setPuntuacionTotal(rs.getInt("puntuacion_total"));
                stats.setRachaActual(rs.getInt("racha_actual"));
                stats.setRachaMaxima(rs.getInt("racha_maxima"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return stats;
    }
    
    /**
     * Actualiza las estadísticas de un usuario para el Tres en Raya
     * @param stats Estadísticas a actualizar
     * @return boolean Verdadero si se actualizó correctamente
     * @throws SQLException Error SQL
     */
    public boolean actualizarEstadisticas(TicTacToeStats stats) throws SQLException {
        // Comprobar si existen estadísticas para este usuario
        TicTacToeStats existentes = obtenerEstadisticasUsuario(stats.getUserEmail());
        
        try {
            PreparedStatement stmt;
            
            if (existentes.getPartidasTotales() == 0) {
                // Crear nuevo registro
                String sql = "INSERT INTO tictactoe_stats (user_email, partidas_totales, partidas_ganadas, " +
                             "partidas_empatadas, partidas_perdidas, puntuacion_total, racha_actual, racha_maxima) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                             
                stmt = getConnection().prepareStatement(sql);
                stmt.setString(1, stats.getUserEmail());
                stmt.setInt(2, stats.getPartidasTotales());
                stmt.setInt(3, stats.getPartidasGanadas());
                stmt.setInt(4, stats.getPartidasEmpatadas());
                stmt.setInt(5, stats.getPartidasPerdidas());
                stmt.setInt(6, stats.getPuntuacionTotal());
                stmt.setInt(7, stats.getRachaActual());
                stmt.setInt(8, stats.getRachaMaxima());
            } else {
                // Actualizar registro existente
                String sql = "UPDATE tictactoe_stats SET " +
                             "partidas_totales = ?, " +
                             "partidas_ganadas = ?, " +
                             "partidas_empatadas = ?, " +
                             "partidas_perdidas = ?, " +
                             "puntuacion_total = ?, " +
                             "racha_actual = ?, " +
                             "racha_maxima = ? " +
                             "WHERE user_email = ?";
                             
                stmt = getConnection().prepareStatement(sql);
                stmt.setInt(1, stats.getPartidasTotales());
                stmt.setInt(2, stats.getPartidasGanadas());
                stmt.setInt(3, stats.getPartidasEmpatadas());
                stmt.setInt(4, stats.getPartidasPerdidas());
                stmt.setInt(5, stats.getPuntuacionTotal());
                stmt.setInt(6, stats.getRachaActual());
                stmt.setInt(7, stats.getRachaMaxima());
                stmt.setString(8, stats.getUserEmail());
            }
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Crea la tabla de estadísticas si no existe
     */
    public boolean crearTablaEstadisticas() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS tictactoe_stats (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_email TEXT NOT NULL, " +
                    "partidas_totales INTEGER DEFAULT 0, " +
                    "partidas_ganadas INTEGER DEFAULT 0, " +
                    "partidas_empatadas INTEGER DEFAULT 0, " +
                    "partidas_perdidas INTEGER DEFAULT 0, " +
                    "puntuacion_total INTEGER DEFAULT 0, " +
                    "racha_actual INTEGER DEFAULT 0, " +
                    "racha_maxima INTEGER DEFAULT 0, " +
                    "UNIQUE(user_email))";
            
            Statement stmt = getConnection().createStatement();
            stmt.execute(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}