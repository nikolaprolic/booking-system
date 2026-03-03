package com.booking.notification_service.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    @RabbitListener(queues = "booking.queue")
    public void handleBookingCreated(String message) {
        logger.info("Notification received: {}", message);
        logger.info("Sending notification for: {}", message);
    }
}
