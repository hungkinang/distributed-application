package edu.bookingtour.svc.email.config;

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
    Queue emailBookingQueue() {
        return new Queue(RabbitMQConstants.QUEUE_EMAIL_BOOKING, true);
    }

    @Bean
    Queue emailPaymentQueue() {
        return new Queue(RabbitMQConstants.QUEUE_EMAIL_PAYMENT, true);
    }

    @Bean
    Binding emailBookingBinding(Queue emailBookingQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(emailBookingQueue)
                .to(bookingExchange)
                .with(RabbitMQConstants.ROUTING_BOOKING_CREATED);
    }

    @Bean
    Binding emailPaymentBinding(Queue emailPaymentQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(emailPaymentQueue)
                .to(bookingExchange)
                .with(RabbitMQConstants.ROUTING_PAYMENT_COMPLETED);
    }

    @Bean
    MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
