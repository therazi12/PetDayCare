package decoradores;

import interfaces.IPricingStrategy;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

public class CamasEnvioDecorator extends ServicioDecorador {
    private Money costoAdicional;

    public CamasEnvioDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoAdicional = Money.usd(15.0);
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Camas de Envío";
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        Money precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricing);
        System.out.println("  [Decorador] Añadiendo Camas de Envío: +" + costoAdicional.toString());
        return precioBase.sumar(costoAdicional);
    }

    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Camas de Envío " + costoAdicional.toString() + "]";
    }
}
