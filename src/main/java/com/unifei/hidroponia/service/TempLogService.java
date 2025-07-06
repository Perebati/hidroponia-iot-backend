package com.unifei.hidroponia.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class TempLogService {
    
    private static final Logger logger = LoggerFactory.getLogger(TempLogService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public boolean registrarMudancaTemp(String jsonMessage) {
        try {
            JSONObject obj = new JSONObject(jsonMessage);
            
            String cliId = obj.getString("cli_id");
            String ambId = obj.getString("amb_id");
            String tempMin = obj.getString("temp_min");
            String tempMax = obj.getString("temp_max");
            
            LocalDate hoje = LocalDate.now();
            LocalTime agora = LocalTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            String sql = "INSERT INTO temp_log (tempMin, tempMax, dataRegistro, horaRegistro, amb_id, cli_amb) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, tempMin, tempMax, hoje.format(dateFormatter), agora.format(timeFormatter), ambId, cliId);
            
            logger.info("Log de mudança de temperatura registrado no banco de dados. Temp Min: {}, Temp Max: {}", tempMin, tempMax);
            return true;
            
        } catch (Exception e) {
            logger.error("Erro ao registrar mudança de temperatura: {}", e.getMessage());
            return false;
        }
    }
}
