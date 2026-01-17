import adapters.StripeAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import valueobjects.Money;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class StripeAdapterTest {
	private StripeAdapter adapter;

	@BeforeEach
	void setUp() {
		adapter = new StripeAdapter("sk_test_123456");
	}

	@Test
	@DisplayName("TC04: Autorizar pago normal")
	void testAutorizarPagoNormal() {
		Money monto = new Money(new BigDecimal("150.00"), "USD");
		String referencia = "RES-STRIPE-1";
		String refStripe = adapter.autorizar(monto, referencia);
		assertNotNull(refStripe);
		assertTrue(refStripe.startsWith("pi_"));
		assertEquals(27, refStripe.length()); // pi_ + 24 chars
	}

	@Test
	@DisplayName("TC05: Autorizar con monto null")
	void testAutorizarMontoNull() {
		String referencia = "RES-STRIPE-2";
		Exception ex = assertThrows(IllegalArgumentException.class, () -> adapter.autorizar(null, referencia));
		assertEquals("El monto no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC06: Autorizar con referencia null/vacía")
	void testAutorizarReferenciaNullVacia() {
		Money monto = new Money(new BigDecimal("50.00"), "USD");
		Exception ex1 = assertThrows(IllegalArgumentException.class, () -> adapter.autorizar(monto, null));
		assertEquals("La referencia no puede ser null o vacía", ex1.getMessage());
		Exception ex2 = assertThrows(IllegalArgumentException.class, () -> adapter.autorizar(monto, "   "));
		assertEquals("La referencia no puede ser null o vacía", ex2.getMessage());
	}

	@Test
	@DisplayName("TC09: Capturar pago normal")
	void testCapturarPagoNormal() {
		assertDoesNotThrow(() -> adapter.capturar("pi_123456789012345678901234"));
	}

	@Test
	@DisplayName("TC10: Capturar con referencia null/vacía")
	void testCapturarReferenciaNullVacia() {
		Exception ex1 = assertThrows(IllegalArgumentException.class, () -> adapter.capturar(null));
		assertEquals("La referencia no puede ser null o vacía", ex1.getMessage());
		Exception ex2 = assertThrows(IllegalArgumentException.class, () -> adapter.capturar("   "));
		assertEquals("La referencia no puede ser null o vacía", ex2.getMessage());
	}

	@Test
	@DisplayName("TC14: Reembolsar pago normal")
	void testReembolsarPagoNormal() {
		Money monto = new Money(new BigDecimal("30.00"), "USD");
		assertDoesNotThrow(() -> adapter.reembolsar("pi_123456789012345678901234", monto));
	}

	@Test
	@DisplayName("TC15: Reembolsar con referencia null/vacía")
	void testReembolsarReferenciaNullVacia() {
		Money monto = new Money(new BigDecimal("30.00"), "USD");
		Exception ex1 = assertThrows(IllegalArgumentException.class, () -> adapter.reembolsar(null, monto));
		assertEquals("La referencia no puede ser null o vacía", ex1.getMessage());
		Exception ex2 = assertThrows(IllegalArgumentException.class, () -> adapter.reembolsar("   ", monto));
		assertEquals("La referencia no puede ser null o vacía", ex2.getMessage());
	}

	@Test
	@DisplayName("TC16: Reembolsar con monto null")
	void testReembolsarMontoNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> adapter.reembolsar("pi_123456789012345678901234", null));
		assertEquals("El monto no puede ser null", ex.getMessage());
	}
}
