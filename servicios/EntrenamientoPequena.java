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
