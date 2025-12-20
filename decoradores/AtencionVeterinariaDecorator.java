package decoradores;

import interfaces.IPricingStrategy;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

public class AtencionVeterinariaDecorator extends ServicioDecorador {
    private Money costoAdicional;

    public AtencionVeterinariaDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoAdicional = Money.usd(35.0);
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Atención Veterinaria";
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        Money precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricing);
        System.out.println("  [Decorador] Añadiendo Atención Veterinaria: +" + costoAdicional.toString());
        return precioBase.sumar(costoAdicional);
    }

    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Atención Veterinaria " + costoAdicional.toString() + "]";
    }
}
