package strategies;

import interfaces.IPricingStrategy;
import interfaces.IServicioBase;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

/**
 * Estrategia estándar de pricing que calcula precios basados en el período y opciones.
 */
public class PricingStrategyStandard implements IPricingStrategy {
    
    @Override
    public Money precioPara(IServicioBase servicio, Periodo periodo, List<String> opciones) {
        if (!(servicio instanceof ServicioAbstracto)) {
            throw new IllegalArgumentException("Esta estrategia solo funciona con ServicioAbstracto");
        }
        
        ServicioAbstracto servicioAbstracto = (ServicioAbstracto) servicio;
        Money precio = servicioAbstracto.getPrecioBase();
        
        // Ajuste por período
        long dias = periodo.duracionEnDias();
        if (periodo.esSemanal()) {
            precio = precio.multiplicar(5 * 0.9); // 5 días con 10% descuento
        } else if (periodo.esMensual()) {
            precio = precio.multiplicar(20 * 0.8); // 20 días con 20% descuento
        } else if (periodo.esDiario()) {
            // Precio diario sin cambios
            precio = precio;
        } else {
            // Para otros períodos, multiplicar por días
            precio = precio.multiplicar(dias);
        }
        
        // Agregar costo de opciones adicionales
        if (opciones != null) {
            for (String opcion : opciones) {
                precio = precio.sumar(Money.usd(calcularCostoOpcion(opcion)));
            }
        }
        
        return precio;
    }
    
    private double calcularCostoOpcion(String opcion) {
        switch (opcion.toLowerCase()) {
            case "juegos":
                return 5.0;
            case "entrenamiento":
                return 10.0;
            case "grooming":
                return 12.0;
            case "extendido":
                return 8.0;
            case "parque_especial":
                return 10.0;
            case "suite":
                return 15.0;
            case "personalizado":
                return 20.0;
            case "avanzado":
                return 15.0;
            case "cama_premium":
                return 10.0;
            case "masaje":
                return 15.0;
            case "aromaterapia":
                return 10.0;
            case "hidromasaje":
                return 30.0;
            default:
                return 0.0;
        }
    }
}

