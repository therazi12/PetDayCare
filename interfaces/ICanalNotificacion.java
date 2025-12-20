package interfaces;

import dominio.Usuario;

/**
 * Interfaz que define un canal de notificación.
 * Se implementará completamente en la Parte 4.
 */
public interface ICanalNotificacion {
    /**
     * Envía un mensaje al usuario a través de este canal.
     * @param mensaje El mensaje a enviar
     * @param destinatario El usuario destinatario
     */
    void enviarMensaje(String mensaje, Usuario destinatario);
}

