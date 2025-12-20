package decoradores;

import interfaces.IPricingStrategy;
import java.util.List;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;

/**
 * Decorador que añade funcionalidad de reportes en tiempo real al servicio.
 */
public class ReporteTiempoRealDecorator extends ServicioDecorador {
    private final Money costoReporte;

    public ReporteTiempoRealDecorator(ServicioAbstracto servicio) {
        super(servicio);
        this.costoReporte = Money.usd(20.0);
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre() + " + Reporte en Tiempo Real";
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        Money precioBase = servicioDecorado.calcularPrecio(periodo, opciones, pricing);
        System.out.println("  [Decorador] Añadiendo Reporte en Tiempo Real: +" + costoReporte.toString());
        return precioBase.sumar(costoReporte);
    }

    @Override
    public String toString() {
        return servicioDecorado.toString() + " [+ Reporte en Tiempo Real " + costoReporte.toString() + "]";
    }
}

