package edu.bookingtour.commons.messaging;

/** Topology RabbitMQ — exchange topic, routing key theo sự kiện nghiệp vụ. */
public final class RabbitMQConstants {

    private RabbitMQConstants() {}

    public static final String BOOKING_EXCHANGE = "booking.topic";

    public static final String ROUTING_BOOKING_CREATED = "booking.created";
    public static final String ROUTING_PAYMENT_COMPLETED = "payment.completed";

    public static final String QUEUE_EMAIL_BOOKING = "email.booking.created";
    public static final String QUEUE_NOTIFICATION_BOOKING = "notification.booking.created";
    public static final String QUEUE_EMAIL_PAYMENT = "email.payment.completed";
    public static final String QUEUE_NOTIFICATION_PAYMENT = "notification.payment.completed";
}
