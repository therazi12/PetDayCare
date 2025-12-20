package valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

/**
 * Pruebas unitarias para la clase Money.
 */
@DisplayName("Pruebas para Money Value Object")
class MoneyTest {

    private Money dinero10USD;
    private Money dinero20USD;
    private Money dinero15EUR;

    @BeforeEach
    void setUp() {
        dinero10USD = Money.usd(10.0);
        dinero20USD = Money.usd(20.0);
        dinero15EUR = Money.eur(15.0);
    }

    @Test
    @DisplayName("Crear Money con valores válidos")
    void crearMoneyValido() {
        Money dinero = Money.usd(50.0);
        assertEquals(BigDecimal.valueOf(50.0), dinero.getMonto());
        assertEquals("USD", dinero.getMoneda());
    }

    @Test
    @DisplayName("Crear Money con BigDecimal")
    void crearMoneyConBigDecimal() {
        Money dinero = new Money(BigDecimal.valueOf(25.50), "MXN");
        assertEquals(BigDecimal.valueOf(25.50), dinero.getMonto());
        assertEquals("MXN", dinero.getMoneda());
    }

    @Test
    @DisplayName("Lanzar excepción al crear Money con monto negativo")
    void crearMoneyConMontoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(-10.0, "USD");
        });
    }

    @Test
    @DisplayName("Lanzar excepción al crear Money con moneda null")
    void crearMoneyConMonedaNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(10.0, null);
        });
    }

    @Test
    @DisplayName("Sumar dos montos de la misma moneda")
    void sumarMontosMismaMoneda() {
        Money resultado = dinero10USD.sumar(dinero20USD);
        assertEquals(BigDecimal.valueOf(30.0), resultado.getMonto());
        assertEquals("USD", resultado.getMoneda());
    }

    @Test
    @DisplayName("Lanzar excepción al sumar montos de diferentes monedas")
    void sumarMontosDiferentesMonedas() {
        assertThrows(IllegalArgumentException.class, () -> {
            dinero10USD.sumar(dinero15EUR);
        });
    }

    @Test
    @DisplayName("Restar dos montos de la misma moneda")
    void restarMontosMismaMoneda() {
        Money resultado = dinero20USD.restar(dinero10USD);
        assertEquals(BigDecimal.valueOf(10.0), resultado.getMonto());
    }

    @Test
    @DisplayName("Lanzar excepción al restar y obtener resultado negativo")
    void restarConResultadoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            dinero10USD.restar(dinero20USD);
        });
    }

    @Test
    @DisplayName("Multiplicar Money por un factor")
    void multiplicarPorFactor() {
        Money resultado = dinero10USD.multiplicar(2.5);
        assertEquals(BigDecimal.valueOf(25.0), resultado.getMonto());
    }

    @Test
    @DisplayName("Comparar Money: esMayorQue")
    void esMayorQue() {
        assertTrue(dinero20USD.esMayorQue(dinero10USD));
        assertFalse(dinero10USD.esMayorQue(dinero20USD));
    }

    @Test
    @DisplayName("Comparar Money: esMenorQue")
    void esMenorQue() {
        assertTrue(dinero10USD.esMenorQue(dinero20USD));
        assertFalse(dinero20USD.esMenorQue(dinero10USD));
    }

    @Test
    @DisplayName("Comparar Money: esIgualA")
    void esIgualA() {
        Money otro10USD = Money.usd(10.0);
        assertTrue(dinero10USD.esIgualA(otro10USD));
        assertFalse(dinero10USD.esIgualA(dinero20USD));
        assertFalse(dinero10USD.esIgualA(null));
    }

    @Test
    @DisplayName("Verificar igualdad con equals")
    void testEquals() {
        Money otro10USD = Money.usd(10.0);
        assertEquals(dinero10USD, otro10USD);
        assertNotEquals(dinero10USD, dinero20USD);
    }

    @Test
    @DisplayName("Verificar hashCode")
    void testHashCode() {
        Money otro10USD = Money.usd(10.0);
        assertEquals(dinero10USD.hashCode(), otro10USD.hashCode());
    }

    @Test
    @DisplayName("Verificar toString")
    void testToString() {
        String resultado = dinero10USD.toString();
        assertTrue(resultado.contains("USD"));
        assertTrue(resultado.contains("10"));
    }

    @Test
    @DisplayName("Redondear a 2 decimales")
    void redondearADosDecimales() {
        Money dinero = new Money(10.999, "USD");
        assertEquals(BigDecimal.valueOf(11.00), dinero.getMonto());
    }

    @Test
    @DisplayName("Crear Money con métodos de conveniencia")
    void crearConMetodosConveniencia() {
        Money usd = Money.usd(100.0);
        Money eur = Money.eur(100.0);
        Money mxn = Money.mxn(100.0);

        assertEquals("USD", usd.getMoneda());
        assertEquals("EUR", eur.getMoneda());
        assertEquals("MXN", mxn.getMoneda());
    }
}

