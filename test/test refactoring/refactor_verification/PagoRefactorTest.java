package refactor_verification;

import dominio.Pago;
import valueobjects.Money;
import adapters.PayPalAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Primitive Obsession en Pago.java
 */
@DisplayName("Verificación Refactorización: Primitive Obsession en Pago")
class PagoRefactorTest {

    @Test
    @DisplayName("Verificar constantes de estado y transiciones")
    void probarEstadosPago() {
        // ENTRADA: Creamos un Pago nuevo
        Pago pago = new Pago("P1", "R1", Money.usd(100.0));
        
        // El estado inicial debe ser ESTADO_PENDIENTE
        assertEquals(Pago.ESTADO_PENDIENTE, pago.getEstado(), "El estado inicial debe ser PENDIENTE");
        
        // ACCIÓN: Autorizar el pago
        pago.autorizar(new PayPalAdapter("k", "s"));
        
        // SALIDA ESPERADA:
        // El estado debe cambiar a ESTADO_AUTORIZADO
        assertEquals(Pago.ESTADO_AUTORIZADO, pago.getEstado(), "El estado debe cambiar a AUTORIZADO después de autorizar");
        assertTrue(pago.estaAutorizado(), "El método helper estaAutorizado() debe retornar true");
        
    }
}
