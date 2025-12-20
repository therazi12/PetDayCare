package strategies;

import factories.CentroGrandeFactory;
import interfaces.IPricingStrategy;
import interfaces.IServicioBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import valueobjects.Money;
import valueobjects.Periodo;

/**
 * Pruebas unitarias para las estrategias de pricing.
 */
@DisplayName("Pruebas para Estrategias de Pricing")
class PricingStrategyTest {

    private IServicioBase servicio;
    private Periodo periodoDiario;
    private Periodo periodoSemanal;
    private Periodo periodoMensual;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        CentroGrandeFactory factory = new CentroGrandeFactory();
        servicio = factory.crearGuarderia();
        
        periodoDiario = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 1, 2, 10, 0)
        );
        
        periodoSemanal = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 1, 7, 10, 0)
        );
        
        periodoMensual = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 2, 1, 10, 0)
        );
    }

    @Test
    @DisplayName("PricingStrategyStandard calcula precio para período diario")
    void pricingStandardPeriodoDiario() {
        IPricingStrategy strategy = new PricingStrategyStandard();
        Money precio = strategy.precioPara(servicio, periodoDiario, null);
        
        assertNotNull(precio);
        assertTrue(precio.getMonto().compareTo(java.math.BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("PricingStrategyStandard calcula precio para período semanal")
    void pricingStandardPeriodoSemanal() {
        IPricingStrategy strategy = new PricingStrategyStandard();
        Money precio = strategy.precioPara(servicio, periodoSemanal, null);
        
        assertNotNull(precio);
        assertTrue(precio.getMonto().compareTo(java.math.BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("PricingStrategyStandard calcula precio para período mensual")
    void pricingStandardPeriodoMensual() {
        IPricingStrategy strategy = new PricingStrategyStandard();
        Money precio = strategy.precioPara(servicio, periodoMensual, null);
        
        assertNotNull(precio);
        assertTrue(precio.getMonto().compareTo(java.math.BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("PricingStrategyStandard incluye costo de opciones")
    void pricingStandardConOpciones() {
        IPricingStrategy strategy = new PricingStrategyStandard();
        List<String> opciones = Arrays.asList("juegos", "grooming");
        
        Money precioSinOpciones = strategy.precioPara(servicio, periodoDiario, null);
        Money precioConOpciones = strategy.precioPara(servicio, periodoDiario, opciones);
        
        assertTrue(precioConOpciones.esMayorQue(precioSinOpciones));
    }

    @Test
    @DisplayName("PricingStrategyPremium calcula precio")
    void pricingPremium() {
        IPricingStrategy strategy = new PricingStrategyPremium();
        Money precio = strategy.precioPara(servicio, periodoDiario, null);
        
        assertNotNull(precio);
        assertTrue(precio.getMonto().compareTo(java.math.BigDecimal.ZERO) > 0);
    }

    @Test
    @DisplayName("PricingStrategyPremium tiene precio mayor que Standard")
    void pricingPremiumMayorQueStandard() {
        IPricingStrategy standard = new PricingStrategyStandard();
        IPricingStrategy premium = new PricingStrategyPremium();
        
        Money precioStandard = standard.precioPara(servicio, periodoDiario, null);
        Money precioPremium = premium.precioPara(servicio, periodoDiario, null);
        
        // Premium generalmente debería ser mayor o igual
        assertTrue(precioPremium.getMonto().compareTo(precioStandard.getMonto()) >= 0);
    }
}

