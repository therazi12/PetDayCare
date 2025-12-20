package adapters;

import interfaces.IPagoProcessor;
import java.util.UUID;
import valueobjects.Money;

/**
 * Adaptador para procesar pagos a través de Stripe.
 * Implementa el patrón Adapter para integrar la API de Stripe.
 */
public class StripeAdapter implements IPagoProcessor {
    private String apiKey;
    private String direccionServidor;

    public StripeAdapter(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("La API key no puede ser null o vacía");
        }
        this.apiKey = apiKey;
        this.direccionServidor = "https://api.stripe.com"; // Por defecto producción
    }

    public StripeAdapter(String apiKey, String direccionServidor) {
        this(apiKey);
        if (direccionServidor != null && !direccionServidor.trim().isEmpty()) {
            this.direccionServidor = direccionServidor;
        }
    }

    @Override
    public String autorizar(Money monto, String referencia) {
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser null");
        }
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new IllegalArgumentException("La referencia no puede ser null o vacía");
        }
        
        // Simulación de autorización con Stripe
        // En producción, aquí se haría la llamada real a la API de Stripe
        System.out.println("  [Stripe] Conectando a " + direccionServidor);
        System.out.println("  [Stripe] Creando intención de pago de " + monto.toString() + " para referencia: " + referencia);
        
        // Simular autenticación
        autenticar();
        
        // Simular creación de PaymentIntent
        String referenciaStripe = "pi_" + UUID.randomUUID().toString().substring(0, 24).replace("-", "");
        
        System.out.println("  [Stripe] PaymentIntent creado. ID: " + referenciaStripe);
        return referenciaStripe;
    }

    @Override
    public void capturar(String referencia) {
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new IllegalArgumentException("La referencia no puede ser null o vacía");
        }
        
        // Simulación de captura con Stripe
        System.out.println("  [Stripe] Confirmando PaymentIntent: " + referencia);
        
        // Simular autenticación
        autenticar();
        
        // Simular confirmación
        System.out.println("  [Stripe] PaymentIntent confirmado exitosamente");
    }

    @Override
    public void reembolsar(String referencia, Money monto) {
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new IllegalArgumentException("La referencia no puede ser null o vacía");
        }
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser null");
        }
        
        // Simulación de reembolso con Stripe
        System.out.println("  [Stripe] Creando reembolso de " + monto.toString() + " para charge: " + referencia);
        
        // Simular autenticación
        autenticar();
        
        // Simular creación de reembolso
        String refundId = "re_" + UUID.randomUUID().toString().substring(0, 24).replace("-", "");
        System.out.println("  [Stripe] Reembolso creado. ID: " + refundId);
    }

    /**
     * Simula la autenticación con Stripe.
     * En producción, esto haría una llamada real a la API de Stripe.
     */
    private void autenticar() {
        System.out.println("  [Stripe] Autenticando con API Key: " + apiKey.substring(0, Math.min(8, apiKey.length())) + "...");
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDireccionServidor() {
        return direccionServidor;
    }
}

