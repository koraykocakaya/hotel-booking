package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.kk.booking.dao.BookingDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. Mockladigimiz objelerde cagirdigimiz metotlar default value return edecektir. int icin 0, Object icin null gibi
 * @author korayk
 */
public class Test02DefaultReturnValues {
	
	private BookingService bookingService;
	
	@BeforeEach
	void setUp() {
		PaymentService paymentServiceMock = mock(PaymentService.class);
		RoomService roomServiceMock = mock(RoomService.class);
		BookingDAO bookingDaoMock = mock(BookingDAO.class);
		MailSender mailSenderMock = mock(MailSender.class);
		
		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDaoMock, mailSenderMock);
		
		System.out.println("Default List: " + roomServiceMock.getAvailableRooms());
		System.out.println("Default object: " + roomServiceMock.findAvailableRoomId(null));
		System.out.println("Default int value: " + roomServiceMock.getRoomCount());
	}
	
	@Test
	void should_CountAvailablePlaces() {
		// given
		int expected = 0;
		
		// then
		int result = bookingService.getAvailablePlaceCount();
		
		// when
		assertEquals(result, expected);
		
	}

}
