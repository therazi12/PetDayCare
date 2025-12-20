package interfaces;

import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

/**
 * Interfaz que define la estrategia de cálculo de precios.
 * Permite diferentes algoritmos de pricing según el tipo de servicio y período.
 */
public interface IPricingStrategy {
    /**
     * Calcula el precio para un servicio específico, período y opciones adicionales.
     * 
     * @param servicio El servicio base para el cual se calcula el precio
     * @param periodo El período de tiempo
     * @param opciones Lista de opciones adicionales (puede ser null o vacía)
     * @return El precio calculado como un objeto Money
     */
    Money precioPara(IServicioBase servicio, Periodo periodo, List<String> opciones);
}

