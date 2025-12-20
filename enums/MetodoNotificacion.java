package enums;

/**
 * Enum que representa los métodos de notificación disponibles para los usuarios.
 */
public enum MetodoNotificacion {
    EMAIL("Email"),
    SMS("SMS"),
    MENSAJERIA("Mensajería"),
    PUSH("Notificación Push"),
    TODOS("Todos los métodos");

    private final String descripcion;

    MetodoNotificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

