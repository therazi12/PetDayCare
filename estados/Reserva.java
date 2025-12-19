package estados;

import servicios.ServicioAbstracto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Reserva {
    private String id;
    private String clienteNombre;
    private String mascotaNombre;
    private ServicioAbstracto servicio;
    private LocalDateTime fechaReserva;
    private IEstadoReserva estadoActual;

    public Reserva(String id, String clienteNombre, String mascotaNombre, ServicioAbstracto servicio) {
        this.id = id;
        this.clienteNombre = clienteNombre;
        this.mascotaNombre = mascotaNombre;
        this.servicio = servicio;
        this.fechaReserva = LocalDateTime.now();
        // Estado inicial: Pendiente
        this.estadoActual = new EstadoReservaPendiente();
    }

    public void setEstado(IEstadoReserva nuevoEstado) {
        this.estadoActual = nuevoEstado;
        System.out.println("  [Estado] Reserva " + id + " cambió a: " + nuevoEstado.obtenerNombreEstado());
    }

    public void confirmar() {
        estadoActual.manejarConfirmacion(this);
    }

    public void cancelar(String motivo) {
        estadoActual.manejarCancelacion(this, motivo);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getMascotaNombre() {
        return mascotaNombre;
    }

    public ServicioAbstracto getServicio() {
        return servicio;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public IEstadoReserva getEstadoActual() {
        return estadoActual;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Reserva #%s - Cliente: %s, Mascota: %s, Servicio: %s, Estado: %s, Fecha: %s",
                id, clienteNombre, mascotaNombre, servicio.getNombre(), 
                estadoActual.obtenerNombreEstado(), fechaReserva.format(formatter));
    }
}
