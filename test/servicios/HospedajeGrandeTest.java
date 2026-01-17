import servicios.HospedajeGrande;
import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import valueobjects.Periodo;
import valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HospedajeGrandeTest {

	@Test
	void testEsCompatible_ReglaValida() {
		HospedajeGrande servicio = new HospedajeGrande();
		Object mascota = new Object();
		ICompatibilidadStrategy regla = Mockito.mock(ICompatibilidadStrategy.class);
		Mockito.when(regla.esCompatible(servicio, mascota)).thenReturn(true);
		assertTrue(servicio.esCompatible(mascota, regla));
		Mockito.verify(regla).esCompatible(servicio, mascota);
	}

	@Test
	void testEsCompatible_ReglaNull() {
		HospedajeGrande servicio = new HospedajeGrande();
		Object mascota = new Object();
		assertDoesNotThrow(() -> servicio.esCompatible(mascota, null));
	}

	@Test
	void testVerificarDisponibilidad_Futuro() {
		HospedajeGrande servicio = new HospedajeGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		assertTrue(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Pasado() {
		HospedajeGrande servicio = new HospedajeGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
		assertFalse(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Null() {
		HospedajeGrande servicio = new HospedajeGrande();
		assertFalse(servicio.verificarDisponibilidad(null));
	}

	@Test
	void testCalcularPrecio_Valido() {
		HospedajeGrande servicio = new HospedajeGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of("premium");
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		Money esperado = Money.usd(250);
		Mockito.when(pricing.calcularPrecio(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(esperado);
		Money resultado = servicio.calcularPrecio(periodo, opciones, pricing);
		assertEquals(esperado, resultado);
	}

	@Test
	void testCalcularPrecio_PricingNull() {
		HospedajeGrande servicio = new HospedajeGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of();
		assertDoesNotThrow(() -> servicio.calcularPrecio(periodo, opciones, null));
	}

	@Test
	void testCalcularPrecio_PeriodoNull() {
		HospedajeGrande servicio = new HospedajeGrande();
		List<String> opciones = List.of();
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		assertThrows(IllegalArgumentException.class, () -> servicio.calcularPrecio(null, opciones, pricing));
	}
}
