import dominio.Disponibilidad;
import valueobjects.Periodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DisponibilidadTest {
	private Disponibilidad disponibilidad;

	@BeforeEach
	void setUp() {
		Periodo periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		disponibilidad = new Disponibilidad("D1", periodo, 5);
	}

	@Test
	@DisplayName("TC01: bloquear cupos válidos incrementa cuposOcupados")
	void testBloquearCuposValidos() {
		disponibilidad.bloquear(3);
		assertEquals(3, disponibilidad.getCuposOcupados());
		assertEquals(2, disponibilidad.getCuposDisponibles());
		assertFalse(disponibilidad.estaCompleta());
	}

	@Test
	@DisplayName("TC02: bloquear con cantidad cero lanza excepción")
	void testBloquearCero() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> disponibilidad.bloquear(0));
		assertEquals("La cantidad a bloquear debe ser mayor que cero", ex.getMessage());
	}

	@Test
	@DisplayName("TC03: bloquear con cantidad negativa lanza excepción")
	void testBloquearNegativo() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> disponibilidad.bloquear(-2));
		assertEquals("La cantidad a bloquear debe ser mayor que cero", ex.getMessage());
	}

	@Test
	@DisplayName("TC04: bloquear más de los cupos disponibles lanza excepción")
	void testBloquearMasQueDisponibles() {
		disponibilidad.bloquear(5); // ocupa todos
		Exception ex = assertThrows(IllegalStateException.class, () -> disponibilidad.bloquear(1));
		assertTrue(ex.getMessage().contains("No hay suficientes cupos disponibles"));
	}

	@Test
	@DisplayName("TC05: liberar cupos válidos decrementa cuposOcupados")
	void testLiberarCuposValidos() {
		disponibilidad.bloquear(4);
		disponibilidad.liberar(2);
		assertEquals(2, disponibilidad.getCuposOcupados());
		assertEquals(3, disponibilidad.getCuposDisponibles());
	}

	@Test
	@DisplayName("TC06: liberar con cantidad cero lanza excepción")
	void testLiberarCero() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> disponibilidad.liberar(0));
		assertEquals("La cantidad a liberar debe ser mayor que cero", ex.getMessage());
	}

	@Test
	@DisplayName("TC07: liberar con cantidad negativa lanza excepción")
	void testLiberarNegativo() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> disponibilidad.liberar(-1));
		assertEquals("La cantidad a liberar debe ser mayor que cero", ex.getMessage());
	}

	@Test
	@DisplayName("TC08: liberar más de los ocupados lanza excepción")
	void testLiberarMasQueOcupados() {
		disponibilidad.bloquear(2);
		Exception ex = assertThrows(IllegalStateException.class, () -> disponibilidad.liberar(3));
		assertTrue(ex.getMessage().contains("No se pueden liberar más cupos de los que están ocupados"));
	}
}
