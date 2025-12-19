package estados;


public class EstadoReservaConfirmada implements IEstadoReserva {

    @Override
    public String obtenerNombreEstado() {
        return "Confirmada";
    }

    @Override
    public void manejarConfirmacion(Reserva reserva) {
        System.out.println("  [Estado] La reserva #" + reserva.getId() + " ya está confirmada.");
    }

    @Override
    public void manejarCancelacion(Reserva reserva, String motivo) {
        System.out.println("  [Estado] Cancelando reserva confirmada #" + reserva.getId() + " - Motivo: " + motivo);
        System.out.println("  [Estado] Se aplicará política de cancelación con penalización");
        reserva.setEstado(new EstadoReservaCancelada());
    }
}
