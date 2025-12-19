package incidentes;


public class Incidente {
    private String id;
    private String motivo;
    private String gravedad;
    private String reservaId;

    public Incidente(String id, String motivo, String gravedad, String reservaId) {
        this.id = id;
        this.motivo = motivo;
        this.gravedad = gravedad;
        this.reservaId = reservaId;
    }

    public void reportar() {
        System.out.println("  [Incidente] Reportando incidente #" + id + 
                          " - Gravedad: " + gravedad + 
                          " - Motivo: " + motivo);
    }

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

    @Override
    public String toString() {
        return String.format("Incidente #%s - Gravedad: %s, Motivo: %s, Reserva: %s",
                id, gravedad, motivo, reservaId);
    }
}
