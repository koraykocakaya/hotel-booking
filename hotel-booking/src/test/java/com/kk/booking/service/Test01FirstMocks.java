package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. Test ettigimiz servislerin icinde kullanacagimiz dependencyleri mocklayarak temp bir obje olusturabiliyoruz
 * @author korayk
 */
public class Test01FirstMocks {
	
	private BookingService bookingService;
	
	@BeforeEach
	void setUp() {
		PaymentService paymentServiceMock = mock(PaymentService.class);
		RoomService roomServiceMock = mock(RoomService.class);
		BookingDAO bookingDaoMock = mock(BookingDAO.class);
		MailSender mailSenderMock = mock(MailSender.class);
		
		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDaoMock, mailSenderMock);
	}
	
	@Test
	void should_CalculateCorrectPrice_When_CorrectInput() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, false);
		double expected = 3 * 4 * 50.0;
		
		// then
		double result = bookingService.calculatePrice(bookingRequest);
		
		// when
		assertEquals(result, expected);
	}

}
