package notificaciones;

import dominio.Notificacion;
import dominio.Usuario;
import enums.MetodoNotificacion;
import interfaces.ICanalNotificacion;
import interfaces.INotifier;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Implementación del servicio de notificaciones.
 * Selecciona automáticamente el canal según las preferencias del usuario.
 */
public class Notificador implements INotifier {
    private Map<MetodoNotificacion, ICanalNotificacion> canales;
    private ICanalNotificacion canalEmail;
    private ICanalNotificacion canalMensajeria;

    public Notificador(ICanalNotificacion canalEmail, ICanalNotificacion canalMensajeria) {
        if (canalEmail == null) {
            throw new IllegalArgumentException("El canal de email no puede ser null");
        }
        if (canalMensajeria == null) {
            throw new IllegalArgumentException("El canal de mensajería no puede ser null");
        }
        
        this.canalEmail = canalEmail;
        this.canalMensajeria = canalMensajeria;
        this.canales = new HashMap<>();
        
        // Mapear canales según método de notificación
        this.canales.put(MetodoNotificacion.EMAIL, canalEmail);
        this.canales.put(MetodoNotificacion.SMS, canalMensajeria);
        this.canales.put(MetodoNotificacion.MENSAJERIA, canalMensajeria);
        this.canales.put(MetodoNotificacion.PUSH, canalMensajeria); // Por ahora usamos mensajería para push
    }

    @Override
    public void enviar(Usuario destinatario, ICanalNotificacion canal, String mensaje) {
        if (destinatario == null) {
            throw new IllegalArgumentException("El destinatario no puede ser null");
        }
        if (canal == null) {
            throw new IllegalArgumentException("El canal no puede ser null");
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser null o vacío");
        }
        
        // Crear notificación
        Notificacion notificacion = new Notificacion(
            UUID.randomUUID().toString().substring(0, 8),
            mensaje
        );
        
        // Enviar a través del canal especificado
        canal.enviarMensaje(mensaje, destinatario);
        
        System.out.println("  [Notificador] Notificación #" + notificacion.getId() + " enviada a " + destinatario.getNombre());
    }

    /**
     * Envía una notificación usando el método preferido del usuario.
     */
    public void enviarSegunPreferencia(Usuario destinatario, String mensaje) {
        if (destinatario == null) {
            throw new IllegalArgumentException("El destinatario no puede ser null");
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser null o vacío");
        }
        
        MetodoNotificacion metodoPreferido = destinatario.getMedioPreferidoNotificacion();
        ICanalNotificacion canal = canales.get(metodoPreferido);
        
        if (canal == null) {
            // Si no hay canal específico, usar email por defecto
            canal = canalEmail;
            System.out.println("  [Notificador] Método " + metodoPreferido + " no disponible, usando EMAIL por defecto");
        }
        
        enviar(destinatario, canal, mensaje);
    }

    /**
     * Envía una notificación a través de todos los canales disponibles.
     */
    public void enviarATodosLosCanales(Usuario destinatario, String mensaje) {
        if (destinatario == null) {
            throw new IllegalArgumentException("El destinatario no puede ser null");
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser null o vacío");
        }
        
        System.out.println("  [Notificador] Enviando notificación a todos los canales disponibles");
        enviar(destinatario, canalEmail, mensaje);
        enviar(destinatario, canalMensajeria, mensaje);
    }

    public Map<MetodoNotificacion, ICanalNotificacion> getCanales() {
        return new HashMap<>(canales); // Retornar copia para inmutabilidad
    }
}

