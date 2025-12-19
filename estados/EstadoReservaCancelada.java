package estados;

public class EstadoReservaCancelada implements IEstadoReserva {

    @Override
    public String obtenerNombreEstado() {
        return "Cancelada";
    }

    @Override
    public void manejarConfirmacion(Reserva reserva) {
        System.out.println("  [Estado] ERROR: No se puede confirmar una reserva cancelada #" + reserva.getId());
        System.out.println("  [Estado] Debe crear una nueva reserva");
    }

    @Override
    public void manejarCancelacion(Reserva reserva, String motivo) {
        System.out.println("  [Estado] La reserva #" + reserva.getId() + " ya está cancelada.");
    }
}
