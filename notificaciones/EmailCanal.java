package notificaciones;

import dominio.Usuario;
import interfaces.ICanalNotificacion;

/**
 * Implementación de canal de notificación por email.
 */
public class EmailCanal implements ICanalNotificacion {
    private String direccionServidorSMTP;

    public EmailCanal(String direccionServidorSMTP) {
        if (direccionServidorSMTP == null || direccionServidorSMTP.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección del servidor SMTP no puede ser null o vacía");
        }
        this.direccionServidorSMTP = direccionServidorSMTP;
    }

    @Override
    public void enviarMensaje(String mensaje, Usuario destinatario) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser null o vacío");
        }
        if (destinatario == null) {
            throw new IllegalArgumentException("El destinatario no puede ser null");
        }
        if (destinatario.getEmail() == null || destinatario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El destinatario debe tener un email válido");
        }
        
        // Simulación de envío de email
        // En producción, aquí se usaría JavaMail o similar para enviar el email real
        System.out.println("  [Email] Conectando al servidor SMTP: " + direccionServidorSMTP);
        System.out.println("  [Email] Enviando email a: " + destinatario.getEmail());
        System.out.println("  [Email] Asunto: Notificación PetDayCare");
        System.out.println("  [Email] Mensaje: " + mensaje);
        System.out.println("  [Email] Email enviado exitosamente");
    }

    public String getDireccionServidorSMTP() {
        return direccionServidorSMTP;
    }

    public void setDireccionServidorSMTP(String direccionServidorSMTP) {
        if (direccionServidorSMTP == null || direccionServidorSMTP.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección del servidor SMTP no puede ser null o vacía");
        }
        this.direccionServidorSMTP = direccionServidorSMTP;
    }
}

