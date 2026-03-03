package com.booking.booking_service.messaging;

import com.booking.booking_service.model.Booking;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public static final String BOOKING_EXCHANGE = "booking.exchange";
    public static final String BOOKING_ROUTING_KEY = "booking.created";

    public void publishBookingCreated(Booking booking) {
        String message = "Booking created: ID=" + booking.getId() +
                ", UserID=" + booking.getUserId() +
                ", ResourceID=" + booking.getResourceId();
        rabbitTemplate.convertAndSend(BOOKING_EXCHANGE, BOOKING_ROUTING_KEY, message);
    }
}
