package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.Room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. Birden cok thenReturn ekleyerek metodun birden cok callu handle edilebilmektedir
 * 2. Yani ilk cagirdiginda farkli, ikinci vs cagirdiginda farkli outputlar donebilecek sekilde ayarlanabilmektedir
 * @author korayk
 */
public class Test04MultipleThenReturnCalls {
	
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
		int firstTotal = 5;
		int secondTotal = 4;
		int thirdTotal = 2;

		when(roomServiceMock.getAvailableRooms())
			.thenReturn(Arrays.asList(new Room("1", 5)))
			.thenReturn(Arrays.asList(new Room("2", 4)))
			.thenReturn(Arrays.asList(new Room("3", 2)));
		
		// then
		int resultFirstCall = bookingService.getAvailablePlaceCount();
		int resultSecondCall = bookingService.getAvailablePlaceCount();
		int resultThirdCall = bookingService.getAvailablePlaceCount();
		
		//then
		assertAll(
			() -> assertEquals(resultFirstCall, firstTotal),
			() -> assertEquals(resultSecondCall, secondTotal),
			() -> assertEquals(resultThirdCall, thirdTotal)
		);
	}
	
}
