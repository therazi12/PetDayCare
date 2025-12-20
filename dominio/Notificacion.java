package dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Clase que representa una notificación en el sistema PetDayCare.
 */
public class Notificacion {
    private String id;
    private String mensaje;
    private LocalDateTime fechaEnvio;

    public Notificacion(String id, String mensaje) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser null o vacío");
        }
        
        this.id = id;
        this.mensaje = mensaje;
        this.fechaEnvio = LocalDateTime.now();
    }

    public Notificacion(String id, String mensaje, LocalDateTime fechaEnvio) {
        this(id, mensaje);
        if (fechaEnvio != null) {
            this.fechaEnvio = fechaEnvio;
        }
    }

    /**
     * Obtiene los datos de la notificación como string.
     */
    public String getDatos() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("Notificación #%s - Fecha: %s - Mensaje: %s",
                id, fechaEnvio.format(formatter), mensaje);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    // Setters
    public void setMensaje(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede ser null o vacío");
        }
        this.mensaje = mensaje;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        if (fechaEnvio == null) {
            throw new IllegalArgumentException("La fecha de envío no puede ser null");
        }
        this.fechaEnvio = fechaEnvio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Notificacion that = (Notificacion) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getDatos();
    }
}

