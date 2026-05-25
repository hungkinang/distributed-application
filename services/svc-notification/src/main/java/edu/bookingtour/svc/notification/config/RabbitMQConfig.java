package edu.bookingtour.svc.notification.config;

import edu.bookingtour.commons.messaging.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    TopicExchange bookingExchange() {
        return new TopicExchange(RabbitMQConstants.BOOKING_EXCHANGE, true, false);
    }

    @Bean
    Queue notificationBookingQueue() {
        return new Queue(RabbitMQConstants.QUEUE_NOTIFICATION_BOOKING, true);
    }

    @Bean
    Binding notificationBookingBinding(Queue notificationBookingQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(notificationBookingQueue)
                .to(bookingExchange)
                .with(RabbitMQConstants.ROUTING_BOOKING_CREATED);
    }

    @Bean
    MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
