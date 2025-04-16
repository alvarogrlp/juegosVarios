-- Primero, crear una tabla temporal con la nueva estructura
CREATE TABLE tictactoe_stats_temp (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_email TEXT NOT NULL,
    partidas_totales INTEGER DEFAULT 0,
    partidas_ganadas INTEGER DEFAULT 0,
    partidas_empatadas INTEGER DEFAULT 0,
    partidas_perdidas INTEGER DEFAULT 0,
    puntuacion_total INTEGER DEFAULT 0,
    racha_actual INTEGER DEFAULT 0,
    racha_maxima INTEGER DEFAULT 0,
    UNIQUE(user_email)
);

-- Si hay datos en la tabla original, cópialos a la nueva tabla
-- (esto es opcional ya que probablemente no tengas datos aún)
INSERT INTO tictactoe_stats_temp (user_email, partidas_totales, partidas_ganadas,
                                 partidas_empatadas, partidas_perdidas, puntuacion_total,
                                 racha_actual, racha_maxima)
SELECT 
    (SELECT email FROM usuario WHERE usuario.id = tictactoe_stats.user_id), 
    partidas_totales, 
    partidas_ganadas, 
    partidas_empatadas, 
    partidas_perdidas, 
    puntuacion_total, 
    racha_actual, 
    racha_maxima
FROM tictactoe_stats;

-- Eliminar la tabla original
DROP TABLE IF EXISTS tictactoe_stats;

-- Renombrar la tabla temporal a la original
ALTER TABLE tictactoe_stats_temp RENAME TO tictactoe_stats;