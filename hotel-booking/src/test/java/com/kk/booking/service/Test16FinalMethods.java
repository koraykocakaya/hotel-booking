package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.Room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. Mockito yeni versiyonlarla birlikte eger inline'i dependency olarak verirsek final metotlarda sorun olmayacaktir,
 * 2. Bu sekilde diger metotlardaki gibi test edilebilmektedir
 * 3. Private metotlar mocklanamaz ve mocklanmamalidir da, bunun yerine onu kullanan public metotlarin mocklanmasi yeterli olacaktir
 */
public class Test16FinalMethods {
	
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
	void should_CountAvailablePlaces_When_OneRoomAvailable() {
		// given
		int total = 5;

		when(roomServiceMock.getAvailableRooms())
			.thenReturn(Arrays.asList(new Room("1", 5)));
		
		// when
		int result = bookingService.getAvailablePlaceCount();
		
		//then
		assertEquals(result, total);
	}

}
