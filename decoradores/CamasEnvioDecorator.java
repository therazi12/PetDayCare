package decoradores;

import servicios.ServicioAbstracto;
import java.util.List;


public class CamasEnvioDecorator extends ServicioDecorador {
    private double costoAdicional;

    public CamasEnvioDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoAdicional = 15.0;
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Camas de Envío";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricingStrategy);
        System.out.println("  [Decorador] Añadiendo Camas de Envío: +$" + costoAdicional);
        return precioBase + costoAdicional;
    }

    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Camas de Envío $" + costoAdicional + "]";
    }
}
