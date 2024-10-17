package com.unifei.hidroponia.listener;

import com.unifei.hidroponia.config.MqttProperties;
import com.unifei.hidroponia.service.AlertaService;
import com.unifei.hidroponia.service.LoginService;
import com.unifei.hidroponia.service.MqttPublishService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoginMqttListener {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginMqttListener.class);
    
    @Autowired
    private MqttClient mqttClient;
    
    @Autowired
    private MqttProperties mqttProperties;
    
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private MqttPublishService mqttPublishService;
    
    @Autowired
    private AlertaService alertaService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void iniciarListener() {
        try {
            String topic = mqttProperties.getTopics().getLoginRequest();
            logger.info("Iniciando listener para tópico de login: {}", topic);
            
            mqttClient.subscribe(topic);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    logger.error("Conexão MQTT perdida no LoginListener: {}", cause.getMessage());
                }
                
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    logger.info("Tentativa de login recebida: {}", payload);
                    
                    String loginResult = loginService.verificarLogin(payload);
                    
                    if (loginResult != null) {
                        logger.info("Login bem-sucedido");
                        alertaService.enviarAlerta(0, "Login bem sucedido");
                        mqttPublishService.publish(mqttProperties.getTopics().getLoginReply(), loginResult);
                    } else {
                        logger.warn("Falha no login");
                        String errorMessage = "{\"err_id\": \"3\", \"err_desc\": \"Usuario ou senha errado!\"}";
                        mqttPublishService.publish(mqttProperties.getTopics().getLoginReply(), errorMessage);
                    }
                }
                
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    // Não utilizado
                }
            });
            
            logger.info("LoginMqttListener iniciado com sucesso");
            
        } catch (MqttException e) {
            logger.error("Erro ao iniciar LoginMqttListener: {}", e.getMessage());
        }
    }
}
