import servicios.BienestarGrande;
import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import valueobjects.Periodo;
import valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BienestarGrandeTest {

	@Test
	void testEsCompatible_ReglaValida() {
		BienestarGrande servicio = new BienestarGrande();
		Object mascota = new Object();
		ICompatibilidadStrategy regla = Mockito.mock(ICompatibilidadStrategy.class);
		Mockito.when(regla.esCompatible(servicio, mascota)).thenReturn(true);
		assertTrue(servicio.esCompatible(mascota, regla));
		Mockito.verify(regla).esCompatible(servicio, mascota);
	}

	@Test
	void testEsCompatible_ReglaNull() {
		BienestarGrande servicio = new BienestarGrande();
		Object mascota = new Object();
		assertDoesNotThrow(() -> servicio.esCompatible(mascota, null));
	}

	@Test
	void testVerificarDisponibilidad_Futuro() {
		BienestarGrande servicio = new BienestarGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		assertTrue(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Pasado() {
		BienestarGrande servicio = new BienestarGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
		assertFalse(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Null() {
		BienestarGrande servicio = new BienestarGrande();
		assertFalse(servicio.verificarDisponibilidad(null));
	}

	@Test
	void testCalcularPrecio_Valido() {
		BienestarGrande servicio = new BienestarGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of("spa");
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		Money esperado = Money.usd(200);
		Mockito.when(pricing.calcularPrecio(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(esperado);
		Money resultado = servicio.calcularPrecio(periodo, opciones, pricing);
		assertEquals(esperado, resultado);
	}

	@Test
	void testCalcularPrecio_PricingNull() {
		BienestarGrande servicio = new BienestarGrande();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of();
		assertDoesNotThrow(() -> servicio.calcularPrecio(periodo, opciones, null));
	}

	@Test
	void testCalcularPrecio_PeriodoNull() {
		BienestarGrande servicio = new BienestarGrande();
		List<String> opciones = List.of();
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		assertThrows(IllegalArgumentException.class, () -> servicio.calcularPrecio(null, opciones, pricing));
	}
}
