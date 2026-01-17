import estados.EstadoReservaConfirmada;
import estados.EstadoReservaCancelada;
import estados.Reserva;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class EstadoReservaConfirmadaTest {

	@Test
	void testObtenerNombreEstado() {
		var estado = new EstadoReservaConfirmada();
		assertEquals("Confirmada", estado.obtenerNombreEstado());
	}

	@Test
	void testManejarConfirmacion_NoCambiaEstado() {
		var estado = new EstadoReservaConfirmada();
		Reserva reserva = Mockito.mock(Reserva.class);
		Mockito.when(reserva.getId()).thenReturn("R3");
		// No debe llamar setEstado
		estado.manejarConfirmacion(reserva);
		Mockito.verify(reserva, Mockito.never()).setEstado(Mockito.any());
	}

	@Test
	void testManejarCancelacion_TransicionACancelada() {
		var estado = new EstadoReservaConfirmada();
		Reserva reserva = Mockito.mock(Reserva.class);
		Mockito.when(reserva.getId()).thenReturn("R4");
		Mockito.doNothing().when(reserva).setEstado(Mockito.any(EstadoReservaCancelada.class));
		estado.manejarCancelacion(reserva, "motivo penalización");
		Mockito.verify(reserva).setEstado(Mockito.any(EstadoReservaCancelada.class));
	}
}
