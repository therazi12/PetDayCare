import dominio.Notificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

	private Notificacion notificacion;
	private final String id = "N1";
	private final String mensaje = "Mensaje inicial";
	private final LocalDateTime fechaInicial = LocalDateTime.of(2025, 12, 25, 10, 30, 0);

	@BeforeEach
	void setUp() {
		notificacion = new Notificacion(id, mensaje, fechaInicial);
	}

	// TC01: setMensaje con mensaje válido
	@Test
	void testSetMensaje_Valido() {
		String nuevoMensaje = "Nuevo mensaje";
		notificacion.setMensaje(nuevoMensaje);
		assertEquals(nuevoMensaje, notificacion.getMensaje());
		assertNotNull(notificacion.getMensaje());
		assertFalse(notificacion.getMensaje().isEmpty());
		assertEquals(id, notificacion.getId());
		assertEquals(fechaInicial, notificacion.getFechaEnvio());
	}

	// TC02: setMensaje con mensaje null
	@Test
	void testSetMensaje_Null() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificacion.setMensaje(null));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	// TC03: setMensaje con mensaje vacío
	@Test
	void testSetMensaje_Vacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificacion.setMensaje("   "));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	// TC04: setFechaEnvio con fecha válida
	@Test
	void testSetFechaEnvio_Valida() {
		LocalDateTime nuevaFecha = LocalDateTime.of(2026, 1, 1, 12, 0, 0);
		notificacion.setFechaEnvio(nuevaFecha);
		assertEquals(nuevaFecha, notificacion.getFechaEnvio());
		assertNotNull(notificacion.getFechaEnvio());
		assertEquals(id, notificacion.getId());
		assertEquals(mensaje, notificacion.getMensaje());
	}

	// TC05: setFechaEnvio con fecha null
	@Test
	void testSetFechaEnvio_Null() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificacion.setFechaEnvio(null));
		assertTrue(ex.getMessage().toLowerCase().contains("fecha"));
	}

	// TC06: getDatos formato esperado
	@Test
	void testGetDatos_Formato() {
		String datos = notificacion.getDatos();
		assertNotNull(datos);
		assertTrue(datos.contains(id));
		assertTrue(datos.contains(mensaje));
		assertTrue(datos.matches("Notificación #" + id + " - Fecha: \\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2} - Mensaje: .+"));
		assertTrue(datos.startsWith("Notificación #" + id));
	}
}
