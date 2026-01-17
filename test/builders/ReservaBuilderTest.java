import builders.ReservaBuilder;
import dominio.Centro;
import dominio.Mascota;
import dominio.Usuario;
import estados.Reserva;
import interfaces.IServicioBase;
import valueobjects.Periodo;
import enums.MetodoNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaBuilderTest {

	private Usuario usuario;
	private Mascota mascota;
	private Centro centro;
	private Periodo periodo;
	private IServicioBase servicio;

	@BeforeEach
	void setUp() {
		usuario = new Usuario("U1", "Juan", "juan@mail.com", "123456789", MetodoNotificacion.EMAIL);
		mascota = new Mascota("M1", "Firulais", "Perro", "Labrador", "Mediano", 3, "");
		centro = new Centro("C1", "Centro 1", "Calle 123", 10);
		periodo = new Periodo(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
		// Mock simple de IServicioBase
		servicio = new IServicioBase() {
			@Override
			public String obtenerNombre() { return "Guardería"; }
			@Override
			public valueobjects.Money calcularPrecio(Periodo p, java.util.List<String> o, interfaces.IPricingStrategy pr) { return valueobjects.Money.usd(10); }
		};
	}

	@Test
	@DisplayName("TC01: addService agrega servicio válido y retorna builder")
	void testAddServiceValido() {
		ReservaBuilder builder = new ReservaBuilder();
		ReservaBuilder returned = builder.addService(servicio);
		assertSame(builder, returned);
		// Para verificar que el servicio se agregó, intentamos construir la reserva con todos los datos
		Reserva reserva = builder.setUsuario(usuario).setMascota(mascota).setCentro(centro).setPeriodo(periodo).build();
		assertNotNull(reserva);
		assertEquals(usuario, reserva.getUsuario());
	}

	@Test
	@DisplayName("TC02: addService con null lanza excepción")
	void testAddServiceNull() {
		ReservaBuilder builder = new ReservaBuilder();
		Exception ex = assertThrows(IllegalArgumentException.class, () -> builder.addService(null));
		assertEquals("El servicio no puede ser null", ex.getMessage());
	}

	@Test
	@DisplayName("TC03: build con todos los datos y servicio crea Reserva")
	void testBuildExitoso() {
		ReservaBuilder builder = new ReservaBuilder()
				.setUsuario(usuario)
				.setMascota(mascota)
				.setCentro(centro)
				.setPeriodo(periodo)
				.addService(servicio);
		Reserva reserva = builder.build();
		assertNotNull(reserva);
		assertEquals(usuario, reserva.getUsuario());
		assertEquals(mascota, reserva.getMascota());
		assertEquals(centro, reserva.getCentro());
		assertEquals(periodo, reserva.getPeriodo());
	}

	@Test
	@DisplayName("TC04: build con datos obligatorios faltantes lanza excepción")
	void testBuildFaltanDatosObligatorios() {
		ReservaBuilder builder = new ReservaBuilder();
		builder.addService(servicio); // Solo agrega servicio, faltan los demás
		Exception ex = assertThrows(IllegalStateException.class, builder::build);
		assertTrue(ex.getMessage().contains("Faltan datos obligatorios"));
	}

	@Test
	@DisplayName("TC05: build con lista de servicios vacía lanza excepción")
	void testBuildSinServicios() {
		ReservaBuilder builder = new ReservaBuilder()
				.setUsuario(usuario)
				.setMascota(mascota)
				.setCentro(centro)
				.setPeriodo(periodo);
		Exception ex = assertThrows(IllegalStateException.class, builder::build);
		assertEquals("La reserva debe tener al menos un servicio.", ex.getMessage());
	}
}
