import incidentes.SoporteCentroHandler;
import incidentes.BaseHandlerIncidente;
import incidentes.Incidente;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class SoporteCentroHandlerTest {

	@Test
	void testManejar_GravedadBaja() {
		SoporteCentroHandler handler = new SoporteCentroHandler();
		Incidente incidente = Mockito.mock(Incidente.class);
		Mockito.when(incidente.esGravedadLeve()).thenReturn(true);
		Mockito.when(incidente.getId()).thenReturn("I1");
		Mockito.when(incidente.getMotivo()).thenReturn("Motivo leve");
		Mockito.when(incidente.getGravedad()).thenReturn("baja");
		String result = handler.manejar(incidente);
		assertNotNull(result);
		assertTrue(result.contains("Soporte Centro"));
		assertTrue(result.contains("Resuelto localmente"));
	}

	@Test
	void testManejar_GravedadMedia() {
		SoporteCentroHandler handler = new SoporteCentroHandler();
		Incidente incidente = Mockito.mock(Incidente.class);
		Mockito.when(incidente.esGravedadLeve()).thenReturn(true);
		Mockito.when(incidente.getId()).thenReturn("I2");
		Mockito.when(incidente.getMotivo()).thenReturn("Motivo media");
		Mockito.when(incidente.getGravedad()).thenReturn("media");
		String result = handler.manejar(incidente);
		assertNotNull(result);
		assertTrue(result.contains("Soporte Centro"));
		assertTrue(result.contains("Resuelto localmente"));
	}

	@Test
	void testManejar_GravedadAlta_ConNextHandler() {
		SoporteCentroHandler handler = new SoporteCentroHandler();
		Incidente incidente = Mockito.mock(Incidente.class);
		Mockito.when(incidente.esGravedadLeve()).thenReturn(false);
		Mockito.when(incidente.getId()).thenReturn("I3");
		Mockito.when(incidente.getMotivo()).thenReturn("Motivo alta");
		Mockito.when(incidente.getGravedad()).thenReturn("alta");
		BaseHandlerIncidente nextHandler = Mockito.mock(BaseHandlerIncidente.class);
		Mockito.when(nextHandler.manejar(incidente)).thenReturn("Escalado a plataforma");
		handler.setNextHandler(nextHandler);
		String result = handler.manejar(incidente);
		assertEquals("Escalado a plataforma", result);
	}

	@Test
	void testManejar_GravedadAlta_SinNextHandler() {
		SoporteCentroHandler handler = new SoporteCentroHandler();
		Incidente incidente = Mockito.mock(Incidente.class);
		Mockito.when(incidente.esGravedadLeve()).thenReturn(false);
		Mockito.when(incidente.getId()).thenReturn("I4");
		Mockito.when(incidente.getMotivo()).thenReturn("Motivo alta");
		Mockito.when(incidente.getGravedad()).thenReturn("alta");
		String result = handler.manejar(incidente);
		assertNotNull(result);
		assertTrue(result.toLowerCase().contains("no se pudo manejar"));
	}
}
