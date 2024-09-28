package com.unifei.hidroponia.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {
    
    private String broker;
    private String username;
    private String password;
    private Topics topics = new Topics();
    
    public static class Topics {
        private String loginRequest;
        private String loginReply;
        private String ambienteStream;
        private String phSet;
        private String tempSet;
        private String relatorioRequest;
        private String relatorioReply;
        private String alerta;
        
        // Getters and Setters
        public String getLoginRequest() { return loginRequest; }
        public void setLoginRequest(String loginRequest) { this.loginRequest = loginRequest; }
        
        public String getLoginReply() { return loginReply; }
        public void setLoginReply(String loginReply) { this.loginReply = loginReply; }
        
        public String getAmbienteStream() { return ambienteStream; }
        public void setAmbienteStream(String ambienteStream) { this.ambienteStream = ambienteStream; }
        
        public String getPhSet() { return phSet; }
        public void setPhSet(String phSet) { this.phSet = phSet; }
        
        public String getTempSet() { return tempSet; }
        public void setTempSet(String tempSet) { this.tempSet = tempSet; }
        
        public String getRelatorioRequest() { return relatorioRequest; }
        public void setRelatorioRequest(String relatorioRequest) { this.relatorioRequest = relatorioRequest; }
        
        public String getRelatorioReply() { return relatorioReply; }
        public void setRelatorioReply(String relatorioReply) { this.relatorioReply = relatorioReply; }
        
        public String getAlerta() { return alerta; }
        public void setAlerta(String alerta) { this.alerta = alerta; }
    }
    
    // Main class getters and setters
    public String getBroker() { return broker; }
    public void setBroker(String broker) { this.broker = broker; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Topics getTopics() { return topics; }
    public void setTopics(Topics topics) { this.topics = topics; }
}
