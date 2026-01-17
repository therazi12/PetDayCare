import servicios.GuarderiaPequena;
import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import valueobjects.Periodo;
import valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GuarderiaPequenaTest {

	@Test
	void testEsCompatible_ReglaValida() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		Object mascota = new Object();
		ICompatibilidadStrategy regla = Mockito.mock(ICompatibilidadStrategy.class);
		Mockito.when(regla.esCompatible(servicio, mascota)).thenReturn(true);
		assertTrue(servicio.esCompatible(mascota, regla));
		Mockito.verify(regla).esCompatible(servicio, mascota);
	}

	@Test
	void testEsCompatible_ReglaNull() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		Object mascota = new Object();
		// Debe usar CompatibilidadStrategyBasica (por defecto retorna false, pero no lanza excepción)
		assertDoesNotThrow(() -> servicio.esCompatible(mascota, null));
	}

	@Test
	void testVerificarDisponibilidad_Futuro() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		assertTrue(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Pasado() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));
		assertFalse(servicio.verificarDisponibilidad(periodo));
	}

	@Test
	void testVerificarDisponibilidad_Null() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		assertFalse(servicio.verificarDisponibilidad(null));
	}

	@Test
	void testCalcularPrecio_Valido() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of("extra");
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		Money esperado = Money.usd(100);
		Mockito.when(pricing.calcularPrecio(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(esperado);
		Money resultado = servicio.calcularPrecio(periodo, opciones, pricing);
		assertEquals(esperado, resultado);
	}

	@Test
	void testCalcularPrecio_PricingNull() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		List<String> opciones = List.of();
		assertDoesNotThrow(() -> servicio.calcularPrecio(periodo, opciones, null));
	}

	@Test
	void testCalcularPrecio_PeriodoNull() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		List<String> opciones = List.of();
		IPricingStrategy pricing = Mockito.mock(IPricingStrategy.class);
		assertThrows(IllegalArgumentException.class, () -> servicio.calcularPrecio(null, opciones, pricing));
	}
	
	@Test
	void testCompletoServicios() {
		GuarderiaPequena servicio = new GuarderiaPequena();
		// Aquí se pueden agregar las pruebas completas para todos los servicios solicitados.
		// Ejemplo de prueba:
		Periodo periodo = new Periodo(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
		assertTrue(servicio.verificarDisponibilidad(periodo));
	}
}
