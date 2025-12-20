package servicios;

import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import strategies.CompatibilidadStrategyBasica;
import strategies.PricingStrategyPremium;
import valueobjects.Money;
import valueobjects.Periodo;
import java.time.LocalDateTime;
import java.util.List;

public class BienestarGrande extends ServicioAbstracto {

    public BienestarGrande() {
        super("BG001", "Bienestar Grande", "Servicios de spa y bienestar premium en centro grande", Money.usd(65.0), "Perros, Gatos, Aves");
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
