package valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

/**
 * Pruebas unitarias para la clase Porcentaje.
 */
@DisplayName("Pruebas para Porcentaje Value Object")
class PorcentajeTest {

    @Test
    @DisplayName("Crear Porcentaje con valor válido")
    void crearPorcentajeValido() {
        Porcentaje porcentaje = new Porcentaje(0.15);
        assertEquals(BigDecimal.valueOf(0.15), porcentaje.getValor());
    }

    @Test
    @DisplayName("Crear Porcentaje desde porcentaje entero")
    void crearDesdePorcentajeEntero() {
        Porcentaje porcentaje = Porcentaje.desdePorcentaje(25);
        assertEquals(25, porcentaje.getPorcentajeEntero());
    }

    @Test
    @DisplayName("Lanzar excepción al crear Porcentaje negativo")
    void crearPorcentajeNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Porcentaje(-0.1);
        });
    }

    @Test
    @DisplayName("Lanzar excepción al crear Porcentaje mayor a 100%")
    void crearPorcentajeMayorA100() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Porcentaje(1.5);
        });
    }

    @Test
    @DisplayName("Lanzar excepción al crear desde porcentaje entero fuera de rango")
    void crearDesdePorcentajeFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> {
            Porcentaje.desdePorcentaje(150);
        });
    }

    @Test
    @DisplayName("Obtener porcentaje entero")
    void obtenerPorcentajeEntero() {
        Porcentaje porcentaje = Porcentaje.desdePorcentaje(30);
        assertEquals(30, porcentaje.getPorcentajeEntero());
    }

    @Test
    @DisplayName("Aplicar porcentaje a un Money")
    void aplicarAMoney() {
        Porcentaje porcentaje = Porcentaje.desdePorcentaje(20);
        Money dinero = Money.usd(100.0);
        Money resultado = porcentaje.aplicarA(dinero);
        
        assertEquals(BigDecimal.valueOf(20.0), resultado.getMonto());
    }

    @Test
    @DisplayName("Calcular porcentaje de un Money")
    void calcularDeMoney() {
        Porcentaje porcentaje = Porcentaje.desdePorcentaje(15);
        Money dinero = Money.usd(200.0);
        Money resultado = porcentaje.calcularDe(dinero);
        
        assertEquals(BigDecimal.valueOf(30.0), resultado.getMonto());
    }

    @Test
    @DisplayName("Verificar igualdad con equals")
    void testEquals() {
        Porcentaje porcentaje1 = Porcentaje.desdePorcentaje(25);
        Porcentaje porcentaje2 = Porcentaje.desdePorcentaje(25);
        assertEquals(porcentaje1, porcentaje2);
    }

    @Test
    @DisplayName("Verificar hashCode")
    void testHashCode() {
        Porcentaje porcentaje1 = Porcentaje.desdePorcentaje(25);
        Porcentaje porcentaje2 = Porcentaje.desdePorcentaje(25);
        assertEquals(porcentaje1.hashCode(), porcentaje2.hashCode());
    }

    @Test
    @DisplayName("Verificar toString")
    void testToString() {
        Porcentaje porcentaje = Porcentaje.desdePorcentaje(50);
        String resultado = porcentaje.toString();
        assertTrue(resultado.contains("50"));
        assertTrue(resultado.contains("%"));
    }
}

