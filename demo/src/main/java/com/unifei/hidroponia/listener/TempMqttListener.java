package com.unifei.hidroponia.listener;

import com.unifei.hidroponia.config.MqttProperties;
import com.unifei.hidroponia.service.TempLogService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TempMqttListener {
    
    private static final Logger logger = LoggerFactory.getLogger(TempMqttListener.class);
    
    @Autowired
    private MqttClient mqttClient;
    
    @Autowired
    private MqttProperties mqttProperties;
    
    @Autowired
    private TempLogService tempLogService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void iniciarListener() {
        try {
            String topic = mqttProperties.getTopics().getTempSet();
            logger.info("Iniciando listener para tópico de temperatura: {}", topic);
            
            mqttClient.subscribe(topic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    logger.info("Mudança de temperatura recebida: {}", payload);
                    
                    boolean sucesso = tempLogService.registrarMudancaTemp(payload);
                    if (!sucesso) {
                        logger.error("Erro ao registrar mudança de temperatura");
                    }
                }
            });
            
            logger.info("TempMqttListener iniciado com sucesso");
            
        } catch (MqttException e) {
            logger.error("Erro ao iniciar TempMqttListener: {}", e.getMessage());
        }
    }
}
