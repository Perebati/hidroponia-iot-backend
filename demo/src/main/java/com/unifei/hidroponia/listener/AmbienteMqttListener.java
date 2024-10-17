package com.unifei.hidroponia.listener;

import com.unifei.hidroponia.config.MqttProperties;
import com.unifei.hidroponia.service.AmbienteService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AmbienteMqttListener {
    
    private static final Logger logger = LoggerFactory.getLogger(AmbienteMqttListener.class);
    
    @Autowired
    private MqttClient mqttClient;
    
    @Autowired
    private MqttProperties mqttProperties;
    
    @Autowired
    private AmbienteService ambienteService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void iniciarListener() {
        try {
            String topic = mqttProperties.getTopics().getAmbienteStream();
            logger.info("Iniciando listener para tópico de ambiente: {}", topic);
            
            mqttClient.subscribe(topic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    logger.debug("Dados do ambiente recebidos: {}", payload);
                    
                    boolean sucesso = ambienteService.processarDadosAmbiente(payload);
                    if (!sucesso) {
                        logger.error("Erro ao processar dados do ambiente");
                    }
                }
            });
            
            logger.info("AmbienteMqttListener iniciado com sucesso");
            
        } catch (MqttException e) {
            logger.error("Erro ao iniciar AmbienteMqttListener: {}", e.getMessage());
        }
    }
}
