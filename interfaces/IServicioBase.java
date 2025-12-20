package interfaces;

import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

/**
 * Interfaz base que define el contrato para todos los servicios del sistema.
 * Implementada por ServicioAbstracto y los decoradores.
 */
public interface IServicioBase {
    /**
     * Obtiene el nombre del servicio.
     * @return El nombre del servicio
     */
    String obtenerNombre();

    /**
     * Calcula el precio del servicio para un período dado, con opciones adicionales
     * y usando una estrategia de precios específica.
     * 
     * @param periodo El período de tiempo para el cual se calcula el precio
     * @param opciones Lista de opciones adicionales (puede ser null o vacía)
     * @param pricing La estrategia de precios a utilizar
     * @return El precio calculado como un objeto Money
     */
    Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing);
}

