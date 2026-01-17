package refactor_verification;

import incidentes.Incidente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Primitive Obsession en Incidente.java
 */
@DisplayName("Verificación Refactorización: Primitive Obsession en Incidente")
class IncidenteRefactorTest {

    @Test
    @DisplayName("Verificar constantes y helpers de Incidente")
    void probarConstantesIncidente() {
        // ENTRADA: Crearemos un incidente con la constante GRAVEDAD_BAJA
        Incidente incidente = new Incidente("I1", "Motivo", Incidente.GRAVEDAD_BAJA, "Rel1");
        
        // VERIFICACIÓN 1: Constante asignada correctamente
        assertEquals("baja", incidente.getGravedad(), "El valor de GRAVEDAD_BAJA debe ser 'baja'");
        
        // ACCIÓN: Probar los helpers de lógica de negocio 
        boolean esLeve = incidente.esGravedadLeve();
        boolean esAlta = incidente.esGravedadAlta();
        
        // SALIDA ESPERADA:
        assertTrue(esLeve, "esGravedadLeve() debe ser true para baja");
        assertFalse(esAlta, "esGravedadAlta() debe ser false para baja");
        
        // VERIFICACIÓN 2: Crear un incidente grave
        Incidente grave = new Incidente("I2", "Grave", Incidente.GRAVEDAD_ALTA, "Rel2");
        assertFalse(grave.esGravedadLeve());
        assertTrue(grave.esGravedadAlta());
    }
}
