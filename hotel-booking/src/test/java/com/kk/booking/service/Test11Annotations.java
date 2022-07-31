package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 1. Mocklari her bir test oncesi mocklayip ana objeye setlemek yerine ilgili islemleri annotationlar ile otomatize edebiliriz.
 * 2. Mocklanacak ve captor objeler icin @Mock (veya @Spy) ve @Captor, bunlari iceren ana obje icin de @InjectMocks kullanmamiz yeterli olacaktir
 * 3. Bu sekilde kullandiklarimiz icin classi da @ExtendWith(MockitoExtension.class) seklinde isaretlemek gerekmektedir
 * 4. Koddaki repetitiondan dolayi onerilen kullanim bu sekildedir    
 * @author korayk
 */
@ExtendWith(MockitoExtension.class)
public class Test11Annotations {
	
	@InjectMocks
	private BookingService bookingService;
	
	@Mock
	private PaymentService paymentServiceMock; 
	
	@Mock
	private RoomService roomServiceMock;
	
	@Mock
	private BookingDAO bookingDaoMock;
	
	@Mock
	private MailSender mailSenderMock;

	@Captor
	private ArgumentCaptor<Double> doubleCaptor;
	
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
