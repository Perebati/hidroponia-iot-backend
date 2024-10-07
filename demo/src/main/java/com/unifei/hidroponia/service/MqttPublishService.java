package com.unifei.hidroponia.service;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttPublishService {
    
    private static final Logger logger = LoggerFactory.getLogger(MqttPublishService.class);
    
    @Autowired
    private MqttClient mqttClient;
    
    public void publish(String topic, String message) {
        try {
            if (mqttClient.isConnected()) {
                MqttMessage mqttMessage = new MqttMessage(message.getBytes());
                mqttMessage.setQos(1);
                mqttClient.publish(topic, mqttMessage);
                logger.info("Mensagem publicada no tópico {}: {}", topic, message);
            } else {
                logger.error("Cliente MQTT não está conectado. Não foi possível publicar mensagem.");
            }
        } catch (MqttException e) {
            logger.error("Erro ao publicar mensagem no tópico {}: {}", topic, e.getMessage());
        }
    }
    
    public void publishAlert(String topic, Integer tipo, String mensagem) {
        String alertJson = String.format("{\"err_id\": \"%d\", \"err_desc\": \"%s\"}", tipo, mensagem);
        publish(topic, alertJson);
    }
}
