package valueobjects;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Value Object que representa un período de tiempo con inicio y fin.
 * Inmutable y con validaciones.
 */
public class Periodo {
    private final LocalDateTime inicio;
    private final LocalDateTime fin;

    public Periodo(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser null");
        }
        if (fin == null) {
            throw new IllegalArgumentException("La fecha de fin no puede ser null");
        }
        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        this.inicio = inicio;
        this.fin = fin;
    }

    // Getters
    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    // Métodos de utilidad
    public long duracionEnHoras() {
        return ChronoUnit.HOURS.between(inicio, fin);
    }

    public long duracionEnDias() {
        return ChronoUnit.DAYS.between(inicio, fin);
    }

    public boolean contiene(LocalDateTime fecha) {
        return !fecha.isBefore(inicio) && !fecha.isAfter(fin);
    }

    public boolean seSolapaCon(Periodo otro) {
        return this.inicio.isBefore(otro.fin) && this.fin.isAfter(otro.inicio);
    }

    public boolean esPosteriorA(Periodo otro) {
        return this.inicio.isAfter(otro.fin);
    }

    public boolean esAnteriorA(Periodo otro) {
        return this.fin.isBefore(otro.inicio);
    }

    public boolean esDiario() {
        return duracionEnDias() == 1;
    }

    public boolean esSemanal() {
        return duracionEnDias() >= 5 && duracionEnDias() <= 7;
    }

    public boolean esMensual() {
        return duracionEnDias() >= 28 && duracionEnDias() <= 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Periodo periodo = (Periodo) obj;
        return Objects.equals(inicio, periodo.inicio) && Objects.equals(fin, periodo.fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inicio, fin);
    }

    @Override
    public String toString() {
        return String.format("Periodo[%s - %s]", inicio, fin);
    }
}

