package incidentes;


public class Incidente {
    private String id;
    private String motivo;
    private String gravedad;
    private String reservaId;
    private String estado; // Estado del incidente: "pendiente", "en_proceso", "resuelto", "cerrado"

    // Constantes de Gravedad
    public static final String GRAVEDAD_BAJA = "baja";
    public static final String GRAVEDAD_MEDIA = "media";
    public static final String GRAVEDAD_ALTA = "alta";
    
    // Constantes de Estado
    public static final String ESTADO_PENDIENTE = "pendiente";
    public static final String ESTADO_EN_PROCESO = "en_proceso";
    public static final String ESTADO_RESUELTO = "resuelto";
    public static final String ESTADO_CERRADO = "cerrado";

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
        this.estado = ESTADO_PENDIENTE; // Estado inicial
    }

    public void reportar() {
        System.out.println("  [Incidente] Reportando incidente #" + id + 
                          " - Gravedad: " + gravedad + 
                          " - Motivo: " + motivo +
                          " - Estado: " + estado);
    }

    
    public boolean esResuelto() {
        return ESTADO_RESUELTO.equalsIgnoreCase(estado) || ESTADO_CERRADO.equalsIgnoreCase(estado);
    }

    
    public void marcarComoResuelto() {
        this.estado = ESTADO_RESUELTO;
        System.out.println("  [Incidente] Incidente #" + id + " marcado como resuelto");
    }

    
    public void marcarComoEnProceso() {
        this.estado = ESTADO_EN_PROCESO;
        System.out.println("  [Incidente] Incidente #" + id + " marcado como en proceso");
    }

    
    public void cerrar() {
        this.estado = ESTADO_CERRADO;
        System.out.println("  [Incidente] Incidente #" + id + " cerrado");
    }
    
    
    public boolean esGravedadLeve() {
        return GRAVEDAD_BAJA.equalsIgnoreCase(gravedad) || GRAVEDAD_MEDIA.equalsIgnoreCase(gravedad);
    }
   
    public boolean esGravedadAlta() {
        return GRAVEDAD_ALTA.equalsIgnoreCase(gravedad);
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
