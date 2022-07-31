package com.kk.booking.service;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. Elimizdeki mock objenin behaviorini verify metodu ile gozlemleyebiliriz, mesela ilgili mockun x metodu su parametrelerle cagrildi mi gibi kontrol edebiliriz
 * 2. Bu ornekte de paymentServiceMock pay metodu request ve 600 parametreleri ile kac defa cagrildigini kontrol edebilmekteyiz
 * 3. Ek olarak verifyNoMoreInteractions ile de daha sonra tekrar kullanilmadigi kontrol edilebilmektedir
 * 4. Ayrica verify(mock, never()) ile de istenilen metodun hic bir parametre ile tekrar cagrilmadigina emin olabiliriz 
 * @author korayk
 */
public class Test07VerifyingBehaviour {
	
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
	void should_InvokePayment_When_Prepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, true);
		
		// when
		bookingService.makeBooking(bookingRequest);
		
		// then
		verify(paymentServiceMock, times(1))
			.pay(bookingRequest, 600.0);
		
		verifyNoMoreInteractions(paymentServiceMock);
	}
	
	@Test
	void should_NotInvokePayment_When_NotPrepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, false);
		
		// when
		bookingService.makeBooking(bookingRequest);
		
		// then
		verify(paymentServiceMock, never())
			.pay(any(), anyDouble());
	}

}
