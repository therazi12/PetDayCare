package adapters;

import interfaces.IPagoProcessor;
import java.util.UUID;
import valueobjects.Money;

/**
 * Adaptador para procesar pagos a través de PayPal.
 * Implementa el patrón Adapter para integrar la API de PayPal.
 */
public class PayPalAdapter implements IPagoProcessor {
    private String apiKey;
    private String apiSecret;
    private String direccionServidor;

    public PayPalAdapter(String apiKey, String apiSecret) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("La API key no puede ser null o vacía");
        }
        if (apiSecret == null || apiSecret.trim().isEmpty()) {
            throw new IllegalArgumentException("El API secret no puede ser null o vacío");
        }
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.direccionServidor = "https://api.paypal.com"; // Por defecto producción
    }

    public PayPalAdapter(String apiKey, String apiSecret, String direccionServidor) {
        this(apiKey, apiSecret);
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
        
        // Simulación de autorización con PayPal
        // En producción, aquí se haría la llamada real a la API de PayPal
        System.out.println("  [PayPal] Conectando a " + direccionServidor);
        System.out.println("  [PayPal] Autorizando pago de " + monto.toString() + " para referencia: " + referencia);
        
        // Simular autenticación
        String token = autenticar();
        
        // Simular autorización
        String referenciaPayPal = "PAYPAL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        System.out.println("  [PayPal] Pago autorizado. Referencia PayPal: " + referenciaPayPal);
        return referenciaPayPal;
    }

    @Override
    public void capturar(String referencia) {
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new IllegalArgumentException("La referencia no puede ser null o vacía");
        }
        
        // Simulación de captura con PayPal
        System.out.println("  [PayPal] Capturando pago con referencia: " + referencia);
        
        // Simular autenticación
        autenticar();
        
        // Simular captura
        System.out.println("  [PayPal] Pago capturado exitosamente");
    }

    @Override
    public void reembolsar(String referencia, Money monto) {
        if (referencia == null || referencia.trim().isEmpty()) {
            throw new IllegalArgumentException("La referencia no puede ser null o vacía");
        }
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser null");
        }
        
        // Simulación de reembolso con PayPal
        System.out.println("  [PayPal] Procesando reembolso de " + monto.toString() + " para referencia: " + referencia);
        
        // Simular autenticación
        autenticar();
        
        // Simular reembolso
        System.out.println("  [PayPal] Reembolso procesado exitosamente");
    }

    /**
     * Simula la autenticación con PayPal.
     * En producción, esto haría una llamada real a la API de PayPal.
     */
    private String autenticar() {
        System.out.println("  [PayPal] Autenticando con API Key: " + apiKey.substring(0, Math.min(8, apiKey.length())) + "...");
        return "paypal_token_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDireccionServidor() {
        return direccionServidor;
    }
}

