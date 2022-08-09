package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.exception.BusinessException;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * 1. Void metotlarda when() yapisi kullanilamamaktadir (thenThrows icin bile)
 * 2. Bunlarda thrpw kontrolu icin doThrow() metodu kullanilmasi gerekmektedir
 * @author korayk
 */
public class Test09MockingVoidMethods {
	
	private BookingService bookingService;
	private PaymentService paymentServiceMock; 
	private RoomService roomServiceMock;
	private BookingDAO bookingDaoMock;
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setUp() {
		paymentServiceMock = mock(PaymentService.class);
		roomServiceMock = mock(RoomService.class);
		bookingDaoMock = mock(BookingDAO.class);
		mailSenderMock = mock(MailSender.class);
		
		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDaoMock, mailSenderMock);
		
	}

	@Test
	void should_ThrowBusinessException_When_BookingRequestNotFound() {
		// given
		BookingRequest request = new BookingRequest("user12", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 3), 2, false);
		doThrow(BusinessException.class).when(mailSenderMock).sendBookingConfirmation(any());
			
		// when
		Executable executable = () -> bookingService.makeBooking(request);
		
		//then
		assertThrows(BusinessException.class, executable);
	}
}
