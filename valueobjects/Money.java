package valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value Object que representa una cantidad monetaria.
 * Inmutable y con validaciones.
 */
public class Money {
    private final BigDecimal monto;
    private final String moneda;

    public Money(BigDecimal monto, String moneda) {
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser null");
        }
        if (monto.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
        if (moneda == null || moneda.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda no puede ser null o vacía");
        }
        this.monto = monto.setScale(2, RoundingMode.HALF_UP);
        this.moneda = moneda.toUpperCase();
    }

    public Money(double monto, String moneda) {
        this(BigDecimal.valueOf(monto), moneda);
    }

    // Getters
    public BigDecimal getMonto() {
        return monto;
    }

    public String getMoneda() {
        return moneda;
    }

    // Operaciones matemáticas
    public Money sumar(Money otro) {
        validarMismaMoneda(otro);
        return new Money(this.monto.add(otro.monto), this.moneda);
    }

    public Money restar(Money otro) {
        validarMismaMoneda(otro);
        BigDecimal resultado = this.monto.subtract(otro.monto);
        if (resultado.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El resultado no puede ser negativo");
        }
        return new Money(resultado, this.moneda);
    }

    public Money multiplicar(double factor) {
        if (factor < 0) {
            throw new IllegalArgumentException("El factor no puede ser negativo");
        }
        return new Money(this.monto.multiply(BigDecimal.valueOf(factor)), this.moneda);
    }

    public Money multiplicar(BigDecimal factor) {
        if (factor == null || factor.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El factor no puede ser null o negativo");
        }
        return new Money(this.monto.multiply(factor), this.moneda);
    }

    private void validarMismaMoneda(Money otro) {
        if (!this.moneda.equals(otro.moneda)) {
            throw new IllegalArgumentException("No se pueden operar montos de diferentes monedas");
        }
    }

    public boolean esMayorQue(Money otro) {
        validarMismaMoneda(otro);
        return this.monto.compareTo(otro.monto) > 0;
    }

    public boolean esMenorQue(Money otro) {
        validarMismaMoneda(otro);
        return this.monto.compareTo(otro.monto) < 0;
    }

    public boolean esIgualA(Money otro) {
        if (otro == null) return false;
        return this.moneda.equals(otro.moneda) && this.monto.compareTo(otro.monto) == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Money money = (Money) obj;
        return Objects.equals(monto, money.monto) && Objects.equals(moneda, money.moneda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monto, moneda);
    }

    @Override
    public String toString() {
        return String.format("%s %s", moneda, monto.toPlainString());
    }

    // Método de conveniencia para crear Money en USD
    public static Money usd(double monto) {
        return new Money(monto, "USD");
    }

    // Método de conveniencia para crear Money en EUR
    public static Money eur(double monto) {
        return new Money(monto, "EUR");
    }

    // Método de conveniencia para crear Money en MXN
    public static Money mxn(double monto) {
        return new Money(monto, "MXN");
    }
}

