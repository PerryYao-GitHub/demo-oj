package com.ypy.pyojbackendcommon.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitMqInitializr {

    public static final String EXCHANGE_NAME = "pyoj-backend-submit-judge-exchange";

    public static final String ROUTING_KEY = "pyoj-backend-submit-judge-routing-key";

    public static final String QUEUE_NAME = "pyoj-backend-submit-judge-queue";

    public static void doInit() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        } catch (Exception e) {
            log.error("MQ fail to start");
        }
        log.info("MQ started successfully");
    }
}
