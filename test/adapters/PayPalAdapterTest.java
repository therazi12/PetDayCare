import adapters.PayPalAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import valueobjects.Money;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PayPalAdapterTest {
	private PayPalAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new PayPalAdapter("apikey123456", "secret987654");
	}

	@Test
	@DisplayName("TC01: Autorizar pago normal")
	void testAutorizarPagoNormal() {
		Money monto = new Money(new BigDecimal("100.00"), "USD");
		String referencia = "RES-123";
		String refPayPal = adapter.autorizar(monto, referencia);
		assertNotNull(refPayPal);
		assertTrue(refPayPal.startsWith("PAYPAL-"));
		assertEquals(15, refPayPal.length()); // PAYPAL-XXXXXXXX
	}

	@Test
	@DisplayName("TC02: Autorizar con monto null")
	void testAutorizarMontoNull() {
		String referencia = "RES-123";
		Exception ex = assertThrows(IllegalArgumentException.class, () -> adapter.autorizar(null, referencia));
		assertEquals("El monto no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC03: Autorizar con referencia null/vacía")
	void testAutorizarReferenciaNullVacia() {
		Money monto = new Money(new BigDecimal("50.00"), "USD");
		Exception ex1 = assertThrows(IllegalArgumentException.class, () -> adapter.autorizar(monto, null));
		assertEquals("La referencia no puede ser null o vacía", ex1.getMessage());
		Exception ex2 = assertThrows(IllegalArgumentException.class, () -> adapter.autorizar(monto, "   "));
		assertEquals("La referencia no puede ser null o vacía", ex2.getMessage());
	}

	@Test
	@DisplayName("TC07: Capturar pago normal")
	void testCapturarPagoNormal() {
		assertDoesNotThrow(() -> adapter.capturar("PAYPAL-12345678"));
	}

	@Test
	@DisplayName("TC08: Capturar con referencia null/vacía")
	void testCapturarReferenciaNullVacia() {
		Exception ex1 = assertThrows(IllegalArgumentException.class, () -> adapter.capturar(null));
		assertEquals("La referencia no puede ser null o vacía", ex1.getMessage());
		Exception ex2 = assertThrows(IllegalArgumentException.class, () -> adapter.capturar("   "));
		assertEquals("La referencia no puede ser null o vacía", ex2.getMessage());
	}

	@Test
	@DisplayName("TC11: Reembolsar pago normal")
	void testReembolsarPagoNormal() {
		Money monto = new Money(new BigDecimal("20.00"), "USD");
		assertDoesNotThrow(() -> adapter.reembolsar("PAYPAL-12345678", monto));
	}

	@Test
	@DisplayName("TC12: Reembolsar con referencia null/vacía")
	void testReembolsarReferenciaNullVacia() {
		Money monto = new Money(new BigDecimal("20.00"), "USD");
		Exception ex1 = assertThrows(IllegalArgumentException.class, () -> adapter.reembolsar(null, monto));
		assertEquals("La referencia no puede ser null o vacía", ex1.getMessage());
		Exception ex2 = assertThrows(IllegalArgumentException.class, () -> adapter.reembolsar("   ", monto));
		assertEquals("La referencia no puede ser null o vacía", ex2.getMessage());
	}

	@Test
	@DisplayName("TC13: Reembolsar con monto null")
	void testReembolsarMontoNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> adapter.reembolsar("PAYPAL-12345678", null));
		assertEquals("El monto no puede ser null", ex.getMessage());
	}
}
