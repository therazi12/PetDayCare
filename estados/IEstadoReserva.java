package estados;


public interface IEstadoReserva {
    String obtenerNombreEstado();
    void manejarConfirmacion(Reserva reserva);
    void manejarCancelacion(Reserva reserva, String motivo);
}
