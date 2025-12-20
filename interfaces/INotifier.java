package interfaces;

import dominio.Usuario;

/**
 * Interfaz que define el servicio de notificaciones.
 * Se implementará completamente en la Parte 4.
 */
public interface INotifier {
    /**
     * Envía una notificación al usuario usando el canal especificado.
     * @param destinatario El usuario destinatario
     * @param canal El canal de notificación a usar
     * @param mensaje El mensaje a enviar
     */
    void enviar(Usuario destinatario, ICanalNotificacion canal, String mensaje);
}

