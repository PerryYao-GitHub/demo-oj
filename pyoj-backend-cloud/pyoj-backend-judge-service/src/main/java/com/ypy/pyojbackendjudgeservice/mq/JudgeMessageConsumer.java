package com.ypy.pyojbackendjudgeservice.mq;

import com.rabbitmq.client.Channel;
import com.ypy.pyojbackendcommon.utils.RabbitMqInitializr;
import com.ypy.pyojbackendjudgeservice.service.JudgeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.handler.annotation.Header;

import javax.annotation.Resource;

@ComponentScan
@Slf4j
public class JudgeMessageConsumer {

    @Resource
    private JudgeService judgeService;

    @SneakyThrows
    @RabbitListener(queues = {RabbitMqInitializr.QUEUE_NAME}, ackMode = "MANUAL")
    public void receive(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        long submitId = Long.parseLong(message);
        try {
            judgeService.doJudge(submitId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
