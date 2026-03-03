package com.booking.booking_service.service;

import com.booking.booking_service.messaging.BookingEventPublisher;
import com.booking.booking_service.model.Booking;
import com.booking.booking_service.model.BookingStatus;
import com.booking.booking_service.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingEventPublisher bookingEventPublisher;

    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.PENDING);
        Booking saved = bookingRepository.save(booking);
        bookingEventPublisher.publishBookingCreated(saved);
        return saved;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Booking confirmBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}
