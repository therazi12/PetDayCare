package servicios;

import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import strategies.CompatibilidadStrategyBasica;
import strategies.PricingStrategyPremium;
import valueobjects.Money;
import valueobjects.Periodo;
import java.time.LocalDateTime;
import java.util.List;

public class GuarderiaGrande extends ServicioAbstracto {

    public GuarderiaGrande() {
        super("GG001", "Guardería Grande", "Cuidado diario premium para mascotas en centro grande", Money.usd(40.0), "Perros, Gatos, Aves");
    }



    @Override
    public boolean verificarDisponibilidad(Periodo periodo) {
        if (periodo == null) {
            return false;
        }
        // Centros grandes tienen más disponibilidad
        return !periodo.getInicio().isBefore(LocalDateTime.now());
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        if (pricing == null) {
            // Usar estrategia premium por defecto para centros grandes
            pricing = new PricingStrategyPremium();
        }
        return super.calcularPrecio(periodo, opciones, pricing);
    }
}