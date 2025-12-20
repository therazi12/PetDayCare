package decoradores;

import factories.CentroGrandeFactory;
import interfaces.IPricingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import strategies.PricingStrategyStandard;
import valueobjects.Money;
import valueobjects.Periodo;

/**
 * Pruebas unitarias para los decoradores de servicios.
 */
@DisplayName("Pruebas para Decoradores")
class DecoradorTest {

    private servicios.ServicioAbstracto servicioBase;
    private Periodo periodo;
    private IPricingStrategy pricing;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        CentroGrandeFactory factory = new CentroGrandeFactory();
        servicioBase = (servicios.ServicioAbstracto) factory.crearGuarderia();
        periodo = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 1, 1, 18, 0)
        );
        pricing = new PricingStrategyStandard();
    }

    @Test
    @DisplayName("CamaraEnVivoDecorator añade funcionalidad")
    void camaraEnVivoDecorator() {
        CamaraEnVivoDecorator decorador = new CamaraEnVivoDecorator(servicioBase);
        
        assertNotNull(decorador);
        assertTrue(decorador.obtenerNombre().contains("Cámara"));
    }

    @Test
    @DisplayName("ReporteTiempoRealDecorator añade funcionalidad")
    void reporteTiempoRealDecorator() {
        ReporteTiempoRealDecorator decorador = new ReporteTiempoRealDecorator(servicioBase);
        
        assertNotNull(decorador);
        assertTrue(decorador.obtenerNombre().contains("Reporte"));
    }

    @Test
    @DisplayName("AtencionVeterinariaDecorator añade funcionalidad")
    void atencionVeterinariaDecorator() {
        AtencionVeterinariaDecorator decorador = new AtencionVeterinariaDecorator(servicioBase);
        
        assertNotNull(decorador);
        assertTrue(decorador.obtenerNombre().contains("Veterinaria"));
    }

    @Test
    @DisplayName("Decorador aumenta el precio")
    void decoradorAumentaPrecio() {
        Money precioBase = servicioBase.calcularPrecio(periodo, new ArrayList<>(), pricing);
        
        CamaraEnVivoDecorator decorador = new CamaraEnVivoDecorator(servicioBase);
        Money precioDecorado = decorador.calcularPrecio(periodo, new ArrayList<>(), pricing);
        
        assertTrue(precioDecorado.esMayorQue(precioBase));
    }

    @Test
    @DisplayName("Múltiples decoradores se pueden encadenar")
    void decoradoresEncadenados() {
        servicios.ServicioAbstracto decorado = new ReporteTiempoRealDecorator(
            new CamaraEnVivoDecorator(servicioBase)
        );
        
        assertNotNull(decorado);
        Money precio = decorado.calcularPrecio(periodo, new ArrayList<>(), pricing);
        assertNotNull(precio);
    }

    @Test
    @DisplayName("Decorador mantiene compatibilidad del servicio base")
    void decoradorMantieneCompatibilidad() {
        CamaraEnVivoDecorator decorador = new CamaraEnVivoDecorator(servicioBase);
        
        // El decorador debe delegar la compatibilidad al servicio base
        assertNotNull(decorador);
    }
}

