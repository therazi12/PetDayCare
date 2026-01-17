package refactor_verification;

import dominio.Mascota;
import interfaces.IServicioBase;
import strategies.CompatibilidadStrategyBasica;
import servicios.ServicioAbstracto;
import servicios.GuarderiaGrande;
import valueobjects.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Data Class en Mascota.java
 */
@DisplayName("Verificación Refactorización: Data Class en Mascota")
class MascotaRefactorTest {

    @Test
    @DisplayName("Verificar método esCompatibleCon movido a Mascota")
    void probarComportamientoMascota() {
        // ENTRADA: Creamos una Mascota y un Servicio
        Mascota mascota = new Mascota("M1", "Fido", "Perro", "Labrador", "Grande", 3, "");
        IServicioBase servicio = new GuarderiaGrande(); // Admite Perros, Gatos, Aves
        
        // ACCIÓN: Llamamos al nuevo método esCompatibleCon en Mascota
        boolean esCompatible = mascota.esCompatibleCon(servicio, new CompatibilidadStrategyBasica());
        
        // SALIDA ESPERADA:
        // Debe retornar true ya que la GuarderiaGrande admite Perros
        
        // CONDICIÓN DE ÉXITO:
        assertTrue(esCompatible, "Mascota.esCompatibleCon debería retornar true para Perro en GuarderiaGrande");
        
    
    }
}
