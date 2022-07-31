package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.Room;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. Mockladigimiz objeden cagrilan metotlarin neyi return edecegi setlenebilmektedir.
 * 2. When then yapisi kullanilarak roomServiceMock.getAvailableRooms() cagrildiginda roomList don seklinde yapi kurulabilmektedir (thenReturn)
 * 3. Bu sekilde custom bir inputa karsi custom bir output cagiracak sekilde ayarlanabilmektedir
 * @author korayk
 */
public class Test03CustomReturnValues {
	
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
		
		// then
		int result = bookingService.getAvailablePlaceCount();
		
		//then
		assertEquals(result, total);
	}
	
	@Test
	void should_CountAvailablePlaces_When_MultipleRoomsAvailable() {
		// given
		List<Room> roomList = Arrays.asList(
			new Room("1", 3),
			new Room("1", 2),
			new Room("1", 1),
			new Room("1", 4));
		
		when(roomServiceMock.getAvailableRooms())
			.thenReturn(roomList);
		
		int total = 10;
		// then
		int result = bookingService.getAvailablePlaceCount();
		
		//then
		assertEquals(result, total);
	}

}
