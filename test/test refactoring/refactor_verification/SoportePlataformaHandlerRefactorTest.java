package refactor_verification;

import incidentes.Incidente;
import incidentes.SoportePlataformaHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Duplicate Code en Handlers
 */
@DisplayName("Verificación Refactorización: Duplicate Code en Handlers")
class SoportePlataformaHandlerRefactorTest {

    @Test
    @DisplayName("Verificar manejo estandarizado (Helper Method)")
    void probarManejoAlta() {
        // ENTRADA: Incidente con gravedad alta
        Incidente incidente = new Incidente("I2", "Grave", Incidente.GRAVEDAD_ALTA, "R2");
        SoportePlataformaHandler handler = new SoportePlataformaHandler();
        
        // ACCIÓN: Manejar el incidente
        String resultado = handler.manejar(incidente);
        
        // SALIDA ESPERADA:
        // Debe usar el formato estandarizado definido en BaseHandlerIncidente.formatearRespuesta

        // CONDICIÓN DE ÉXITO:
        assertNotNull(resultado);
        assertTrue(resultado.contains("SOPORTE PLATAFORMA"), "El encabezado debe estar correcto");
        // Verifica elementos del formato estándar
        assertTrue(resultado.contains("manejado por soporte plataforma"), "Debe contener el texto del helper formatearRespuesta");
        

    }
}
