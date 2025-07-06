package com.unifei.hidroponia.service;

import com.unifei.hidroponia.config.MqttProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertaService {
    
    private static final Logger logger = LoggerFactory.getLogger(AlertaService.class);
    
    @Autowired
    private MqttPublishService mqttPublishService;
    
    @Autowired
    private MqttProperties mqttProperties;
    
    public void enviarAlerta(Integer tipo, String mensagem) {
        try {
            logger.info("Enviando alerta - Tipo: {}, Mensagem: {}", tipo, mensagem);
            mqttPublishService.publishAlert(mqttProperties.getTopics().getAlerta(), tipo, mensagem);
        } catch (Exception e) {
            logger.error("Erro ao enviar alerta: {}", e.getMessage());
        }
    }
}
