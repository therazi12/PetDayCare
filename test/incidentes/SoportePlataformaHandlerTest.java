import incidentes.SoportePlataformaHandler;
import incidentes.BaseHandlerIncidente;
import incidentes.Incidente;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class SoportePlataformaHandlerTest {

	@Test
	void testManejar_GravedadAlta() {
		SoportePlataformaHandler handler = new SoportePlataformaHandler();
		Incidente incidente = Mockito.mock(Incidente.class);
		Mockito.when(incidente.getGravedad()).thenReturn("alta");
		Mockito.when(incidente.getId()).thenReturn("I5");
		Mockito.when(incidente.getMotivo()).thenReturn("Motivo crítico");
		String result = handler.manejar(incidente);
		assertNotNull(result);
		assertTrue(result.contains("SOPORTE PLATAFORMA"));
		assertTrue(result.contains("Intervención directa"));
	}

	@Test
	void testManejar_GravedadNoAlta_ConNextHandler() {
		SoportePlataformaHandler handler = new SoportePlataformaHandler();
		Incidente incidente = Mockito.mock(Incidente.class);
		Mockito.when(incidente.getGravedad()).thenReturn("media");
		Mockito.when(incidente.getId()).thenReturn("I6");
		Mockito.when(incidente.getMotivo()).thenReturn("Motivo media");
		BaseHandlerIncidente nextHandler = Mockito.mock(BaseHandlerIncidente.class);
		Mockito.when(nextHandler.manejar(incidente)).thenReturn("Escalado a otro handler");
		handler.setNextHandler(nextHandler);
		String result = handler.manejar(incidente);
		assertEquals("Escalado a otro handler", result);
	}

	@Test
	void testManejar_GravedadNoAlta_SinNextHandler() {
		SoportePlataformaHandler handler = new SoportePlataformaHandler();
		Incidente incidente = Mockito.mock(Incidente.class);
		Mockito.when(incidente.getGravedad()).thenReturn("baja");
		Mockito.when(incidente.getId()).thenReturn("I7");
		Mockito.when(incidente.getMotivo()).thenReturn("Motivo baja");
		String result = handler.manejar(incidente);
		assertNotNull(result);
		assertTrue(result.toLowerCase().contains("no se pudo manejar"));
	}
}
