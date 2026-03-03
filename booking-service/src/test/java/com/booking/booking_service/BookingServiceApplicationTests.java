package com.booking.booking_service;

import com.booking.booking_service.messaging.BookingEventPublisher;
import com.booking.booking_service.model.Booking;
import com.booking.booking_service.model.BookingStatus;
import com.booking.booking_service.repository.BookingRepository;
import com.booking.booking_service.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceApplicationTests {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingEventPublisher bookingEventPublisher;

    @InjectMocks
    private BookingService bookingService;

    private Booking testBooking;

    @BeforeEach
    void setUp() {
        testBooking = new Booking();
        testBooking.setId(1L);
        testBooking.setUserId(1L);
        testBooking.setResourceId(1L);
        testBooking.setStartTime(LocalDateTime.now());
        testBooking.setEndTime(LocalDateTime.now().plusHours(2));
        testBooking.setStatus(BookingStatus.PENDING);
    }

    @Test
    void createBooking_ShouldSaveAndPublishEvent() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);
        Booking created = bookingService.createBooking(testBooking);
        assertNotNull(created);
        assertEquals(BookingStatus.PENDING, created.getStatus());
        verify(bookingRepository, times(1)).save(testBooking);
        verify(bookingEventPublisher, times(1)).publishBookingCreated(testBooking);
    }

    @Test
    void getAllBookings_ShouldReturnAllBookings() {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(testBooking));
        List<Booking> bookings = bookingService.getAllBookings();
        assertEquals(1, bookings.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void confirmBooking_ShouldChangeStatusToConfirmed() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);
        Booking confirmed = bookingService.confirmBooking(1L);
        assertEquals(BookingStatus.CONFIRMED, confirmed.getStatus());
    }

    @Test
    void cancelBooking_ShouldChangeStatusToCancelled() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(testBooking);
        Booking cancelled = bookingService.cancelBooking(1L);
        assertEquals(BookingStatus.CANCELLED, cancelled.getStatus());
    }

    @Test
    void getBookingById_ShouldThrowException_WhenNotFound() {
        when(bookingRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookingService.getBookingById(99L));
    }
}