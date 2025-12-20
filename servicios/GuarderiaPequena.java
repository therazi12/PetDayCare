package servicios;

import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import strategies.CompatibilidadStrategyBasica;
import strategies.PricingStrategyStandard;
import valueobjects.Money;
import valueobjects.Periodo;
import java.time.LocalDateTime;
import java.util.List;

public class GuarderiaPequena extends ServicioAbstracto {

    public GuarderiaPequena() {
        super("GP001", "Guardería Pequeña", "Cuidado diario para mascotas en centro pequeño", Money.usd(25.0), "Perros, Gatos");
    }

    @Override
    public boolean esCompatible(Object mascota, ICompatibilidadStrategy regla) {
        if (regla == null) {
            // Estrategia por defecto si no se proporciona
            regla = new CompatibilidadStrategyBasica();
        }
        return regla.esCompatible(this, mascota);
    }

    @Override
    public boolean verificarDisponibilidad(Periodo periodo) {
        // Lógica simplificada - siempre hay disponibilidad en centros pequeños
        if (periodo == null) {
            return false;
        }
        // Verificar que el período no sea en el pasado
        return !periodo.getInicio().isBefore(LocalDateTime.now());
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        if (pricing == null) {
            // Usar estrategia estándar por defecto
            pricing = new PricingStrategyStandard();
        }
        return super.calcularPrecio(periodo, opciones, pricing);
    }
}
