import dominio.Centro;
import dominio.Disponibilidad;
import dominio.PoliticaCancelacion;
import factories.ICentroFactory;
import interfaces.IServicioBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import valueobjects.Periodo;
import valueobjects.Porcentaje;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CentroTest {
	private Centro centro;

	@BeforeEach
	void setUp() {
		centro = new Centro("C1", "Centro 1", "Calle 123", 10);
	}

	@Test
	@DisplayName("TC01: asignarFabrica asigna correctamente una factory")
	void testAsignarFabricaValida() {
		ICentroFactory factory = new ICentroFactory() {
			public IServicioBase crearGuarderia() { return null; }
			public IServicioBase crearHospedaje() { return null; }
			public IServicioBase crearPaseo() { return null; }
			public IServicioBase crearEntrenamiento() { return null; }
			public IServicioBase crearBienestar() { return null; }
		};
		assertDoesNotThrow(() -> centro.asignarFabrica(factory));
		assertSame(factory, centro.getServicioFactory());
	}

	@Test
	@DisplayName("TC02: asignarFabrica con null lanza excepción")
	void testAsignarFabricaNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> centro.asignarFabrica(null));
		assertEquals("La factory no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC03: definirPoliticaCancelacion asigna correctamente una política")
	void testDefinirPoliticaValida() {
		PoliticaCancelacion politica = new PoliticaCancelacion("P1", "Flexible", 24, new Porcentaje(0.1));
		assertDoesNotThrow(() -> centro.definirPoliticaCancelacion(politica));
		assertEquals(politica, centro.getPoliticaCancelacion());
	}

	@Test
	@DisplayName("TC04: definirPoliticaCancelacion con null lanza excepción")
	void testDefinirPoliticaNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> centro.definirPoliticaCancelacion(null));
		assertEquals("La política de cancelación no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC05: programarDisponibilidad agrega disponibilidad")
	void testProgramarDisponibilidadValida() {
		Disponibilidad disp = new Disponibilidad("D1", new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)), 5);
		assertDoesNotThrow(() -> centro.programarDisponibilidad(disp));
		assertTrue(centro.getDisponibilidades().containsKey("D1"));
	}

	@Test
	@DisplayName("TC06: programarDisponibilidad con null lanza excepción")
	void testProgramarDisponibilidadNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> centro.programarDisponibilidad(null));
		assertEquals("La disponibilidad no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC07: obtenerServicio retorna servicio correcto para tipo válido")
	void testObtenerServicioTipoValido() {
		ICentroFactory factory = new ICentroFactory() {
			public IServicioBase crearGuarderia() { return () -> "Guardería"; }
			public IServicioBase crearHospedaje() { return () -> "Hospedaje"; }
			public IServicioBase crearPaseo() { return () -> "Paseo"; }
			public IServicioBase crearEntrenamiento() { return () -> "Entrenamiento"; }
			public IServicioBase crearBienestar() { return () -> "Bienestar"; }
		};
		centro.asignarFabrica(factory);
		assertEquals("Guardería", centro.obtenerServicio(Centro.TIPO_GUARDERIA).obtenerNombre());
		assertEquals("Hospedaje", centro.obtenerServicio(Centro.TIPO_HOSPEDAJE).obtenerNombre());
		assertEquals("Paseo", centro.obtenerServicio(Centro.TIPO_PASEO).obtenerNombre());
		assertEquals("Entrenamiento", centro.obtenerServicio(Centro.TIPO_ENTRENAMIENTO).obtenerNombre());
		assertEquals("Bienestar", centro.obtenerServicio(Centro.TIPO_BIENESTAR).obtenerNombre());
	}

	@Test
	@DisplayName("TC08: obtenerServicio con tipo null lanza excepción")
	void testObtenerServicioTipoNull() {
		ICentroFactory factory = new ICentroFactory() {
			public IServicioBase crearGuarderia() { return null; }
			public IServicioBase crearHospedaje() { return null; }
			public IServicioBase crearPaseo() { return null; }
			public IServicioBase crearEntrenamiento() { return null; }
			public IServicioBase crearBienestar() { return null; }
		};
		centro.asignarFabrica(factory);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> centro.obtenerServicio(null));
		assertEquals("El tipo de servicio no puede ser null o vacío", ex.getMessage());
	}

	@Test
	@DisplayName("TC09: obtenerServicio con tipo vacío lanza excepción")
	void testObtenerServicioTipoVacio() {
		ICentroFactory factory = new ICentroFactory() {
			public IServicioBase crearGuarderia() { return null; }
			public IServicioBase crearHospedaje() { return null; }
			public IServicioBase crearPaseo() { return null; }
			public IServicioBase crearEntrenamiento() { return null; }
			public IServicioBase crearBienestar() { return null; }
		};
		centro.asignarFabrica(factory);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> centro.obtenerServicio("   "));
		assertEquals("El tipo de servicio no puede ser null o vacío", ex.getMessage());
	}

	@Test
	@DisplayName("TC10: obtenerServicio con tipo inválido lanza excepción")
	void testObtenerServicioTipoInvalido() {
		ICentroFactory factory = new ICentroFactory() {
			public IServicioBase crearGuarderia() { return null; }
			public IServicioBase crearHospedaje() { return null; }
			public IServicioBase crearPaseo() { return null; }
			public IServicioBase crearEntrenamiento() { return null; }
			public IServicioBase crearBienestar() { return null; }
		};
		centro.asignarFabrica(factory);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> centro.obtenerServicio("spa"));
		assertTrue(ex.getMessage().contains("Tipo de servicio no válido"));
	}

	@Test
	@DisplayName("TC11: obtenerServicio sin factory asignada lanza excepción")
	void testObtenerServicioSinFactory() {
		Exception ex = assertThrows(IllegalStateException.class, () -> centro.obtenerServicio(Centro.TIPO_GUARDERIA));
		assertEquals("El centro no tiene una factory asignada", ex.getMessage());
	}
}
