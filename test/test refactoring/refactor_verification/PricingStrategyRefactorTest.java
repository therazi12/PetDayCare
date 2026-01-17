package refactor_verification;

import strategies.PricingStrategyPremium;
import servicios.GuarderiaGrande;
import valueobjects.Money;
import valueobjects.Periodo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Magic Numbers en PricingStrategyPremium
 */
@DisplayName("Verificación Refactorización: Magic Numbers en PricingStrategy")
class PricingStrategyRefactorTest {

    @Test
    @DisplayName("Verificar cálculo usando constantes (Semanal)")
    void probarCalculoSemanal() {
        // ENTRADA: Servicio de $40/día y periodo semanal (5 días hábiles, lógica del sistema)
        // PricingStrategyPremium aplica MULTIPLICADOR_SEMANAL = 0.85
        GuarderiaGrande servicio = new GuarderiaGrande(); // precioBase = 40
        PricingStrategyPremium estrategia = new PricingStrategyPremium();
        
        LocalDateTime inicio = LocalDateTime.of(2024, 1, 1, 10, 0); // Lunes
        LocalDateTime fin = LocalDateTime.of(2024, 1, 8, 10, 0);    // Lunes sgte (Semanal lógica)
        Periodo periodo = new Periodo(inicio, fin) {
            @Override
            public boolean esSemanal() { return true; } // Forzamos para test aislado
        };
        
        // ACCIÓN: Calcular precio
        // Fórmula esperada: precioBase * 5 * MULTIPLICADOR_SEMANAL (0.85)
        // 40 * 5 * 0.85 = 200 * 0.85 = 170.0
        Money precio = estrategia.precioPara(servicio, periodo, new ArrayList<>());
        
        // SALIDA ESPERADA: $170.0
        assertEquals(170.0, precio.getMonto().doubleValue(), 0.01, "El precio semanal debe aplicar el multiplicador 0.85");
    }

    @Test
    @DisplayName("Verificar constantes de opciones (Magic Numbers)")
    void probarCostoOpciones() {
        // ENTRADA: Opciones "suite" y "juegos"
        // PRECIO_SUITE = 20.0
        // PRECIO_JUEGOS = 8.0
        GuarderiaGrande servicio = new GuarderiaGrande();
        PricingStrategyPremium estrategia = new PricingStrategyPremium();
        
        Periodo periodo = new Periodo(LocalDateTime.now(), LocalDateTime.now().plusDays(1)) {
             @Override
             public boolean esDiario() { return true; }
        }; // Diario sin descuento extra (precioBase = 40)
        
        List<String> opciones = new ArrayList<>();
        opciones.add("suite");
        opciones.add("juegos");
        
        // ACCIÓN: Calcular
        // Base (40) + Suite (20) + Juegos (8) = 68.0
        Money precio = estrategia.precioPara(servicio, periodo, opciones);
        
        // SALIDA ESPERADA: 68.0
        
        assertEquals(68.0, precio.getMonto().doubleValue(), 0.01, "Debe sumar los precios constantes de opciones");
    }
}
