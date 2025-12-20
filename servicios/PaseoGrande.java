package servicios;

import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import strategies.CompatibilidadStrategyBasica;
import strategies.PricingStrategyPremium;
import valueobjects.Money;
import valueobjects.Periodo;
import java.time.LocalDateTime;
import java.util.List;

public class PaseoGrande extends ServicioAbstracto {

    public PaseoGrande() {
        super("PG001", "Paseo Grande", "Servicio de paseo premium para perros en centro grande", Money.usd(25.0), "Perros");
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
            pricing = new PricingStrategyPremium();
        }
        return super.calcularPrecio(periodo, opciones, pricing);
    }
}
