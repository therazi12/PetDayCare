package decoradores;

import interfaces.IPricingStrategy;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

public class RecorteTermoRicosDecorator extends ServicioDecorador {
    private Money costoAdicional;

    public RecorteTermoRicosDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoAdicional = Money.usd(20.0);
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Recorte Termorricos";
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        Money precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricing);
        System.out.println("  [Decorador] Añadiendo Recorte Termorricos: +" + costoAdicional.toString());
        return precioBase.sumar(costoAdicional);
    }

    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Recorte Termorricos " + costoAdicional.toString() + "]";
    }
}
