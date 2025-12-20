package valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value Object que representa un porcentaje.
 * Inmutable y con validaciones.
 */
public class Porcentaje {
    private final BigDecimal valor; // Almacenado como decimal (ej: 0.15 para 15%)

    public Porcentaje(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException("El valor no puede ser null");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El porcentaje no puede ser negativo");
        }
        if (valor.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("El porcentaje no puede ser mayor que 1 (100%)");
        }
        this.valor = valor.setScale(4, RoundingMode.HALF_UP);
    }

    public Porcentaje(double valor) {
        this(BigDecimal.valueOf(valor));
    }

    /**
     * Crea un Porcentaje desde un valor entero (ej: 15 para 15%).
     */
    public static Porcentaje desdePorcentaje(int porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }
        return new Porcentaje(BigDecimal.valueOf(porcentaje).divide(BigDecimal.valueOf(100)));
    }

    // Getters
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Retorna el porcentaje como entero (ej: 15 para 15%).
     */
    public int getPorcentajeEntero() {
        return valor.multiply(BigDecimal.valueOf(100)).intValue();
    }

    /**
     * Aplica el porcentaje a un valor Money.
     */
    public Money aplicarA(Money monto) {
        return monto.multiplicar(valor);
    }

    /**
     * Calcula el porcentaje de un valor Money.
     */
    public Money calcularDe(Money monto) {
        return monto.multiplicar(valor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Porcentaje that = (Porcentaje) obj;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return getPorcentajeEntero() + "%";
    }
}

