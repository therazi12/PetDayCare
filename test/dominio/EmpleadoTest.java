import dominio.Empleado;
import roles.IRolEmpleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoTest {
	private Empleado empleado;
	private IRolEmpleado rol;

	@BeforeEach
	void setUp() {
		rol = new IRolEmpleado() {
			public String obtenerNombreRol() { return "Cuidador"; }
			public boolean puedeConfigurarCentro() { return false; }
			public boolean puedeGestionarCuidado() { return true; }
		};
		empleado = new Empleado("E1", "C1", "Pedro", rol);
	}

	@Test
	@DisplayName("TC01: asignarA asigna reserva correctamente")
	void testAsignarAValido() {
		empleado.asignarA("R1");
		assertTrue(empleado.estaAsignadoA("R1"));
		assertEquals(1, empleado.getNumeroReservasAsignadas());
	}

	@Test
	@DisplayName("TC02: asignarA con reservaId null lanza excepción")
	void testAsignarANull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> empleado.asignarA(null));
		assertEquals("El ID de reserva no puede ser null o vacío", ex.getMessage());
	}

	@Test
	@DisplayName("TC03: asignarA con reservaId vacío lanza excepción")
	void testAsignarAVacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> empleado.asignarA("   "));
		assertEquals("El ID de reserva no puede ser null o vacío", ex.getMessage());
	}

	@Test
	@DisplayName("TC04: liberarDe con reserva asignada la elimina")
	void testLiberarDeAsignado() {
		empleado.asignarA("R2");
		assertTrue(empleado.estaAsignadoA("R2"));
		empleado.liberarDe("R2");
		assertFalse(empleado.estaAsignadoA("R2"));
	}

	@Test
	@DisplayName("TC05: liberarDe con reserva no asignada no cambia el estado")
	void testLiberarDeNoAsignado() {
		empleado.asignarA("R3");
		assertEquals(1, empleado.getNumeroReservasAsignadas());
		empleado.liberarDe("R4"); // R4 no asignada
		assertEquals(1, empleado.getNumeroReservasAsignadas());
	}

	@Test
	@DisplayName("TC06: liberarDe con reservaId null lanza excepción")
	void testLiberarDeNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> empleado.liberarDe(null));
		assertEquals("El ID de reserva no puede ser null o vacío", ex.getMessage());
	}

	@Test
	@DisplayName("TC07: liberarDe con reservaId vacío lanza excepción")
	void testLiberarDeVacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> empleado.liberarDe("   "));
		assertEquals("El ID de reserva no puede ser null o vacío", ex.getMessage());
	}
}
