package estados;


public class EstadoReservaPendiente implements IEstadoReserva {

    @Override
    public String obtenerNombreEstado() {
        return "Pendiente";
    }

    @Override
    public void manejarConfirmacion(Reserva reserva) {
        System.out.println("  [Estado] Confirmando reserva pendiente #" + reserva.getId());
        reserva.setEstado(new EstadoReservaConfirmada());
    }

    @Override
    public void manejarCancelacion(Reserva reserva, String motivo) {
        System.out.println("  [Estado] Cancelando reserva pendiente #" + reserva.getId() + " - Motivo: " + motivo);
        reserva.setEstado(new EstadoReservaCancelada());
    }
}
