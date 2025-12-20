package dominio;

import estados.Reserva;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import valueobjects.Money;
import valueobjects.Porcentaje;

/**
 * Representa la política de cancelación de un centro.
 * Define las reglas para calcular penalidades por cancelación.
 */
public class PoliticaCancelacion {
    private String id;
    private String descripcion;
    private int ventanaGratisHoras; // Ventana de tiempo en horas para cancelar sin penalidad
    private Porcentaje penalidad; // Porcentaje de penalidad (ej: 0.15 para 15%)

    public PoliticaCancelacion(String id, String descripcion, int ventanaGratisHoras, Porcentaje penalidad) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser null o vacía");
        }
        if (ventanaGratisHoras < 0) {
            throw new IllegalArgumentException("La ventana gratis no puede ser negativa");
        }
        if (penalidad == null) {
            throw new IllegalArgumentException("La penalidad no puede ser null");
        }
        
        this.id = id;
        this.descripcion = descripcion;
        this.ventanaGratisHoras = ventanaGratisHoras;
        this.penalidad = penalidad;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getVentanaGratisHoras() {
        return ventanaGratisHoras;
    }

    public Porcentaje getPenalidad() {
        return penalidad;
    }

    /**
     * Calcula la penalidad por cancelación basándose en la reserva y la fecha de cancelación.
     * 
     * @param reserva La reserva que se está cancelando
     * @param fechaCancelacion La fecha en que se cancela la reserva
     * @return El monto de la penalidad a aplicar, o Money.zero() si está dentro de la ventana gratis
     */
    public Money calcularPenalidad(Reserva reserva, LocalDateTime fechaCancelacion) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva no puede ser null");
        }
        if (fechaCancelacion == null) {
            throw new IllegalArgumentException("La fecha de cancelación no puede ser null");
        }
        
        // Obtener el período de la reserva
        var periodo = reserva.getPeriodo();
        if (periodo == null) {
            return Money.usd(0);
        }
        
        // Calcular horas hasta el inicio de la reserva
        long horasHastaInicio = ChronoUnit.HOURS.between(fechaCancelacion, periodo.getInicio());
        
        // Si está dentro de la ventana gratis, no hay penalidad
        if (horasHastaInicio >= ventanaGratisHoras) {
            return Money.usd(0);
        }
        
        // Calcular la penalidad sobre el total de la reserva
        Money totalReserva = reserva.getTotal();
        if (totalReserva == null) {
            return Money.usd(0);
        }
        return penalidad.calcularDe(totalReserva);
    }

    /**
     * Verifica si la cancelación está dentro de la ventana gratis.
     */
    public boolean estaDentroVentanaGratis(LocalDateTime fechaCancelacion, LocalDateTime fechaInicioReserva) {
        if (fechaCancelacion == null || fechaInicioReserva == null) {
            return false;
        }
        long horasHastaInicio = ChronoUnit.HOURS.between(fechaCancelacion, fechaInicioReserva);
        return horasHastaInicio >= ventanaGratisHoras;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PoliticaCancelacion that = (PoliticaCancelacion) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("PoliticaCancelacion[id=%s, descripcion=%s, ventanaGratis=%dh, penalidad=%s]",
                id, descripcion, ventanaGratisHoras, penalidad.toString());
    }
}

