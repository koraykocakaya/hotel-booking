package com.kk.booking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;

import com.kk.booking.CurrencyConverter;
import com.kk.booking.dao.BookingDAO;
import com.kk.booking.model.BookingRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 1. Static metotlari mocklamak icin oncelikle dependency olarak mockito-core degil, mockito-inline kullanmamiz gerekmektedir
 * 2. Sadece ilgili yerde try with ile tanimlamak temporary tanimlamak acisindan saglikli olacaktir
 * 3. Sonrasinda mock method call benzer sekilde when, thenReturn ile ilgili mocklama yapilabilmekte
 * @author korayk
 */
@ExtendWith(MockitoExtension.class)
public class Test14StaticMethods {
	
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
	void should_CalculateCorrectPrice() {
		
		try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
			// given
			BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 5), 3, false);
			double expected = 600.0;

			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(600.0);
			
			// when
			double returnVal = bookingService.calculatePriceEuro(bookingRequest);
			
			// then
			assertEquals(returnVal, expected);
		}
		
	}
	
	
}
