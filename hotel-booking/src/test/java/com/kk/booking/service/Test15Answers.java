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
 * 1. Static metotlar icin behavioru da answer yardimiyla ayarlayabilmekteyiz.
 * 2. Bu ornekte de oldugu gibi ilgili metodun ilk parametresini alip bunu 0.8 ile return eder bir mock yaratabildik
 * @author korayk
 */
@ExtendWith(MockitoExtension.class)
public class Test15Answers {
	
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
			double expected = 480.0;

			mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble()))
							.thenAnswer(inv -> (double) inv.getArgument(0) * 0.8);
			
			// when
			double returnVal = bookingService.calculatePriceEuro(bookingRequest);
			
			// then
			assertEquals(returnVal, expected);
		}
		
	}
	
	
}
