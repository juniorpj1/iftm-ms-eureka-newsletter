package com.iftm.trabalho.mensages;

import com.iftm.trabalho.models.dtos.LogDTO;
import com.iftm.trabalho.models.dtos.NewsDTO;
import com.iftm.trabalho.models.dtos.PostDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqSendLog {

    @Value("${newsletter.rabbitmq.exchange}")
    private String exchange;

    @Value("${newsletter.rabbitmq.routingKey}")
    private String routingKey;

    @Value("${newsletter.rabbitmq.queue}")
    private String queue;

    public final RabbitTemplate rabbitTemplate;

    public RabbitMqSendLog(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendLog(LogDTO newsDTO) {
        rabbitTemplate.execute(channel -> {
            channel.exchangeDeclare(exchange, "direct", true);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, routingKey);
            return null;
        });
        rabbitTemplate.convertAndSend(exchange, routingKey, newsDTO);
    }

}
