import decoradores.AtencionVeterinariaDecorator;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import interfaces.IPricingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AtencionVeterinariaDecoratorTest {
	private ServicioAbstracto servicioBase;
	private AtencionVeterinariaDecorator decorador;
	private Periodo periodo;
	private IPricingStrategy pricing;

	@BeforeEach
	void setUp() {
		// Mock simple de ServicioAbstracto
		servicioBase = new ServicioAbstracto("S1", "Guardería", "desc", Money.usd(100), "Perros") {
			@Override
			public boolean verificarDisponibilidad(Periodo p) { return true; }
		};
		decorador = new AtencionVeterinariaDecorator(servicioBase);
		periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		pricing = (servicio, per, opciones) -> Money.usd(100);
	}

	@Test
	@DisplayName("TC01: calcularPrecio suma 35 USD al precio base")
	void testCalcularPrecioSumaCorrecta() {
		Money resultado = decorador.calcularPrecio(periodo, List.of("juegos"), pricing);
		assertNotNull(resultado);
		assertEquals(Money.usd(135), resultado);
		assertTrue(resultado.esMayorQue(Money.usd(100)));
	}

	@Test
	@DisplayName("TC02: calcularPrecio con opciones vacías suma 35 USD")
	void testCalcularPrecioOpcionesVacias() {
		Money resultado = decorador.calcularPrecio(periodo, Collections.emptyList(), pricing);
		assertEquals(Money.usd(135), resultado);
	}

	@Test
	@DisplayName("TC03: calcularPrecio con periodo null propaga excepción")
	void testCalcularPrecioPeriodoNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> decorador.calcularPrecio(null, List.of("juegos"), pricing));
		assertEquals("El período no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC04: calcularPrecio con pricing null propaga excepción")
	void testCalcularPrecioPricingNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> decorador.calcularPrecio(periodo, List.of("juegos"), null));
		assertEquals("La estrategia de pricing no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC05: calcularPrecio cuando el precio base es cero retorna 35 USD")
	void testCalcularPrecioBaseCero() {
		ServicioAbstracto servicioCero = new ServicioAbstracto("S2", "Guardería", "desc", Money.usd(0), "Perros") {
			@Override
			public boolean verificarDisponibilidad(Periodo p) { return true; }
			@Override
			public Money calcularPrecio(Periodo p, List<String> o, IPricingStrategy pr) { return Money.usd(0); }
		};
		AtencionVeterinariaDecorator decoCero = new AtencionVeterinariaDecorator(servicioCero);
		Money resultado = decoCero.calcularPrecio(periodo, List.of("juegos"), pricing);
		assertEquals(Money.usd(35), resultado);
		assertTrue(resultado.esIgualA(Money.usd(35)));
	}
}
