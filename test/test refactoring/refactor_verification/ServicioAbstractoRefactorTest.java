package refactor_verification;

import servicios.GuarderiaGrande; // Concrete implementation needed
import dominio.Mascota;
import interfaces.IServicioBase;
import strategies.CompatibilidadStrategyBasica;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Speculative Generality en ServicioAbstracto
 */
@DisplayName("Verificación Refactorización: Speculative Generality")
class ServicioAbstractoRefactorTest {

    @Test
    @DisplayName("Verificar firma esCompatible(Mascota)")
    void probarFirmaTipada() {
        // ENTRADA: Mascota especifica
        Mascota mascota = new Mascota("M1", "Test", "Perro", "Raza", "Tam", 1, "");
        GuarderiaGrande servicio = new GuarderiaGrande(); // Hereda de ServicioAbstracto
        
        // ACCIÓN: Llamar a esCompatible pasando una Mascota
        
        boolean resultado = servicio.esCompatible(mascota, new CompatibilidadStrategyBasica());
        
        // SALIDA ESPERADA:
        assertTrue(resultado, "Debe aceptar y procesar correctamente el objeto Mascota");
        
        
    }
}
