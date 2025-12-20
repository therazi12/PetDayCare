package decoradores;

import interfaces.IPricingStrategy;
import java.util.List;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;

/**
 * Decorador que añade funcionalidad de cámara en vivo al servicio.
 */
public class CamaraEnVivoDecorator extends ServicioDecorador {
    private final Money costoReporte;

    public CamaraEnVivoDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoReporte = Money.usd(15.0);
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Cámara en Vivo";
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        Money precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricing);
        System.out.println("  [Decorador] Añadiendo Cámara en Vivo: +" + costoReporte.toString());
        return precioBase.sumar(costoReporte);
    }


    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Cámara en Vivo " + costoReporte.toString() + "]";
    }
}

