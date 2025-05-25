package com.ypy.pyojbackendsubmitservice.mq;

import com.ypy.pyojbackendcommon.utils.RabbitMqInitializr;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JudgeMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        rabbitTemplate.convertAndSend(RabbitMqInitializr.EXCHANGE_NAME, RabbitMqInitializr.ROUTING_KEY, message);
    }
}
