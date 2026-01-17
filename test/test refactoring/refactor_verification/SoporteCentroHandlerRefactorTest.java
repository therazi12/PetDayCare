package refactor_verification;

import incidentes.Incidente;
import incidentes.SoporteCentroHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Feature Envy en SoporteCentroHandler
 */
@DisplayName("Verificación Refactorización: Feature Envy en SoporteCentroHandler")
class SoporteCentroHandlerRefactorTest {

    @Test
    @DisplayName("Verificar manejo de gravedad leve")
    void probarManejoLeve() {
        // ENTRADA: Incidente con gravedad baja
        Incidente incidente = new Incidente("I1", "Leve", Incidente.GRAVEDAD_BAJA, "R1");
        SoporteCentroHandler handler = new SoporteCentroHandler();
        
        // ACCIÓN: Manejar el incidente
        // El handler ahora pregunta incidente.esGravedadLeve() en lugar de getGravedad()
        String resultado = handler.manejar(incidente);
        
        // SALIDA ESPERADA:
        // El formato debe indicar que fue manejado por SOPORTE CENTRO
        
        // CONDICIÓN DE ÉXITO:
        assertNotNull(resultado);
        assertTrue(resultado.contains("SOPORTE CENTRO"), "Debe ser manejado por soporte centro");
        assertTrue(resultado.contains("Resuelto localmente"), "La acción debe ser correcta");
        
    }
}
