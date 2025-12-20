package incidentes;


public class Incidente {
    private String id;
    private String motivo;
    private String gravedad;
    private String reservaId;
    private String estado; // Estado del incidente: "pendiente", "en_proceso", "resuelto", "cerrado"

    public Incidente(String id, String motivo, String gravedad, String reservaId) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo no puede ser null o vacío");
        }
        if (gravedad == null || gravedad.trim().isEmpty()) {
            throw new IllegalArgumentException("La gravedad no puede ser null o vacía");
        }
        if (reservaId == null || reservaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de reserva no puede ser null o vacío");
        }
        
        this.id = id;
        this.motivo = motivo;
        this.gravedad = gravedad;
        this.reservaId = reservaId;
        this.estado = "pendiente"; // Estado inicial
    }

    public void reportar() {
        System.out.println("  [Incidente] Reportando incidente #" + id + 
                          " - Gravedad: " + gravedad + 
                          " - Motivo: " + motivo +
                          " - Estado: " + estado);
    }

    /**
     * Verifica si el incidente está resuelto.
     */
    public boolean esResuelto() {
        return "resuelto".equalsIgnoreCase(estado) || "cerrado".equalsIgnoreCase(estado);
    }

    /**
     * Marca el incidente como resuelto.
     */
    public void marcarComoResuelto() {
        this.estado = "resuelto";
        System.out.println("  [Incidente] Incidente #" + id + " marcado como resuelto");
    }

    /**
     * Marca el incidente como en proceso.
     */
    public void marcarComoEnProceso() {
        this.estado = "en_proceso";
        System.out.println("  [Incidente] Incidente #" + id + " marcado como en proceso");
    }

    /**
     * Cierra el incidente.
     */
    public void cerrar() {
        this.estado = "cerrado";
        System.out.println("  [Incidente] Incidente #" + id + " cerrado");
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getGravedad() {
        return gravedad;
    }

    public String getReservaId() {
        return reservaId;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede ser null o vacío");
        }
        this.estado = estado.toLowerCase().trim();
    }

    @Override
    public String toString() {
        return String.format("Incidente #%s - Gravedad: %s, Motivo: %s, Reserva: %s, Estado: %s",
                id, gravedad, motivo, reservaId, estado);
    }
}
