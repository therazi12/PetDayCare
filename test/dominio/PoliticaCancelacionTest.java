import dominio.PoliticaCancelacion;
import estados.Reserva;
import valueobjects.Money;
import valueobjects.Porcentaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PoliticaCancelacionTest {

	private PoliticaCancelacion politica;
	private Porcentaje penalidad;
	private Reserva reserva;
	private Money total;
	private LocalDateTime inicio;
	private LocalDateTime cancelacion;

	@BeforeEach
	void setUp() {
		penalidad = Mockito.mock(Porcentaje.class);
		Mockito.when(penalidad.calcularDe(Mockito.any(Money.class))).thenAnswer(invocation -> {
			Money m = invocation.getArgument(0);
			// Simula 20% de penalidad
			return Money.usd(m.getMonto().multiply(new java.math.BigDecimal("0.2")));
		});
		Mockito.when(penalidad.toString()).thenReturn("20%");
		politica = new PoliticaCancelacion("PC1", "Política estándar", 24, penalidad);
		reserva = Mockito.mock(Reserva.class);
		total = Money.usd(100);
		inicio = LocalDateTime.of(2026, 2, 1, 10, 0, 0);
		cancelacion = inicio.minusHours(30); // 30 horas antes
	}

	// TC01: calcularPenalidad dentro ventana gratis
	@Test
	void testCalcularPenalidad_DentroVentanaGratis() {
		Mockito.when(reserva.getPeriodo()).thenReturn(new valueobjects.Periodo(inicio, inicio.plusDays(1)));
		Mockito.when(reserva.getTotal()).thenReturn(total);
		Money penalidadResult = politica.calcularPenalidad(reserva, inicio.minusHours(30));
		assertNotNull(penalidadResult);
		assertEquals(Money.usd(0), penalidadResult);
	}

	// TC02: calcularPenalidad fuera ventana gratis
	@Test
	void testCalcularPenalidad_FueraVentanaGratis() {
		Mockito.when(reserva.getPeriodo()).thenReturn(new valueobjects.Periodo(inicio, inicio.plusDays(1)));
		Mockito.when(reserva.getTotal()).thenReturn(total);
		Money penalidadResult = politica.calcularPenalidad(reserva, inicio.minusHours(10));
		assertNotNull(penalidadResult);
		assertEquals(Money.usd(20), penalidadResult); // 20% de 100
	}

	// TC03: calcularPenalidad reserva null
	@Test
	void testCalcularPenalidad_ReservaNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> politica.calcularPenalidad(null, cancelacion));
		assertTrue(ex.getMessage().toLowerCase().contains("reserva"));
	}

	// TC04: calcularPenalidad fechaCancelacion null
	@Test
	void testCalcularPenalidad_FechaCancelacionNull() {
		Mockito.when(reserva.getPeriodo()).thenReturn(new valueobjects.Periodo(inicio, inicio.plusDays(1)));
		Exception ex = assertThrows(IllegalArgumentException.class, () -> politica.calcularPenalidad(reserva, null));
		assertTrue(ex.getMessage().toLowerCase().contains("cancelación"));
	}

	// TC05: calcularPenalidad reserva con periodo null
	@Test
	void testCalcularPenalidad_PeriodoNull() {
		Mockito.when(reserva.getPeriodo()).thenReturn(null);
		Money penalidadResult = politica.calcularPenalidad(reserva, cancelacion);
		assertEquals(Money.usd(0), penalidadResult);
	}

	// TC06: calcularPenalidad reserva con total null
	@Test
	void testCalcularPenalidad_TotalNull() {
		Mockito.when(reserva.getPeriodo()).thenReturn(new valueobjects.Periodo(inicio, inicio.plusDays(1)));
		Mockito.when(reserva.getTotal()).thenReturn(null);
		Money penalidadResult = politica.calcularPenalidad(reserva, cancelacion);
		assertEquals(Money.usd(0), penalidadResult);
	}

	// TC07: estaDentroVentanaGratis true
	@Test
	void testEstaDentroVentanaGratis_True() {
		boolean dentro = politica.estaDentroVentanaGratis(inicio.minusHours(30), inicio);
		assertTrue(dentro);
	}

	// TC08: estaDentroVentanaGratis false
	@Test
	void testEstaDentroVentanaGratis_False() {
		boolean dentro = politica.estaDentroVentanaGratis(inicio.minusHours(10), inicio);
		assertFalse(dentro);
	}

	// TC09: estaDentroVentanaGratis fechaCancelacion null
	@Test
	void testEstaDentroVentanaGratis_FechaCancelacionNull() {
		boolean dentro = politica.estaDentroVentanaGratis(null, inicio);
		assertFalse(dentro);
	}

	// TC10: estaDentroVentanaGratis fechaInicioReserva null
	@Test
	void testEstaDentroVentanaGratis_FechaInicioNull() {
		boolean dentro = politica.estaDentroVentanaGratis(inicio.minusHours(10), null);
		assertFalse(dentro);
	}
}
