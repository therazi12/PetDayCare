import estados.EstadoReservaPendiente;
import estados.EstadoReservaConfirmada;
import estados.EstadoReservaCancelada;
import estados.Reserva;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class EstadoReservaPendienteTest {

	@Test
	void testObtenerNombreEstado() {
		var estado = new EstadoReservaPendiente();
		assertEquals("Pendiente", estado.obtenerNombreEstado());
	}

	@Test
	void testManejarConfirmacion_TransicionAConfirmada() {
		var estado = new EstadoReservaPendiente();
		Reserva reserva = Mockito.mock(Reserva.class);
		Mockito.when(reserva.getId()).thenReturn("R1");
		// Simula setEstado
		Mockito.doNothing().when(reserva).setEstado(Mockito.any(EstadoReservaConfirmada.class));
		estado.manejarConfirmacion(reserva);
		Mockito.verify(reserva).setEstado(Mockito.any(EstadoReservaConfirmada.class));
	}

	@Test
	void testManejarCancelacion_TransicionACancelada() {
		var estado = new EstadoReservaPendiente();
		Reserva reserva = Mockito.mock(Reserva.class);
		Mockito.when(reserva.getId()).thenReturn("R2");
		Mockito.doNothing().when(reserva).setEstado(Mockito.any(EstadoReservaCancelada.class));
		estado.manejarCancelacion(reserva, "motivo");
		Mockito.verify(reserva).setEstado(Mockito.any(EstadoReservaCancelada.class));
	}
}
