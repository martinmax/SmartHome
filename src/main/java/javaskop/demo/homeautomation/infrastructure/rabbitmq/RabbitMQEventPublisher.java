package javaskop.demo.homeautomation.infrastructure.rabbitmq;

import javaskop.demo.homeautomation.core.event.Event;
import javaskop.demo.homeautomation.core.event.EventPublisher;
import javaskop.demo.homeautomation.core.exception.SmartHomeException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by martin.ilievski on 11/20/2015.
 */
@Component
public class RabbitMQEventPublisher implements EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin rabbitAdmin;
    private TopicExchange exchange;

    @PostConstruct
    void init() {
        try {
            rabbitTemplate.setMessageConverter(new JsonMessageConverter());
            exchange = new TopicExchange("homeAutomation");
            rabbitAdmin.declareExchange(exchange);
        } catch (Exception e) {
            throw new SmartHomeException("Could not initalize RabbitMQEventPublisher, probably could not connect to RabbitMQ server",e);
        }
    }

    @Override
    public void publish(Event event) {
        try {
            Queue queue = new Queue(event.getQueue());
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(queue.getName()));
            rabbitTemplate.convertAndSend(exchange.getName(), event.getQueue(), event.getData());
        } catch (Exception e) {
            throw new SmartHomeException("Could not send event " + event.toString(), e);
        }
    }
}
