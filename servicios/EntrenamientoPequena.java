package servicios;

import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import strategies.CompatibilidadStrategyBasica;
import strategies.PricingStrategyStandard;
import valueobjects.Money;
import valueobjects.Periodo;
import java.time.LocalDateTime;
import java.util.List;

public class EntrenamientoPequena extends ServicioAbstracto {

    public EntrenamientoPequena() {
        super("EP001", "Entrenamiento Pequeña", "Sesiones de entrenamiento básico en centro pequeño", Money.usd(30.0), "Perros, Gatos");
    }

    @Override
    public boolean esCompatible(Object mascota, ICompatibilidadStrategy regla) {
        if (regla == null) {
            regla = new CompatibilidadStrategyBasica();
        }
        return regla.esCompatible(this, mascota);
    }

    @Override
    public boolean verificarDisponibilidad(Periodo periodo) {
        if (periodo == null) {
            return false;
        }
        return !periodo.getInicio().isBefore(LocalDateTime.now());
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        if (pricing == null) {
            pricing = new PricingStrategyStandard();
        }
        return super.calcularPrecio(periodo, opciones, pricing);
    }
}
