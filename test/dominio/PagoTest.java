import dominio.Pago;
import interfaces.IPagoProcessor;
import valueobjects.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PagoTest {

	private Pago pago;
	private IPagoProcessor processor;
	private Money monto;

	@BeforeEach
	void setUp() {
		monto = Mockito.mock(Money.class);
		Mockito.when(monto.getMonto()).thenReturn(new BigDecimal("100.00"));
		Mockito.when(monto.toString()).thenReturn("100.00 MXN");
		pago = new Pago("P1", "R1", monto);
		processor = Mockito.mock(IPagoProcessor.class);
	}

	// TC01: autorizar normal
	@Test
	void testAutorizar_Normal() {
		Mockito.when(processor.autorizar(Mockito.eq(monto), Mockito.anyString())).thenReturn("REF123");
		pago.autorizar(processor);
		assertEquals(Pago.ESTADO_AUTORIZADO, pago.getEstado());
		assertEquals("REF123", pago.getReferenciaExterna());
		assertTrue(pago.estaAutorizado());
		assertFalse(pago.fallo());
	}

	// TC02: autorizar con processor null
	@Test
	void testAutorizar_ProcessorNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> pago.autorizar(null));
		assertTrue(ex.getMessage().toLowerCase().contains("procesador"));
	}

	// TC03: autorizar con estado != pendiente
	@Test
	void testAutorizar_EstadoNoPendiente() {
		pago.setEstado(Pago.ESTADO_AUTORIZADO);
		Exception ex = assertThrows(IllegalStateException.class, () -> pago.autorizar(processor));
		assertTrue(ex.getMessage().toLowerCase().contains("pendiente"));
	}

	// TC04: capturar normal
	@Test
	void testCapturar_Normal() {
		// Autorizar primero
		Mockito.when(processor.autorizar(Mockito.eq(monto), Mockito.anyString())).thenReturn("REF456");
		pago.autorizar(processor);
		Mockito.doNothing().when(processor).capturar("REF456");
		pago.capturar(processor);
		assertEquals(Pago.ESTADO_CAPTURADO, pago.getEstado());
		assertTrue(pago.estaCompletado());
		assertFalse(pago.fallo());
	}

	// TC05: capturar con processor null
	@Test
	void testCapturar_ProcessorNull() {
		pago.setEstado(Pago.ESTADO_AUTORIZADO);
		pago.setReferenciaExterna("REF789");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> pago.capturar(null));
		assertTrue(ex.getMessage().toLowerCase().contains("procesador"));
	}

	// TC06: capturar con estado != autorizado
	@Test
	void testCapturar_EstadoNoAutorizado() {
		Exception ex = assertThrows(IllegalStateException.class, () -> pago.capturar(processor));
		assertTrue(ex.getMessage().toLowerCase().contains("autorizado"));
	}

	// TC07: capturar con referenciaExterna vacía
	@Test
	void testCapturar_ReferenciaVacia() {
		pago.setEstado(Pago.ESTADO_AUTORIZADO);
		pago.setReferenciaExterna("");
		Exception ex = assertThrows(IllegalStateException.class, () -> pago.capturar(processor));
		assertTrue(ex.getMessage().toLowerCase().contains("referencia"));
	}

	// TC08: reembolsar normal
	@Test
	void testReembolsar_Normal() {
		// Autorizar y capturar primero
		Mockito.when(processor.autorizar(Mockito.eq(monto), Mockito.anyString())).thenReturn("REF999");
		pago.autorizar(processor);
		Mockito.doNothing().when(processor).capturar("REF999");
		pago.capturar(processor);
		Mockito.doNothing().when(processor).reembolsar("REF999", monto);
		pago.reembolsar(processor);
		assertEquals(Pago.ESTADO_REEMBOLSADO, pago.getEstado());
		assertFalse(pago.fallo());
	}

	// TC09: reembolsar con processor null
	@Test
	void testReembolsar_ProcessorNull() {
		pago.setEstado(Pago.ESTADO_CAPTURADO);
		pago.setReferenciaExterna("REFX");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> pago.reembolsar(null));
		assertTrue(ex.getMessage().toLowerCase().contains("procesador"));
	}

	// TC10: reembolsar con estado != capturado
	@Test
	void testReembolsar_EstadoNoCapturado() {
		Exception ex = assertThrows(IllegalStateException.class, () -> pago.reembolsar(processor));
		assertTrue(ex.getMessage().toLowerCase().contains("capturado"));
	}

	// TC11: reembolsar con referenciaExterna vacía
	@Test
	void testReembolsar_ReferenciaVacia() {
		pago.setEstado(Pago.ESTADO_CAPTURADO);
		pago.setReferenciaExterna("");
		Exception ex = assertThrows(IllegalStateException.class, () -> pago.reembolsar(processor));
		assertTrue(ex.getMessage().toLowerCase().contains("referencia"));
	}
}
