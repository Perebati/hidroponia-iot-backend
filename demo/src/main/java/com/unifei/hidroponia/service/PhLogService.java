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
public class PhLogService {
    
    private static final Logger logger = LoggerFactory.getLogger(PhLogService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public boolean registrarMudancaPh(String jsonMessage) {
        try {
            JSONObject obj = new JSONObject(jsonMessage);
            
            String cliId = obj.getString("cli_id");
            String ambId = obj.getString("amb_id");
            String phTarget = obj.getString("ph_target");
            
            LocalDate hoje = LocalDate.now();
            LocalTime agora = LocalTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            String sql = "INSERT INTO ph_log (phTarget, dataRegistro, horaRegistro, amb_id, cli_amb) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, phTarget, hoje.format(dateFormatter), agora.format(timeFormatter), ambId, cliId);
            
            logger.info("Log de mudança de pH registrado no banco de dados. pH Target: {}", phTarget);
            return true;
            
        } catch (Exception e) {
            logger.error("Erro ao registrar mudança de pH: {}", e.getMessage());
            return false;
        }
    }
}
