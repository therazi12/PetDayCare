package dominio;

import interfaces.IPagoProcessor;
import java.util.Objects;
import valueobjects.Money;


public class Pago {
    private String id;
    private String reservaId;
    private Money monto;
    // Constantes de estado
    public static final String ESTADO_PENDIENTE = "pendiente";
    public static final String ESTADO_AUTORIZADO = "autorizado";
    public static final String ESTADO_CAPTURADO = "capturado";
    public static final String ESTADO_REEMBOLSADO = "reembolsado";
    public static final String ESTADO_FALLIDO = "fallido";

    public Pago(String id, String reservaId, Money monto) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (reservaId == null || reservaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de reserva no puede ser null o vacío");
        }
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser null");
        }
        if (monto.getMonto().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        
        this.id = id;
        this.reservaId = reservaId;
        this.monto = monto;
        this.estado = ESTADO_PENDIENTE;
        this.referenciaExterna = "";
    }


    public void autorizar(IPagoProcessor processor) {
        if (processor == null) {
            throw new IllegalArgumentException("El procesador de pagos no puede ser null");
        }
        if (!ESTADO_PENDIENTE.equals(estado)) {
            throw new IllegalStateException("Solo se pueden autorizar pagos en estado pendiente. Estado actual: " + estado);
        }
        
        try {
            String referencia = processor.autorizar(monto, "Reserva-" + reservaId);
            this.referenciaExterna = referencia;
            this.estado = ESTADO_AUTORIZADO;
            System.out.println("  [Pago] Pago #" + id + " autorizado. Referencia: " + referencia);
        } catch (Exception e) {
            this.estado = ESTADO_FALLIDO;
            System.out.println("  [Pago] Error al autorizar pago #" + id + ": " + e.getMessage());
            throw new RuntimeException("Error al autorizar el pago", e);
        }
    }


    public void capturar(IPagoProcessor processor) {
        if (processor == null) {
            throw new IllegalArgumentException("El procesador de pagos no puede ser null");
        }
        if (!ESTADO_AUTORIZADO.equals(estado)) {
            throw new IllegalStateException("Solo se pueden capturar pagos autorizados. Estado actual: " + estado);
        }
        if (referenciaExterna == null || referenciaExterna.trim().isEmpty()) {
            throw new IllegalStateException("No hay referencia externa para capturar");
        }
        
        try {
            processor.capturar(referenciaExterna);
            this.estado = ESTADO_CAPTURADO;
            System.out.println("  [Pago] Pago #" + id + " capturado exitosamente");
        } catch (Exception e) {
            this.estado = ESTADO_FALLIDO;
            System.out.println("  [Pago] Error al capturar pago #" + id + ": " + e.getMessage());
            throw new RuntimeException("Error al capturar el pago", e);
        }
    }

 
    public void reembolsar(IPagoProcessor processor) {
        if (processor == null) {
            throw new IllegalArgumentException("El procesador de pagos no puede ser null");
        }
        if (!ESTADO_CAPTURADO.equals(estado)) {
            throw new IllegalStateException("Solo se pueden reembolsar pagos capturados. Estado actual: " + estado);
        }
        if (referenciaExterna == null || referenciaExterna.trim().isEmpty()) {
            throw new IllegalStateException("No hay referencia externa para reembolsar");
        }
        
        try {
            processor.reembolsar(referenciaExterna, monto);
            this.estado = ESTADO_REEMBOLSADO;
            System.out.println("  [Pago] Pago #" + id + " reembolsado exitosamente");
        } catch (Exception e) {
            System.out.println("  [Pago] Error al reembolsar pago #" + id + ": " + e.getMessage());
            throw new RuntimeException("Error al reembolsar el pago", e);
        }
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getReservaId() {
        return reservaId;
    }

    public Money getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    // Setters
    public void setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede ser null o vacío");
        }
        this.estado = estado.toLowerCase().trim();
    }

    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna != null ? referenciaExterna : "";
    }


    public boolean estaCompletado() {
        return ESTADO_CAPTURADO.equals(estado);
    }


    public boolean estaAutorizado() {
        return ESTADO_AUTORIZADO.equals(estado);
    }

    public boolean fallo() {
        return ESTADO_FALLIDO.equals(estado);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pago pago = (Pago) obj;
        return Objects.equals(id, pago.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Pago[id=%s, reservaId=%s, monto=%s, estado=%s, referencia=%s]",
                id, reservaId, monto.toString(), estado, referenciaExterna);
    }
}


