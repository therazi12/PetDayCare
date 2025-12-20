package notificaciones;

import dominio.Usuario;
import interfaces.ICanalNotificacion;

/**
 * Implementación de canal de notificación por mensajería (SMS, WhatsApp, etc.).
 */
public class MensajeriaCanal implements ICanalNotificacion {
    private String endpointAPI;

    public MensajeriaCanal(String endpointAPI) {
        if (endpointAPI == null || endpointAPI.trim().isEmpty()) {
            throw new IllegalArgumentException("El endpoint de la API no puede ser null o vacío");
        }
        this.endpointAPI = endpointAPI;
    }

    @Override
    public void enviarMensaje(String mensaje, Usuario destinatario) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser null o vacío");
        }
        if (destinatario == null) {
            throw new IllegalArgumentException("El destinatario no puede ser null");
        }
        if (destinatario.getTelefono() == null || destinatario.getTelefono().trim().isEmpty()) {
            throw new IllegalArgumentException("El destinatario debe tener un teléfono válido");
        }
        
        // Simulación de envío de mensaje
        // En producción, aquí se haría una llamada HTTP real a la API de mensajería
        System.out.println("  [Mensajería] Conectando a API: " + endpointAPI);
        System.out.println("  [Mensajería] Enviando mensaje a: " + destinatario.getTelefono());
        System.out.println("  [Mensajería] Mensaje: " + mensaje);
        System.out.println("  [Mensajería] Mensaje enviado exitosamente");
    }

    public String getEndpointAPI() {
        return endpointAPI;
    }

    public void setEndpointAPI(String endpointAPI) {
        if (endpointAPI == null || endpointAPI.trim().isEmpty()) {
            throw new IllegalArgumentException("El endpoint de la API no puede ser null o vacío");
        }
        this.endpointAPI = endpointAPI;
    }
}

