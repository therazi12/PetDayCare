import factories.CentroGrandeFactory;
import interfaces.IServicioBase;
import servicios.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CentroGrandeFactoryTest {

	@Test
	void testCrearGuarderia() {
		CentroGrandeFactory factory = new CentroGrandeFactory();
		IServicioBase servicio = factory.crearGuarderia();
		assertNotNull(servicio);
		assertTrue(servicio instanceof GuarderiaGrande);
	}

	@Test
	void testCrearHospedaje() {
		CentroGrandeFactory factory = new CentroGrandeFactory();
		IServicioBase servicio = factory.crearHospedaje();
		assertNotNull(servicio);
		assertTrue(servicio instanceof HospedajeGrande);
	}

	@Test
	void testCrearPaseo() {
		CentroGrandeFactory factory = new CentroGrandeFactory();
		IServicioBase servicio = factory.crearPaseo();
		assertNotNull(servicio);
		assertTrue(servicio instanceof PaseoGrande);
	}

	@Test
	void testCrearEntrenamiento() {
		CentroGrandeFactory factory = new CentroGrandeFactory();
		IServicioBase servicio = factory.crearEntrenamiento();
		assertNotNull(servicio);
		assertTrue(servicio instanceof EntrenamientoGrande);
	}

	@Test
	void testCrearBienestar() {
		CentroGrandeFactory factory = new CentroGrandeFactory();
		IServicioBase servicio = factory.crearBienestar();
		assertNotNull(servicio);
		assertTrue(servicio instanceof BienestarGrande);
	}
}
