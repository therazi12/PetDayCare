package factories;

import interfaces.ICentroFactory;
import interfaces.IServicioBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las factories de centros.
 */
@DisplayName("Pruebas para Factories de Centros")
class CentroFactoryTest {

    @Test
    @DisplayName("CentroGrandeFactory crea todos los servicios")
    void centroGrandeFactoryCreaServicios() {
        ICentroFactory factory = new CentroGrandeFactory();
        
        assertNotNull(factory.crearGuarderia());
        assertNotNull(factory.crearHospedaje());
        assertNotNull(factory.crearPaseo());
        assertNotNull(factory.crearEntrenamiento());
        assertNotNull(factory.crearBienestar());
    }

    @Test
    @DisplayName("CentroPequenaFactory crea todos los servicios")
    void centroPequenaFactoryCreaServicios() {
        ICentroFactory factory = new CentroPequenaFactory();
        
        assertNotNull(factory.crearGuarderia());
        assertNotNull(factory.crearHospedaje());
        assertNotNull(factory.crearPaseo());
        assertNotNull(factory.crearEntrenamiento());
        assertNotNull(factory.crearBienestar());
    }

    @Test
    @DisplayName("Servicios de CentroGrandeFactory implementan IServicioBase")
    void serviciosGrandeImplementanIServicioBase() {
        ICentroFactory factory = new CentroGrandeFactory();
        
        assertTrue(factory.crearGuarderia() instanceof IServicioBase);
        assertTrue(factory.crearHospedaje() instanceof IServicioBase);
        assertTrue(factory.crearPaseo() instanceof IServicioBase);
    }

    @Test
    @DisplayName("Servicios de CentroPequenaFactory implementan IServicioBase")
    void serviciosPequenaImplementanIServicioBase() {
        ICentroFactory factory = new CentroPequenaFactory();
        
        assertTrue(factory.crearGuarderia() instanceof IServicioBase);
        assertTrue(factory.crearHospedaje() instanceof IServicioBase);
        assertTrue(factory.crearPaseo() instanceof IServicioBase);
    }

    @Test
    @DisplayName("Servicios tienen nombres válidos")
    void serviciosTienenNombresValidos() {
        ICentroFactory factory = new CentroGrandeFactory();
        
        IServicioBase guarderia = factory.crearGuarderia();
        assertNotNull(guarderia.obtenerNombre());
        assertFalse(guarderia.obtenerNombre().isEmpty());
    }
}

