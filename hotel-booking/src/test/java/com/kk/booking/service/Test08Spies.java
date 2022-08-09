package com.kk.booking.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 1. Mock default value return ederken, spy actual value donecektir
 * 2. Buradaki ornekte de save metodu normak sekilde calisti ve ilgili String kaydi return etti, mock olsaydi null donecekti
 * 3. spy'da da mocktaki gibi when then mantigi var ancak burada doReturn(bookingRequest).when(bookingDaoMock).get(bookingId); seklinde kullanilmakta
 * 4. Boylece ilgili metodu calistirdigimizda null donmeyecektir, bu ekleme yapilmadiginda mocktan farkli olarak null donecekti
 * @author korayk
 */
public class Test08Spies {
	
	private BookingService bookingService;
	private PaymentService paymentServiceMock; 
	private RoomService roomServiceMock;
	private BookingDAO bookingDaoMock;
	private MailSender mailSenderMock;
	
	@BeforeEach
	void setUp() {
		paymentServiceMock = mock(PaymentService.class);
		roomServiceMock = mock(RoomService.class);
		bookingDaoMock = spy(BookingDAO.class);
		mailSenderMock = mock(MailSender.class);
		
		System.out.println(bookingDaoMock.save(new BookingRequest(null, null, null, 0, false)));
		bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDaoMock, mailSenderMock);
	}
	
	@Test
	void should_MakeBooking_When_InputOK() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, true);
		
		// when
		String bookingId = bookingService.makeBooking(bookingRequest);
		
		// then
		verify(bookingDaoMock)
			.save(bookingRequest);
		System.out.println("Booking ID: " + bookingId);
	}
	
	@Test
	void should_CancelBooking_When_InputOK() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, true);
		bookingRequest.setRoomId("1.3");
		String bookingId = "1";
		
		doReturn(bookingRequest).when(bookingDaoMock).get(bookingId);
		
		// when
		bookingService.cancelBooking(bookingId);
		
		// then

	}
	
}
