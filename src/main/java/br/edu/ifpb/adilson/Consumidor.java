package br.edu.ifpb.adilson;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumidor {

    public static void main(String[] args) {
        String QUEUE_NAME = "filaHello";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("586467");

        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Definindo função de callback
            DeliverCallback callback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody());
                System.out.println("Recebi a mensagem: " + msg);
            };

            // Consome da fila
            channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
            System.out.println("Continuarei executando outras atividades enquanto não chega mensagem...");

        } catch (IOException | TimeoutException err) {
            System.out.println(err);
        }
    }
}
