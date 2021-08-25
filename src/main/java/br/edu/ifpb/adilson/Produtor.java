package br.edu.ifpb.adilson;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// Classe responsável por enviar itens à fila

public class Produtor {

    public static void main(String[] args) {
        // Criação de uma factory de conexão, responsável por criar as conexões
        ConnectionFactory connectionFactory = new ConnectionFactory();

        // Localização do gestor da fila (Queue Manager)
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("mqadmin");
        connectionFactory.setPassword("586467");

        String QUEUE_NAME = "filaHello";

        try (
                // Criação de uma conexão
                Connection connection = connectionFactory.newConnection();

                // Criando um canal de conexão
                Channel channel = connection.createChannel()

                //Esse corpo especifica o envio da mensagem para a fila

                /**
                 * Declaração da fila. Se não existir ainda no Queue Manager, será criada.
                 * Se já existir e foi criada com os mesmos parâmetros, pega a referência
                 * da fila. Se foi criada com parâmetros diferentes, lança exceção.
                 */
        ){
            String msg = "Hello World!";

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("Mensagem enviada: " + msg);
        } catch (IOException | TimeoutException err) {
            System.out.println(err);
        }

    }
}
