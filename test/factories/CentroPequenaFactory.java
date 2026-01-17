import factories.CentroPequenaFactory;
import interfaces.IServicioBase;
import servicios.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CentroPequenaFactoryTest {

	@Test
	void testCrearGuarderia() {
		CentroPequenaFactory factory = new CentroPequenaFactory();
		IServicioBase servicio = factory.crearGuarderia();
		assertNotNull(servicio);
		assertTrue(servicio instanceof GuarderiaPequena);
	}

	@Test
	void testCrearHospedaje() {
		CentroPequenaFactory factory = new CentroPequenaFactory();
		IServicioBase servicio = factory.crearHospedaje();
		assertNotNull(servicio);
		assertTrue(servicio instanceof HospedajePequena);
	}

	@Test
	void testCrearPaseo() {
		CentroPequenaFactory factory = new CentroPequenaFactory();
		IServicioBase servicio = factory.crearPaseo();
		assertNotNull(servicio);
		assertTrue(servicio instanceof PaseoPequena);
	}

	@Test
	void testCrearEntrenamiento() {
		CentroPequenaFactory factory = new CentroPequenaFactory();
		IServicioBase servicio = factory.crearEntrenamiento();
		assertNotNull(servicio);
		assertTrue(servicio instanceof EntrenamientoPequena);
	}

	@Test
	void testCrearBienestar() {
		CentroPequenaFactory factory = new CentroPequenaFactory();
		IServicioBase servicio = factory.crearBienestar();
		assertNotNull(servicio);
		assertTrue(servicio instanceof BienestarPequena);
	}
}
