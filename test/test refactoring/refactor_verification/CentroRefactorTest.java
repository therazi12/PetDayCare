package refactor_verification;

import dominio.Centro;
import dominio.Guarderia;
import interfaces.IServicioBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Magic Strings en Centro.java
 */
@DisplayName("Verificación Refactorización: Magic Strings en Centro")
class CentroRefactorTest {

    @Test
    @DisplayName("Verificar uso de constantes en obtenerServicio")
    void probarConstantesServicio() {
        // ENTRADA: Creamos un Centro y solicitamos servicio usando la constante TIPO_GUARDERIA
        Centro centro = new Centro("C1", "Test Centro", "Dir", 100);
        centro.asignarFabrica(new factories.CentroGrandeFactory());
        
        IServicioBase servicio = centro.obtenerServicio(Centro.TIPO_GUARDERIA);
        
        // SALIDA ESPERADA:
        // 1. El servicio no debe ser nulo
        // 2. El servicio debe ser del tipo correcto 
        
        // CONDICIÓN DE APROBACIÓN:
        assertNotNull(servicio, "El servicio no debería ser null al usar la constante TIPO_GUARDERIA");
        assertTrue(servicio.obtenerNombre().contains("Guardería"), "El servicio debería ser una Guardería");
        
        // VERIFICACIÓN doble: Probando otra constante
        IServicioBase hospedaje = centro.obtenerServicio(Centro.TIPO_HOSPEDAJE);
        assertNotNull(hospedaje, "El servicio no debería ser null al usar la constante TIPO_HOSPEDAJE");
    }
}
