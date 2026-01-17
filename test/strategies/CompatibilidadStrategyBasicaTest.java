import strategies.CompatibilidadStrategyBasica;
import interfaces.IServicioBase;
import servicios.ServicioAbstracto;
import dominio.Mascota;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class CompatibilidadStrategyBasicaTest {

	@Test
	void testEsCompatible_MascotaPositiva() {
		CompatibilidadStrategyBasica strategy = new CompatibilidadStrategyBasica();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Mockito.when(servicio.getTiposMascotaAdmitidos()).thenReturn("Perros, Gatos");
		Mascota mascota = Mockito.mock(Mascota.class);
		Mockito.when(mascota.getEspecie()).thenReturn("Perros");
		assertTrue(strategy.esCompatible(servicio, mascota));
	}

	@Test
	void testEsCompatible_MascotaNegativa() {
		CompatibilidadStrategyBasica strategy = new CompatibilidadStrategyBasica();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Mockito.when(servicio.getTiposMascotaAdmitidos()).thenReturn("Perros");
		Mascota mascota = Mockito.mock(Mascota.class);
		Mockito.when(mascota.getEspecie()).thenReturn("Gatos");
		assertFalse(strategy.esCompatible(servicio, mascota));
	}

	@Test
	void testEsCompatible_StringPositiva() {
		CompatibilidadStrategyBasica strategy = new CompatibilidadStrategyBasica();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Mockito.when(servicio.getTiposMascotaAdmitidos()).thenReturn("Perros");
		assertTrue(strategy.esCompatible(servicio, "Perros"));
	}

	@Test
	void testEsCompatible_StringNegativa() {
		CompatibilidadStrategyBasica strategy = new CompatibilidadStrategyBasica();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Mockito.when(servicio.getTiposMascotaAdmitidos()).thenReturn("Perros");
		assertFalse(strategy.esCompatible(servicio, "Aves"));
	}

	@Test
	void testEsCompatible_ServicioNoCompatible() {
		CompatibilidadStrategyBasica strategy = new CompatibilidadStrategyBasica();
		IServicioBase servicio = Mockito.mock(IServicioBase.class); // No es ServicioAbstracto
		Mascota mascota = Mockito.mock(Mascota.class);
		assertFalse(strategy.esCompatible(servicio, mascota));
	}

	// Dummy class con getEspecie()
	static class MascotaDummy {
		public String getEspecie() { return "Perros"; }
	}

	@Test
	void testEsCompatible_ReflexionPositiva() {
		CompatibilidadStrategyBasica strategy = new CompatibilidadStrategyBasica();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Mockito.when(servicio.getTiposMascotaAdmitidos()).thenReturn("Perros");
		MascotaDummy dummy = new MascotaDummy();
		assertTrue(strategy.esCompatible(servicio, dummy));
	}

	// Dummy class sin getEspecie()
	static class SinGetEspecie {}

	@Test
	void testEsCompatible_ReflexionNegativa() {
		CompatibilidadStrategyBasica strategy = new CompatibilidadStrategyBasica();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Mockito.when(servicio.getTiposMascotaAdmitidos()).thenReturn("Perros");
		SinGetEspecie dummy = new SinGetEspecie();
		assertFalse(strategy.esCompatible(servicio, dummy));
	}
}
