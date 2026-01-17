import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import strategies.PricingStrategyStandard;
import interfaces.IServicioBase;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PricingStrategyStandardTest {

	@Test
	void testPrecioPara_DiarioSinOpciones() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(100);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(true);
		Mockito.when(periodo.esSemanal()).thenReturn(false);
		Mockito.when(periodo.esMensual()).thenReturn(false);
		Mockito.when(periodo.duracionEnDias()).thenReturn(1L);
		Money result = strategy.precioPara(servicio, periodo, null);
		assertEquals(base, result);
	}

	@Test
	void testPrecioPara_SemanalSinOpciones() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(100);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(false);
		Mockito.when(periodo.esSemanal()).thenReturn(true);
		Mockito.when(periodo.esMensual()).thenReturn(false);
		Mockito.when(periodo.duracionEnDias()).thenReturn(5L);
		Money result = strategy.precioPara(servicio, periodo, null);
		// 5 días * 0.9 = 4.5, 100 * 4.5 = 450
		assertEquals(Money.usd(450), result);
	}

	@Test
	void testPrecioPara_MensualSinOpciones() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(100);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(false);
		Mockito.when(periodo.esSemanal()).thenReturn(false);
		Mockito.when(periodo.esMensual()).thenReturn(true);
		Mockito.when(periodo.duracionEnDias()).thenReturn(20L);
		Money result = strategy.precioPara(servicio, periodo, null);
		// 20 días * 0.8 = 16, 100 * 16 = 1600
		assertEquals(Money.usd(1600), result);
	}

	@Test
	void testPrecioPara_PersonalizadoSinOpciones() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(100);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(false);
		Mockito.when(periodo.esSemanal()).thenReturn(false);
		Mockito.when(periodo.esMensual()).thenReturn(false);
		Mockito.when(periodo.duracionEnDias()).thenReturn(7L);
		Money result = strategy.precioPara(servicio, periodo, null);
		// 100 * 7 = 700
		assertEquals(Money.usd(700), result);
	}

	@Test
	void testPrecioPara_DiarioConOpciones() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(100);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(true);
		Mockito.when(periodo.esSemanal()).thenReturn(false);
		Mockito.when(periodo.esMensual()).thenReturn(false);
		Mockito.when(periodo.duracionEnDias()).thenReturn(1L);
		Money result = strategy.precioPara(servicio, periodo, Arrays.asList("juegos", "suite"));
		// 100 + 5 + 15 = 120
		assertEquals(Money.usd(120), result);
	}

	@Test
	void testPrecioPara_ServicioNoCompatible() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		IServicioBase servicio = Mockito.mock(IServicioBase.class); // No es ServicioAbstracto
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(true);
		Mockito.when(periodo.duracionEnDias()).thenReturn(1L);
		assertThrows(IllegalArgumentException.class, () ->
				strategy.precioPara(servicio, periodo, Collections.singletonList("juegos")));
	}

	@Test
	void testCalcularCostoOpcion_Juegos() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		double costo = invokeCalcularCostoOpcion(strategy, "juegos");
		assertEquals(5.0, costo);
	}

	@Test
	void testCalcularCostoOpcion_Suite() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		double costo = invokeCalcularCostoOpcion(strategy, "suite");
		assertEquals(15.0, costo);
	}

	@Test
	void testCalcularCostoOpcion_OpcionInexistente() {
		PricingStrategyStandard strategy = new PricingStrategyStandard();
		double costo = invokeCalcularCostoOpcion(strategy, "opcion_inexistente");
		assertEquals(0.0, costo);
	}

	// Utilidad para invocar método privado calcularCostoOpcion
	private double invokeCalcularCostoOpcion(PricingStrategyStandard strategy, String opcion) {
		try {
			java.lang.reflect.Method m = PricingStrategyStandard.class.getDeclaredMethod("calcularCostoOpcion", String.class);
			m.setAccessible(true);
			return (double) m.invoke(strategy, opcion);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
