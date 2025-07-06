package com.unifei.hidroponia.listener;

import com.unifei.hidroponia.config.MqttProperties;
import com.unifei.hidroponia.service.MqttPublishService;
import com.unifei.hidroponia.service.RelatorioService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RelatorioMqttListener {
    
    private static final Logger logger = LoggerFactory.getLogger(RelatorioMqttListener.class);
    
    @Autowired
    private MqttClient mqttClient;
    
    @Autowired
    private MqttProperties mqttProperties;
    
    @Autowired
    private RelatorioService relatorioService;
    
    @Autowired
    private MqttPublishService mqttPublishService;
    
    @EventListener(ApplicationReadyEvent.class)
    public void iniciarListener() {
        try {
            String topic = mqttProperties.getTopics().getRelatorioRequest();
            logger.info("Iniciando listener para tópico de relatório: {}", topic);
            
            mqttClient.subscribe(topic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    logger.info("Solicitação de relatório recebida: {}", payload);
                    
                    String relatorio = relatorioService.gerarRelatorio(payload);
                    
                    if (relatorio != null) {
                        logger.info("Relatório gerado com sucesso");
                        mqttPublishService.publish(mqttProperties.getTopics().getRelatorioReply(), relatorio);
                    } else {
                        logger.error("Falha ao gerar relatório");
                    }
                }
            });
            
            logger.info("RelatorioMqttListener iniciado com sucesso");
            
        } catch (MqttException e) {
            logger.error("Erro ao iniciar RelatorioMqttListener: {}", e.getMessage());
        }
    }
}
