package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * 1. verifydeki gibi gecen parametreyi belirtmek yerine geceni yakalayip elimizdeki ile kontrol etmek icin ArgumentCaptor kullanmaktayiz
 * 2. Burada ilgili degeri capture() metodu ile yakalayip istenilen degeri sonrasinda kontrol edebilmekteyiz
 * 3. Coklu oldugu durumda ilgili degerleri getAllValue ile alip her biri icin ayrica kontrol edebiliriz 
 * @author korayk
 */
public class Test10ArgumentCaptors {
	
	private BookingService bookingService;
	private PaymentService paymentServiceMock; 
	private RoomService roomServiceMock;
	private BookingDAO bookingDaoMock;
	private MailSender mailSenderMock;
	private ArgumentCaptor<Double> doubleCaptor;
	
	@BeforeEach
	void setUp() {
		paymentServiceMock = mock(PaymentService.class);
		roomServiceMock = mock(RoomService.class);
		bookingDaoMock = mock(BookingDAO.class);
		mailSenderMock = mock(MailSender.class);
		
		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDaoMock, mailSenderMock);
		doubleCaptor = ArgumentCaptor.forClass(Double.class);
	}
	
	@Test
	void should_PayCorrectPrice_When_InputOK() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, true);
		
		// when
		bookingService.makeBooking(bookingRequest);
		
		// then
		verify(paymentServiceMock, times(1))
			.pay(eq(bookingRequest), doubleCaptor.capture());

		Double capturedValue = doubleCaptor.getValue();
		
		assertEquals(capturedValue, 600.0);
		
		
	}
	
	@Test
	void should_PayCorrectPrices_When_MultipleCalls() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, true);
		BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 4, true);
		
		// when
		bookingService.makeBooking(bookingRequest);
		bookingService.makeBooking(bookingRequest2);
		
		// then
		verify(paymentServiceMock, times(2))
			.pay(any(), doubleCaptor.capture());

		List<Double> capturedValueList = doubleCaptor.getAllValues();
		
		assertAll(
				() -> assertEquals(capturedValueList.get(0), 600.0),
				() -> assertEquals(capturedValueList.get(1), 800.0) 
		);
	}
	
}
