package adapters;

import interfaces.IPagoProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import valueobjects.Money;

/**
 * Pruebas unitarias para los adapters de pago.
 */
@DisplayName("Pruebas para Adapters de Pago")
class PagoAdapterTest {

    @Test
    @DisplayName("PayPalAdapter autoriza pago")
    void paypalAdapterAutoriza() {
        IPagoProcessor adapter = new PayPalAdapter("test-key", "test-secret");
        Money monto = Money.usd(100.0);
        
        String referencia = adapter.autorizar(monto, "test-123");
        assertNotNull(referencia);
        assertFalse(referencia.isEmpty());
    }

    @Test
    @DisplayName("PayPalAdapter captura pago")
    void paypalAdapterCaptura() {
        IPagoProcessor adapter = new PayPalAdapter("test-key", "test-secret");
        String referencia = adapter.autorizar(Money.usd(100.0), "test-123");
        
        assertDoesNotThrow(() -> {
            adapter.capturar(referencia);
        });
    }

    @Test
    @DisplayName("PayPalAdapter reembolsa pago")
    void paypalAdapterReembolsa() {
        IPagoProcessor adapter = new PayPalAdapter("test-key", "test-secret");
        String referencia = adapter.autorizar(Money.usd(100.0), "test-123");
        adapter.capturar(referencia);
        
        assertDoesNotThrow(() -> {
            adapter.reembolsar(referencia, Money.usd(50.0));
        });
    }

    @Test
    @DisplayName("StripeAdapter autoriza pago")
    void stripeAdapterAutoriza() {
        IPagoProcessor adapter = new StripeAdapter("test-key");
        Money monto = Money.usd(100.0);
        
        String referencia = adapter.autorizar(monto, "test-123");
        assertNotNull(referencia);
        assertFalse(referencia.isEmpty());
    }

    @Test
    @DisplayName("StripeAdapter captura pago")
    void stripeAdapterCaptura() {
        IPagoProcessor adapter = new StripeAdapter("test-key");
        String referencia = adapter.autorizar(Money.usd(100.0), "test-123");
        
        assertDoesNotThrow(() -> {
            adapter.capturar(referencia);
        });
    }

    @Test
    @DisplayName("StripeAdapter reembolsa pago")
    void stripeAdapterReembolsa() {
        IPagoProcessor adapter = new StripeAdapter("test-key");
        String referencia = adapter.autorizar(Money.usd(100.0), "test-123");
        adapter.capturar(referencia);
        
        assertDoesNotThrow(() -> {
            adapter.reembolsar(referencia, Money.usd(50.0));
        });
    }

    @Test
    @DisplayName("Lanzar excepción al capturar referencia inválida")
    void capturarReferenciaInvalida() {
        IPagoProcessor adapter = new PayPalAdapter();
        
        assertThrows(IllegalArgumentException.class, () -> {
            adapter.capturar("referencia-invalida");
        });
    }

    @Test
    @DisplayName("Lanzar excepción al reembolsar referencia inválida")
    void reembolsarReferenciaInvalida() {
        IPagoProcessor adapter = new StripeAdapter();
        
        assertThrows(IllegalArgumentException.class, () -> {
            adapter.reembolsar("referencia-invalida", Money.usd(50.0));
        });
    }
}

