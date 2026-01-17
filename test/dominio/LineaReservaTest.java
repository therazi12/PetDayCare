import dominio.LineaReserva;
import interfaces.IPagoProcessor;
import interfaces.IPricingStrategy;
import interfaces.IServicioBase;
import valueobjects.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class LineaReservaTest {
	private LineaReserva linea;
	private IServicioBase servicio;

	@BeforeEach
	void setUp() {
		servicio = new IServicioBase() {
			public String obtenerNombre() { return "Guardería"; }
			public Money calcularPrecio(valueobjects.Periodo p, java.util.List<String> o, interfaces.IPricingStrategy pr) { return Money.usd(100); }
		};
		linea = new LineaReserva("L1", servicio, Money.usd(100));
	}

	@Test
	@DisplayName("TC01: establecerPrecio con pricing válido actualiza el precio")
	void testEstablecerPrecioConPricing() {
		IPricingStrategy pricing = (serv, per, opc) -> Money.usd(200);
		linea.establecerPrecio(pricing);
		assertEquals(Money.usd(200), linea.getPrecio());
	}

	@Test
	@DisplayName("TC02: establecerPrecio con pricing null lanza excepción")
	void testEstablecerPrecioNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> linea.establecerPrecio((IPricingStrategy) null));
		assertEquals("La estrategia de pricing no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC03: establecerPrecio con Money válido actualiza el precio")
	void testEstablecerPrecioMoneyValido() {
		linea.establecerPrecio(Money.usd(150));
		assertEquals(Money.usd(150), linea.getPrecio());
	}

	@Test
	@DisplayName("TC04: establecerPrecio con Money null lanza excepción")
	void testEstablecerPrecioMoneyNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> linea.establecerPrecio((Money) null));
		assertEquals("El precio no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC05: reembolsar con proc válido ejecuta reembolso")
	void testReembolsarProcValido() {
		AtomicBoolean called = new AtomicBoolean(false);
		IPagoProcessor proc = new IPagoProcessor() {
			public String autorizar(Money m, String r) { return null; }
			public void capturar(String r) {}
			public void reembolsar(String r, Money m) { called.set(true); }
		};
		linea.reembolsar(proc);
		assertTrue(called.get());
	}

	@Test
	@DisplayName("TC06: reembolsar con proc null lanza excepción")
	void testReembolsarProcNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> linea.reembolsar(null));
		assertEquals("El procesador de pagos no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC01: capturar con proc válido ejecuta capturar")
	void testCapturarProcValido() {
		AtomicBoolean called = new AtomicBoolean(false);
		IPagoProcessor proc = new IPagoProcessor() {
			public String autorizar(Money m, String r) { return null; }
			public void capturar(String r) { called.set(true); }
			public void reembolsar(String r, Money m) {}
		};
		linea.capturar(proc);
		assertTrue(called.get());
	}

	@Test
	@DisplayName("TC02: capturar con proc null lanza excepción")
	void testCapturarProcNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> linea.capturar(null));
		assertEquals("El procesador de pagos no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC03: capturar con proc que lanza excepción propaga el error")
	void testCapturarProcLanzaExcepcion() {
		IPagoProcessor proc = new IPagoProcessor() {
			public String autorizar(Money m, String r) { return null; }
			public void capturar(String r) { throw new RuntimeException("Error en capturar"); }
			public void reembolsar(String r, Money m) {}
		};
		RuntimeException ex = assertThrows(RuntimeException.class, () -> linea.capturar(proc));
		assertEquals("Error en capturar", ex.getMessage());
	}

	@Test
	@DisplayName("TC04: no se puede crear LineaReserva con id vacío o null")
	void testConstructorIdInvalido() {
		Exception ex1 = assertThrows(IllegalArgumentException.class, () -> new LineaReserva(null, servicio, Money.usd(10)));
		assertEquals("El ID no puede ser null o vacío", ex1.getMessage());
		Exception ex2 = assertThrows(IllegalArgumentException.class, () -> new LineaReserva("   ", servicio, Money.usd(10)));
		assertEquals("El ID no puede ser null o vacío", ex2.getMessage());
	}

	@Test
	@DisplayName("TC05: capturar con id con caracteres especiales funciona")
	void testCapturarIdEspeciales() {
		LineaReserva lineaEspecial = new LineaReserva("L-!@#%$*()_+", servicio, Money.usd(10));
		AtomicBoolean called = new AtomicBoolean(false);
		IPagoProcessor proc = new IPagoProcessor() {
			public String autorizar(Money m, String r) { return null; }
			public void capturar(String r) { called.set(r.equals("linea-L-!@#%$*()_+")); }
			public void reembolsar(String r, Money m) {}
		};
		lineaEspecial.capturar(proc);
		assertTrue(called.get());
	}
}
