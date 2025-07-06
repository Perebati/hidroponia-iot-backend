package com.unifei.hidroponia.Events;

import org.eclipse.paho.client.mqttv3.*;

public class alertaEvento {

    private static final String BROKER = "ssl://b-f3ef6eef-9bc8-426e-bb01-87b30b3421ed-1.mq.sa-east-1.amazonaws.com:8883";
    private static final String TOPIC_OUT = "10/alerta";
    private static final String USERNAME = "projetosistemas";
    private static final String PASSWORD = "projetosistemas";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/trabSD";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";

    public static void main(Integer tipo, String message) {
        try {
            System.out.println("Enviando Alerta...");
            MqttClient mqttClient = new MqttClient(BROKER, MqttClient.generateClientId());
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName(USERNAME);
            mqttConnectOptions.setPassword(PASSWORD.toCharArray());

            mqttClient.connect(mqttConnectOptions);
            mqttClient.subscribe(TOPIC_OUT);

            String retorno = "{\"err_id\": \"" + tipo + "\", \"err_desc\": \"" + message + "\" }";
            MqttMessage mqttMessageReturn = new MqttMessage(retorno.getBytes());
            mqttClient.publish(TOPIC_OUT, mqttMessageReturn);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}