package com.unifei.hidroponia.service;

import com.unifei.hidroponia.entity.Ambiente;
import com.unifei.hidroponia.repository.AmbienteRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class AmbienteService {
    
    private static final Logger logger = LoggerFactory.getLogger(AmbienteService.class);
    
    @Autowired
    private AmbienteRepository ambienteRepository;
    
    @Autowired
    private AlertaService alertaService;
    
    public boolean processarDadosAmbiente(String jsonMessage) {
        try {
            JSONObject obj = new JSONObject(jsonMessage);
            
            Integer ambId = Integer.valueOf(obj.getString("amb_id"));
            Integer tempAtual = Integer.valueOf(obj.getString("temp_atual"));
            Integer tempMin = Integer.valueOf(obj.getString("temp_min"));
            Integer tempMax = Integer.valueOf(obj.getString("temp_max"));
            BigDecimal phAtual = new BigDecimal(obj.getString("ph_atual"));
            BigDecimal phTarget = new BigDecimal(obj.getString("ph_target"));
            Integer lumAtual = Integer.valueOf(obj.getString("lum_atual"));
            
            // Verificar alertas
            verificarAlertas(tempAtual, tempMin, tempMax, phAtual, phTarget);
            
            // Salvar dados no banco
            Ambiente ambiente = new Ambiente(
                tempAtual, tempMin, tempMax, phAtual, phTarget, lumAtual,
                ambId, LocalDate.now(), LocalTime.now()
            );
            
            ambienteRepository.save(ambiente);
            logger.info("Dados do ambiente salvos com sucesso. Ambiente ID: {}", ambId);
            
            return true;
            
        } catch (Exception e) {
            logger.error("Erro ao processar dados do ambiente: {}", e.getMessage());
            return false;
        }
    }
    
    private void verificarAlertas(Integer tempAtual, Integer tempMin, Integer tempMax, 
                                 BigDecimal phAtual, BigDecimal phTarget) {
        
        if (tempAtual > tempMax) {
            alertaService.enviarAlerta(5, "Temperatura muito elevada!");
        } else if (tempAtual < tempMin) {
            alertaService.enviarAlerta(5, "Temperatura muito baixa!");
        }
        
        BigDecimal phDiff = phAtual.subtract(phTarget);
        if (phDiff.compareTo(new BigDecimal("3")) > 0) {
            alertaService.enviarAlerta(5, "pH muito alto!");
        } else if (phDiff.compareTo(new BigDecimal("-3")) < 0) {
            alertaService.enviarAlerta(5, "pH muito baixo!");
        }
    }
}
