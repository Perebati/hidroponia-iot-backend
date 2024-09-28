package com.unifei.hidroponia.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(MqttConfig.class);
    
    @Autowired
    private MqttProperties mqttProperties;
    
    @Bean
    public MqttClient mqttClient() throws MqttException {
        try {
            String clientId = "HidroponiaBackend_" + System.currentTimeMillis();
            MqttClient client = new MqttClient(mqttProperties.getBroker(), clientId);
            
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(30);
            options.setKeepAliveInterval(60);
            
            client.connect(options);
            logger.info("MQTT Client conectado ao broker: {}", mqttProperties.getBroker());
            
            return client;
        } catch (MqttException e) {
            logger.error("Erro ao conectar no MQTT broker: {}", e.getMessage());
            throw e;
        }
    }
}
