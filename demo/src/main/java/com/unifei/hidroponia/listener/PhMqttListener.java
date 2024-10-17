package com.unifei.hidroponia.listener;

import com.unifei.hidroponia.config.MqttProperties;
import com.unifei.hidroponia.service.PhLogService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PhMqttListener {
    
    private static final Logger logger = LoggerFactory.getLogger(PhMqttListener.class);
    
    @Autowired
    private MqttClient mqttClient;
    
    @Autowired
    private MqttProperties mqttProperties;
    
    @Autowired
    private PhLogService phLogService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void iniciarListener() {
        try {
            String topic = mqttProperties.getTopics().getPhSet();
            logger.info("Iniciando listener para tópico de pH: {}", topic);
            
            mqttClient.subscribe(topic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    logger.info("Mudança de pH recebida: {}", payload);
                    
                    boolean sucesso = phLogService.registrarMudancaPh(payload);
                    if (!sucesso) {
                        logger.error("Erro ao registrar mudança de pH");
                    }
                }
            });
            
            logger.info("PhMqttListener iniciado com sucesso");
            
        } catch (MqttException e) {
            logger.error("Erro ao iniciar PhMqttListener: {}", e.getMessage());
        }
    }
}
