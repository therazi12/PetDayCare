import servicios.EntrenamientoGrande;
import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import valueobjects.Periodo;
import valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EntrenamientoGrandeTest {

	@Test
	void testEsCompatible_ReglaValida() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		Object mascota = new Object();
		ICompatibilidadStrategy regla = Mockito.mock(ICompatibilidadStrategy.class);
		Mockito.when(regla.esCompatible(servicio, mascota)).thenReturn(true);
		assertTrue(servicio.esCompatible(mascota, regla));
		Mockito.verify(regla).esCompatible(servicio, mascota);
	}

	@Test
	void testEsCompatible_ReglaNull() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		Object mascota = new Object();
		assertDoesNotThrow(() -> servicio.esCompatible(mascota, null));
	}

	@Test
	void testVerificarDisponibilidad_Futuro() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		assertTrue(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Pasado() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
		assertFalse(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Null() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		assertFalse(servicio.verificarDisponibilidad(null));
	}

	@Test
	void testCalcularPrecio_Valido() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of("pro");
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		Money esperado = Money.usd(300);
		Mockito.when(pricing.calcularPrecio(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(esperado);
		Money resultado = servicio.calcularPrecio(periodo, opciones, pricing);
		assertEquals(esperado, resultado);
	}

	@Test
	void testCalcularPrecio_PricingNull() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of();
		assertDoesNotThrow(() -> servicio.calcularPrecio(periodo, opciones, null));
	}

	@Test
	void testCalcularPrecio_PeriodoNull() {
		EntrenamientoGrande servicio = new EntrenamientoGrande();
		List<String> opciones = List.of();
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		assertThrows(IllegalArgumentException.class, () -> servicio.calcularPrecio(null, opciones, pricing));
	}
}
