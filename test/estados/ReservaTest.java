import estados.Reserva;
import dominio.Usuario;
import dominio.Mascota;
import dominio.Centro;
import dominio.PoliticaCancelacion;
import interfaces.IPagoProcessor;
import interfaces.INotifier;
import interfaces.ICanalNotificacion;
import interfaces.IServicioBase;
import interfaces.IPricingStrategy;
import valueobjects.Periodo;
import valueobjects.Money;
import notificaciones.Notificador;
import incidentes.IHandlerIncidente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

	private Usuario usuario;
	private Mascota mascota;
	private Centro centro;
	private Periodo periodo;
	private IServicioBase servicio;
	private Reserva reserva;

	@BeforeEach
	void setUp() {
		usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getNombre()).thenReturn("Juan");
		mascota = Mockito.mock(Mascota.class);
		Mockito.when(mascota.getNombre()).thenReturn("Firulais");
		centro = Mockito.mock(Centro.class);
		Mockito.when(centro.getNombre()).thenReturn("Centro 1");
		periodo = new Periodo(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
		servicio = Mockito.mock(IServicioBase.class);
		reserva = new Reserva(usuario, mascota, centro, periodo, List.of(servicio));
	}

	// TC01: confirmar normal
	@Test
	void testConfirmar_Normal() {
		IPagoProcessor pagos = Mockito.mock(IPagoProcessor.class);
		INotifier notificador = Mockito.mock(Notificador.class);
		PoliticaCancelacion politica = Mockito.mock(PoliticaCancelacion.class);
		Mockito.when(pagos.autorizar(Mockito.any(), Mockito.anyString())).thenReturn("REF");
		Mockito.doNothing().when(pagos).capturar(Mockito.anyString());
		Mockito.when(politica.calcularPenalidad(Mockito.any(), Mockito.any())).thenReturn(Money.usd(0));
		Mockito.doNothing().when(notificador).enviarSegunPreferencia(Mockito.any(), Mockito.anyString());
		assertDoesNotThrow(() -> reserva.confirmar(pagos, notificador, politica));
	}

	// TC02: confirmar pagos null
	@Test
	void testConfirmar_PagosNull() {
		INotifier notificador = Mockito.mock(Notificador.class);
		PoliticaCancelacion politica = Mockito.mock(PoliticaCancelacion.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.confirmar(null, notificador, politica));
		assertTrue(ex.getMessage().toLowerCase().contains("procesador"));
	}

	// TC03: confirmar notificador null
	@Test
	void testConfirmar_NotificadorNull() {
		IPagoProcessor pagos = Mockito.mock(IPagoProcessor.class);
		PoliticaCancelacion politica = Mockito.mock(PoliticaCancelacion.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.confirmar(pagos, null, politica));
		assertTrue(ex.getMessage().toLowerCase().contains("notificador"));
	}

	// TC04: cancelar normal
	@Test
	void testCancelar_Normal() {
		String motivo = "No puedo asistir";
		PoliticaCancelacion politica = Mockito.mock(PoliticaCancelacion.class);
		IPagoProcessor pagos = Mockito.mock(IPagoProcessor.class);
		INotifier notificador = Mockito.mock(Notificador.class);
		Mockito.when(politica.calcularPenalidad(Mockito.any(), Mockito.any())).thenReturn(Money.usd(10));
		Mockito.doNothing().when(pagos).reembolsar(Mockito.anyString(), Mockito.any());
		Mockito.doNothing().when(notificador).enviarSegunPreferencia(Mockito.any(), Mockito.anyString());
		assertDoesNotThrow(() -> reserva.cancelar(motivo, politica, pagos, notificador));
	}

	// TC05: cancelar motivo vacío
	@Test
	void testCancelar_MotivoVacio() {
		PoliticaCancelacion politica = Mockito.mock(PoliticaCancelacion.class);
		IPagoProcessor pagos = Mockito.mock(IPagoProcessor.class);
		INotifier notificador = Mockito.mock(INotifier.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.cancelar("   ", politica, pagos, notificador));
		assertTrue(ex.getMessage().toLowerCase().contains("motivo"));
	}

	// TC06: cancelar politica null
	@Test
	void testCancelar_PoliticaNull() {
		IPagoProcessor pagos = Mockito.mock(IPagoProcessor.class);
		INotifier notificador = Mockito.mock(INotifier.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.cancelar("motivo", null, pagos, notificador));
		assertTrue(ex.getMessage().toLowerCase().contains("política"));
	}

	// TC07: cancelar pagos null
	@Test
	void testCancelar_PagosNull() {
		PoliticaCancelacion politica = Mockito.mock(PoliticaCancelacion.class);
		INotifier notificador = Mockito.mock(INotifier.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.cancelar("motivo", politica, null, notificador));
		assertTrue(ex.getMessage().toLowerCase().contains("procesador"));
	}

	// TC08: cancelar notificador null
	@Test
	void testCancelar_NotificadorNull() {
		PoliticaCancelacion politica = Mockito.mock(PoliticaCancelacion.class);
		IPagoProcessor pagos = Mockito.mock(IPagoProcessor.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.cancelar("motivo", politica, pagos, null));
		assertTrue(ex.getMessage().toLowerCase().contains("notificador"));
	}

	// TC09: reportarIncidente normal
	@Test
	void testReportarIncidente_Normal() {
		IHandlerIncidente handler = Mockito.mock(IHandlerIncidente.class);
		Mockito.when(handler.manejar(Mockito.any())).thenReturn("OK");
		String result = reserva.reportarIncidente("Motivo", "Alta", handler);
		assertEquals("OK", result);
	}

	// TC10: reportarIncidente motivo vacío
	@Test
	void testReportarIncidente_MotivoVacio() {
		IHandlerIncidente handler = Mockito.mock(IHandlerIncidente.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.reportarIncidente("   ", "Alta", handler));
		assertTrue(ex.getMessage().toLowerCase().contains("motivo"));
	}

	// TC11: reportarIncidente gravedad vacía
	@Test
	void testReportarIncidente_GravedadVacia() {
		IHandlerIncidente handler = Mockito.mock(IHandlerIncidente.class);
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.reportarIncidente("Motivo", "   ", handler));
		assertTrue(ex.getMessage().toLowerCase().contains("gravedad"));
	}

	// TC12: reportarIncidente handler null
	@Test
	void testReportarIncidente_HandlerNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> reserva.reportarIncidente("Motivo", "Alta", null));
		assertTrue(ex.getMessage().toLowerCase().contains("handler"));
	}
}
