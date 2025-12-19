package decoradores;

import servicios.ServicioAbstracto;
import java.util.List;


public abstract class ServicioDecorador extends ServicioAbstracto {
    protected ServicioAbstracto servicioDecorado;

    public ServicioDecorador(ServicioAbstracto servicio) {
        super(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(), 
              servicio.getPrecioBase(), servicio.getTiposMascotaAdmitidos());
        this.servicioDecorado = servicio;
    }

    @Override
    public boolean esCompatible(String mascota, String regla) {
        return servicioDecorado.esCompatible(mascota, regla);
    }

    @Override
    public boolean verificarDisponibilidad(String periodo) {
        return servicioDecorado.verificarDisponibilidad(periodo);
    }

    @Override
    public String obtenerNombre() {
        return servicioDecorado.obtenerNombre();
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        return servicioDecorado.calcularPrecio(periodo, opciones, pricingStrategy);
    }
}
