package strategies;

import interfaces.IPricingStrategy;
import interfaces.IServicioBase;
import servicios.ServicioAbstracto;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

/**
 * Estrategia premium de pricing con descuentos más agresivos y opciones premium.
 */
public class PricingStrategyPremium implements IPricingStrategy {
    
    @Override
    public Money precioPara(IServicioBase servicio, Periodo periodo, List<String> opciones) {
        if (!(servicio instanceof ServicioAbstracto)) {
            throw new IllegalArgumentException("Esta estrategia solo funciona con ServicioAbstracto");
        }
        
        ServicioAbstracto servicioAbstracto = (ServicioAbstracto) servicio;
        Money precio = servicioAbstracto.getPrecioBase();
        
        // Ajuste por período con descuentos más agresivos
        if (periodo.esSemanal()) {
            precio = precio.multiplicar(5 * 0.85); // 5 días con 15% descuento
        } else if (periodo.esMensual()) {
            precio = precio.multiplicar(20 * 0.75); // 20 días con 25% descuento
        } else if (periodo.esDiario()) {
            precio = precio;
        } else {
            long dias = periodo.duracionEnDias();
            precio = precio.multiplicar(dias * 0.9); // 10% descuento para otros períodos
        }
        
        // Agregar costo de opciones adicionales (precios premium)
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
                return 8.0;
            case "entrenamiento":
                return 15.0;
            case "grooming":
                return 12.0;
            case "extendido":
                return 10.0;
            case "parque_especial":
                return 12.0;
            case "suite":
                return 20.0;
            case "personalizado":
                return 25.0;
            case "avanzado":
                return 25.0;
            case "cama_premium":
                return 15.0;
            case "masaje":
                return 25.0;
            case "aromaterapia":
                return 15.0;
            case "hidromasaje":
                return 30.0;
            default:
                return 0.0;
        }
    }
}

