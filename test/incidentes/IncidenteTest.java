import incidentes.Incidente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IncidenteTest {

	@Test
	void testMarcarComoEnProceso() {
		Incidente incidente = new Incidente("I1", "Motivo", "baja", "R1");
		incidente.marcarComoEnProceso();
		assertEquals(Incidente.ESTADO_EN_PROCESO, incidente.getEstado());
	}

	@Test
	void testMarcarComoResuelto() {
		Incidente incidente = new Incidente("I2", "Motivo", "alta", "R2");
		incidente.marcarComoResuelto();
		assertEquals(Incidente.ESTADO_RESUELTO, incidente.getEstado());
	}
}
