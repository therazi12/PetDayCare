import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import strategies.PricingStrategyPremium;
import interfaces.IServicioBase;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PricingStrategyPremiumTest {

	@Test
	void testPrecioPara_DiarioSinOpciones() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(200);
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
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(200);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(false);
		Mockito.when(periodo.esSemanal()).thenReturn(true);
		Mockito.when(periodo.esMensual()).thenReturn(false);
		Mockito.when(periodo.duracionEnDias()).thenReturn(5L);
		Money result = strategy.precioPara(servicio, periodo, null);
		// 5 * 0.85 = 4.25, 200 * 4.25 = 850
		assertEquals(Money.usd(850), result);
	}

	@Test
	void testPrecioPara_MensualSinOpciones() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(200);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(false);
		Mockito.when(periodo.esSemanal()).thenReturn(false);
		Mockito.when(periodo.esMensual()).thenReturn(true);
		Mockito.when(periodo.duracionEnDias()).thenReturn(20L);
		Money result = strategy.precioPara(servicio, periodo, null);
		// 20 * 0.75 = 15, 200 * 15 = 3000
		assertEquals(Money.usd(3000), result);
	}

	@Test
	void testPrecioPara_PersonalizadoSinOpciones() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(200);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(false);
		Mockito.when(periodo.esSemanal()).thenReturn(false);
		Mockito.when(periodo.esMensual()).thenReturn(false);
		Mockito.when(periodo.duracionEnDias()).thenReturn(7L);
		Money result = strategy.precioPara(servicio, periodo, null);
		// 7 * 0.9 = 6.3, 200 * 6.3 = 1260
		assertEquals(Money.usd(1260), result);
	}

	@Test
	void testPrecioPara_DiarioConOpciones() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		ServicioAbstracto servicio = Mockito.mock(ServicioAbstracto.class);
		Money base = Money.usd(200);
		Mockito.when(servicio.getPrecioBase()).thenReturn(base);
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(true);
		Mockito.when(periodo.esSemanal()).thenReturn(false);
		Mockito.when(periodo.esMensual()).thenReturn(false);
		Mockito.when(periodo.duracionEnDias()).thenReturn(1L);
		Money result = strategy.precioPara(servicio, periodo, Arrays.asList("juegos", "suite"));
		// 200 + 8 + 20 = 228
		assertEquals(Money.usd(228), result);
	}

	@Test
	void testPrecioPara_ServicioNoCompatible() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		IServicioBase servicio = Mockito.mock(IServicioBase.class); // No es ServicioAbstracto
		Periodo periodo = Mockito.mock(Periodo.class);
		Mockito.when(periodo.esDiario()).thenReturn(true);
		Mockito.when(periodo.duracionEnDias()).thenReturn(1L);
		assertThrows(IllegalArgumentException.class, () ->
				strategy.precioPara(servicio, periodo, Collections.singletonList("juegos")));
	}

	@Test
	void testCalcularCostoOpcion_Juegos() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		double costo = invokeCalcularCostoOpcion(strategy, "juegos");
		assertEquals(8.0, costo);
	}

	@Test
	void testCalcularCostoOpcion_Suite() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		double costo = invokeCalcularCostoOpcion(strategy, "suite");
		assertEquals(20.0, costo);
	}

	@Test
	void testCalcularCostoOpcion_OpcionInexistente() {
		PricingStrategyPremium strategy = new PricingStrategyPremium();
		double costo = invokeCalcularCostoOpcion(strategy, "opcion_inexistente");
		assertEquals(0.0, costo);
	}

	// Utilidad para invocar método privado calcularCostoOpcion
	private double invokeCalcularCostoOpcion(PricingStrategyPremium strategy, String opcion) {
		try {
			java.lang.reflect.Method m = PricingStrategyPremium.class.getDeclaredMethod("calcularCostoOpcion", String.class);
			m.setAccessible(true);
			return (double) m.invoke(strategy, opcion);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
