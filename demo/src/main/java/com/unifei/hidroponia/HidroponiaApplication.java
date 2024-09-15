package com.unifei.hidroponia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.unifei.hidroponia.config.MqttProperties;

@SpringBootApplication
@EnableConfigurationProperties(MqttProperties.class)
public class HidroponiaApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(HidroponiaApplication.class);

    public static void main(String[] args) {
        logger.info("Iniciando Sistema de Hidroponia Backend...");
        
        SpringApplication.run(HidroponiaApplication.class, args);
        
        logger.info("Sistema de Hidroponia Backend iniciado com sucesso!");
        logger.info("Aguardando mensagens MQTT...");
    }
}
