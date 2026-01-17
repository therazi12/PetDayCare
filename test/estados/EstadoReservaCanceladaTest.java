import estados.EstadoReservaCancelada;
import estados.Reserva;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class EstadoReservaCanceladaTest {

	@Test
	void testObtenerNombreEstado() {
		var estado = new EstadoReservaCancelada();
		assertEquals("Cancelada", estado.obtenerNombreEstado());
	}

	@Test
	void testManejarConfirmacion_NoCambiaEstado() {
		var estado = new EstadoReservaCancelada();
		Reserva reserva = Mockito.mock(Reserva.class);
		Mockito.when(reserva.getId()).thenReturn("R5");
		// No debe llamar setEstado
		estado.manejarConfirmacion(reserva);
		Mockito.verify(reserva, Mockito.never()).setEstado(Mockito.any());
	}

	@Test
	void testManejarCancelacion_NoCambiaEstado() {
		var estado = new EstadoReservaCancelada();
		Reserva reserva = Mockito.mock(Reserva.class);
		Mockito.when(reserva.getId()).thenReturn("R6");
		estado.manejarCancelacion(reserva, "motivo ya cancelada");
		Mockito.verify(reserva, Mockito.never()).setEstado(Mockito.any());
	}
}
