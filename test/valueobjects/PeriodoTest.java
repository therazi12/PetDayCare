package valueobjects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Pruebas unitarias para la clase Periodo.
 */
@DisplayName("Pruebas para Periodo Value Object")
class PeriodoTest {

    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Periodo periodo;

    @BeforeEach
    void setUp() {
        inicio = LocalDateTime.of(2024, 1, 1, 10, 0);
        fin = LocalDateTime.of(2024, 1, 1, 18, 0);
        periodo = new Periodo(inicio, fin);
    }

    @Test
    @DisplayName("Crear Periodo con fechas válidas")
    void crearPeriodoValido() {
        assertNotNull(periodo);
        assertEquals(inicio, periodo.getInicio());
        assertEquals(fin, periodo.getFin());
    }

    @Test
    @DisplayName("Lanzar excepción al crear Periodo con inicio null")
    void crearPeriodoConInicioNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Periodo(null, fin);
        });
    }

    @Test
    @DisplayName("Lanzar excepción al crear Periodo con fin null")
    void crearPeriodoConFinNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Periodo(inicio, null);
        });
    }

    @Test
    @DisplayName("Lanzar excepción cuando fin es anterior a inicio")
    void crearPeriodoConFinAnteriorAInicio() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Periodo(fin, inicio);
        });
    }

    @Test
    @DisplayName("Calcular duración en horas")
    void calcularDuracionEnHoras() {
        assertEquals(8, periodo.duracionEnHoras());
    }

    @Test
    @DisplayName("Calcular duración en días")
    void calcularDuracionEnDias() {
        LocalDateTime inicioDias = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime finDias = LocalDateTime.of(2024, 1, 5, 10, 0);
        Periodo periodoDias = new Periodo(inicioDias, finDias);
        assertEquals(4, periodoDias.duracionEnDias());
    }

    @Test
    @DisplayName("Verificar si contiene una fecha")
    void contieneFecha() {
        LocalDateTime fechaDentro = LocalDateTime.of(2024, 1, 1, 14, 0);
        LocalDateTime fechaFuera = LocalDateTime.of(2024, 1, 2, 14, 0);
        
        assertTrue(periodo.contiene(fechaDentro));
        assertFalse(periodo.contiene(fechaFuera));
    }

    @Test
    @DisplayName("Verificar si se solapa con otro período")
    void seSolapaCon() {
        Periodo otroPeriodo = new Periodo(
            LocalDateTime.of(2024, 1, 1, 15, 0),
            LocalDateTime.of(2024, 1, 1, 20, 0)
        );
        assertTrue(periodo.seSolapaCon(otroPeriodo));
    }

    @Test
    @DisplayName("Verificar si no se solapa con otro período")
    void noSeSolapaCon() {
        Periodo otroPeriodo = new Periodo(
            LocalDateTime.of(2024, 1, 2, 10, 0),
            LocalDateTime.of(2024, 1, 2, 18, 0)
        );
        assertFalse(periodo.seSolapaCon(otroPeriodo));
    }

    @Test
    @DisplayName("Verificar si es posterior a otro período")
    void esPosteriorA() {
        Periodo otroPeriodo = new Periodo(
            LocalDateTime.of(2023, 12, 31, 10, 0),
            LocalDateTime.of(2023, 12, 31, 18, 0)
        );
        assertTrue(periodo.esPosteriorA(otroPeriodo));
    }

    @Test
    @DisplayName("Verificar si es anterior a otro período")
    void esAnteriorA() {
        Periodo otroPeriodo = new Periodo(
            LocalDateTime.of(2024, 1, 2, 10, 0),
            LocalDateTime.of(2024, 1, 2, 18, 0)
        );
        assertTrue(periodo.esAnteriorA(otroPeriodo));
    }

    @Test
    @DisplayName("Verificar si es diario")
    void esDiario() {
        Periodo periodoDiario = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 1, 2, 10, 0)
        );
        assertTrue(periodoDiario.esDiario());
    }

    @Test
    @DisplayName("Verificar si es semanal")
    void esSemanal() {
        Periodo periodoSemanal = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 1, 7, 10, 0)
        );
        assertTrue(periodoSemanal.esSemanal());
    }

    @Test
    @DisplayName("Verificar si es mensual")
    void esMensual() {
        Periodo periodoMensual = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 2, 1, 10, 0)
        );
        assertTrue(periodoMensual.esMensual());
    }

    @Test
    @DisplayName("Verificar igualdad con equals")
    void testEquals() {
        Periodo otroPeriodo = new Periodo(inicio, fin);
        assertEquals(periodo, otroPeriodo);
    }

    @Test
    @DisplayName("Verificar hashCode")
    void testHashCode() {
        Periodo otroPeriodo = new Periodo(inicio, fin);
        assertEquals(periodo.hashCode(), otroPeriodo.hashCode());
    }

    @Test
    @DisplayName("Verificar toString")
    void testToString() {
        String resultado = periodo.toString();
        assertTrue(resultado.contains("Periodo"));
    }
}

