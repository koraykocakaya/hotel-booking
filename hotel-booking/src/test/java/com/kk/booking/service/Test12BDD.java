package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDate;
import java.util.Arrays;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.BookingRequest;
import com.kk.booking.model.Room;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 1. Aslinda bizim kullandigimiz given, when, then yapisi Behavioral Driven Development (BDD) isaret etmektedir
 * 2. Ancak metot adlari given'da when gibi kafa karistirici olabilmektedir, bunun yerine BDDMockito kullandigimizda
 * 3. given, willReturn ve then gibi metotlari kullanarak metotlari BDD'ye uygun sekilde kullanabilmemzi saglanmaktadir 
 * @author korayk
 */
@ExtendWith(MockitoExtension.class)
public class Test12BDD {
	
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
	void should_CountAvailablePlaces_When_OneRoomAvailable() {
		// given
		int total = 5;

		given(roomServiceMock.getAvailableRooms())
			.willReturn(Arrays.asList(new Room("1", 5)));
		
		// then
		int result = bookingService.getAvailablePlaceCount();
		
		//then
		assertEquals(result, total);
	}
	
	@Test
	void should_InvokePayment_When_Prepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, true);
		
		// when
		bookingService.makeBooking(bookingRequest);
		
		// then
		then(paymentServiceMock).should(times(1))
			.pay(bookingRequest, 600.0);
		
		verifyNoMoreInteractions(paymentServiceMock);
	}
	
	
}
