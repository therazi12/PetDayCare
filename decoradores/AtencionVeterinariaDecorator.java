package decoradores;

import servicios.ServicioAbstracto;
import java.util.List;


public class AtencionVeterinariaDecorator extends ServicioDecorador {
    private double costoAdicional;

    public AtencionVeterinariaDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoAdicional = 35.0;
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Atención Veterinaria";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricingStrategy);
        System.out.println("  [Decorador] Añadiendo Atención Veterinaria: +$" + costoAdicional);
        return precioBase + costoAdicional;
    }

    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Atención Veterinaria $" + costoAdicional + "]";
    }
}
