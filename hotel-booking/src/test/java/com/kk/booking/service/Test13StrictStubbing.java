package com.kk.booking.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

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
 * 1. MockitoExtension ile birlikte Strict stubbing de gelmektedir, bu ozellik gereksiz yere when kullandigimizda exception firlatmaktadir
 * 2. Yani biz mockun girmeyecegi metodun behaviorunu verdigimizde onu yakalayacak ve hata firlatacaktir.
 * 3. Bu sayede ozellikle copy-pastelerden dolayi gereksiz kodlari silmemizi sagalyacaktir
 * 4. Anlik hata atmamasi icin basina lenient() vermemiz yeterli olacaktir (Yoruma almak yerine bu sekilde bir sistem uygulayabiliriz)
 * @author korayk
 */
@ExtendWith(MockitoExtension.class)
public class Test13StrictStubbing {
	
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
	void should_InvokePayment_When_Prepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, false);
		
		lenient().when(paymentServiceMock.pay(any(), anyDouble())).thenReturn("1");
		
		// when
		bookingService.makeBooking(bookingRequest);
		
		// then
		// no exceptions
	}
	
	
}
