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
    
    // Constantes de descuentos
    public static final double MULTIPLICADOR_SEMANAL = 0.85; // 15% descuento
    public static final double MULTIPLICADOR_MENSUAL = 0.75; // 25% descuento
    public static final double MULTIPLICADOR_OTRO = 0.9;     // 10% descuento
    
    // Constantes de precios de opciones
    public static final double PRECIO_JUEGOS = 8.0;
    public static final double PRECIO_ENTRENAMIENTO = 15.0;
    public static final double PRECIO_GROOMING = 12.0;
    public static final double PRECIO_EXTENDIDO = 10.0;
    public static final double PRECIO_PARQUE_ESPECIAL = 12.0;
    public static final double PRECIO_SUITE = 20.0;
    public static final double PRECIO_PERSONALIZADO = 25.0;
    public static final double PRECIO_AVANZADO = 25.0;
    public static final double PRECIO_CAMA_PREMIUM = 15.0;
    public static final double PRECIO_MASAJE = 25.0;
    public static final double PRECIO_AROMATERAPIA = 15.0;
    public static final double PRECIO_HIDROMASAJE = 30.0;
    
    @Override
    public Money precioPara(IServicioBase servicio, Periodo periodo, List<String> opciones) {
        if (!(servicio instanceof ServicioAbstracto)) {
            throw new IllegalArgumentException("Esta estrategia solo funciona con ServicioAbstracto");
        }
        
        ServicioAbstracto servicioAbstracto = (ServicioAbstracto) servicio;
        Money precio = servicioAbstracto.getPrecioBase();
        
        // Ajuste por período con descuentos más agresivos
        if (periodo.esSemanal()) {
            precio = precio.multiplicar(5 * MULTIPLICADOR_SEMANAL); // 5 días con 15% descuento
        } else if (periodo.esMensual()) {
            precio = precio.multiplicar(20 * MULTIPLICADOR_MENSUAL); // 20 días con 25% descuento
        } else if (periodo.esDiario()) {
            precio = precio;
        } else {
            long dias = periodo.duracionEnDias();
            precio = precio.multiplicar(dias * MULTIPLICADOR_OTRO); // 10% descuento para otros períodos
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
                return PRECIO_JUEGOS;
            case "entrenamiento":
                return PRECIO_ENTRENAMIENTO;
            case "grooming":
                return PRECIO_GROOMING;
            case "extendido":
                return PRECIO_EXTENDIDO;
            case "parque_especial":
                return PRECIO_PARQUE_ESPECIAL;
            case "suite":
                return PRECIO_SUITE;
            case "personalizado":
                return PRECIO_PERSONALIZADO;
            case "avanzado":
                return PRECIO_AVANZADO;
            case "cama_premium":
                return PRECIO_CAMA_PREMIUM;
            case "masaje":
                return PRECIO_MASAJE;
            case "aromaterapia":
                return PRECIO_AROMATERAPIA;
            case "hidromasaje":
                return PRECIO_HIDROMASAJE;
            default:
                return 0.0;
        }
    }
}

