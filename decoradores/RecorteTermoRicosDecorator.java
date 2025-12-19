package decoradores;

import servicios.ServicioAbstracto;
import java.util.List;


public class RecorteTermoRicosDecorator extends ServicioDecorador {
    private double costoAdicional;

    public RecorteTermoRicosDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoAdicional = 20.0;
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Recorte Termorricos";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricingStrategy);
        System.out.println("  [Decorador] Añadiendo Recorte Termorricos: +$" + costoAdicional);
        return precioBase + costoAdicional;
    }

    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Recorte Termorricos $" + costoAdicional + "]";
    }
}
