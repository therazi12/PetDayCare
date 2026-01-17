import servicios.EntrenamientoPequena;
import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import valueobjects.Periodo;
import valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EntrenamientoPequenaTest {

	@Test
	void testEsCompatible_ReglaValida() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		Object mascota = new Object();
		ICompatibilidadStrategy regla = Mockito.mock(ICompatibilidadStrategy.class);
		Mockito.when(regla.esCompatible(servicio, mascota)).thenReturn(true);
		assertTrue(servicio.esCompatible(mascota, regla));
		Mockito.verify(regla).esCompatible(servicio, mascota);
	}

	@Test
	void testEsCompatible_ReglaNull() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		Object mascota = new Object();
		assertDoesNotThrow(() -> servicio.esCompatible(mascota, null));
	}

	@Test
	void testVerificarDisponibilidad_Futuro() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		assertTrue(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Pasado() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
		assertFalse(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Null() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		assertFalse(servicio.verificarDisponibilidad(null));
	}

	@Test
	void testCalcularPrecio_Valido() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of("básico");
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		Money esperado = Money.usd(80);
		Mockito.when(pricing.calcularPrecio(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(esperado);
		Money resultado = servicio.calcularPrecio(periodo, opciones, pricing);
		assertEquals(esperado, resultado);
	}

	@Test
	void testCalcularPrecio_PricingNull() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of();
		assertDoesNotThrow(() -> servicio.calcularPrecio(periodo, opciones, null));
	}

	@Test
	void testCalcularPrecio_PeriodoNull() {
		EntrenamientoPequena servicio = new EntrenamientoPequena();
		List<String> opciones = List.of();
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		assertThrows(IllegalArgumentException.class, () -> servicio.calcularPrecio(null, opciones, pricing));
	}
}
