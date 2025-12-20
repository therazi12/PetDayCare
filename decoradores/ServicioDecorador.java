package decoradores;

import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

/**
 * Clase abstracta base para todos los decoradores de servicios.
 * Implementa el patrón Decorator para añadir funcionalidades a los servicios.
 */
public abstract class ServicioDecorador extends ServicioAbstracto {
    protected ServicioAbstracto servicioDecorado;

    public ServicioDecorador(ServicioAbstracto servicio) {
        super(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(), 
              servicio.getPrecioBase(), servicio.getTiposMascotaAdmitidos());
        this.servicioDecorado = servicio;
    }

    @Override
    public boolean esCompatible(Object mascota, ICompatibilidadStrategy regla) {
        return servicioDecorado.esCompatible(mascota, regla);
    }

    @Override
    public boolean verificarDisponibilidad(Periodo periodo) {
        return servicioDecorado.verificarDisponibilidad(periodo);
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre();
    }

    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        return servicioDecorado.calcularPrecio(periodo, opciones, pricing);
    }
}
