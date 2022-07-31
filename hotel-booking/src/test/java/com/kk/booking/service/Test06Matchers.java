package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;

import com.kk.booking.dao.BookingDAO;
import com.kk.booking.exception.BusinessException;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * 1. Mockladigimiz obje icin metoda parametre gecerken object icin any kullanarak ne gelirse gelsin bu sekilde davranmasini saglayabiliriz
 * 2. Primitive typelar icin anyDouble() gibi specific metotlar kullanilmasi gerekmektedir
 * 3. any() ile beraber sabit bir deger gondermek istersek any(), eq(400.0) oldugu gibi eq ile cagirmamiz gerekecektir
 * 4. String icin de any() kullanmaliyiz, cunku anyString() null ile match olmayacaktir
 * @author korayk
 */
public class Test06Matchers {
	
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
	void should_NotCompleteBooking_When_PriceTooHigh() {
		// given
		BookingRequest request = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 2, true);
//		when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);
		when(this.paymentServiceMock.pay(any(), eq(400.0))).thenThrow(BusinessException.class);
		
		// then
		Executable executable = () -> bookingService.makeBooking(request);
		
		//then
		assertThrows(BusinessException.class, executable);
	}
}
