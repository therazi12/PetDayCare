package refactor_verification;

import servicios.GuarderiaGrande;
import servicios.GuarderiaPequena;
import dominio.Mascota;
import strategies.CompatibilidadStrategyBasica;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Duplicate Code en Guarderias
 */
@DisplayName("Verificación Refactorización: Duplicate Code en Guarderías")
class GuarderiaRefactorTest {

    @Test
    @DisplayName("Verificar lógica pull-up en GuarderiaGrande")
    void probarGuarderiaGrande() {
        // ENTRADA: Guardería Grande (admite Perros, Gatos, Aves)
        GuarderiaGrande servicio = new GuarderiaGrande();
        Mascota mascota = new Mascota("M1", "Pili", "Ave", "Loro", "Pequeño", 2, "");
        
        // ACCIÓN: Verificar compatibilidad usando el método heredado
        boolean esCompatible = servicio.esCompatible(mascota, new CompatibilidadStrategyBasica());
        
        // SALIDA ESPERADA: true
        assertTrue(esCompatible, "GuarderiaGrande debería ser compatible con Ave (lógica heredada)");
    }

    @Test
    @DisplayName("Verificar lógica pull-up en GuarderiaPequena")
    void probarGuarderiaPequena() {
        // ENTRADA: Guardería Pequeña (solo Perros, Gatos - NO Aves)
        GuarderiaPequena servicio = new GuarderiaPequena();
        Mascota mascota = new Mascota("M2", "Pili", "Ave", "Loro", "Pequeño", 2, "");
        
        // ACCIÓN: Verificar compatibilidad
        boolean esCompatible = servicio.esCompatible(mascota, new CompatibilidadStrategyBasica());
        
        // SALIDA ESPERADA: false
        assertFalse(esCompatible, "GuarderiaPequena NO debería ser compatible con Ave (lógica heredada correcta)");
        
    }
}
