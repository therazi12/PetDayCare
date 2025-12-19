package servicios;

import java.util.List;


public class PaseoGrande extends ServicioAbstracto {

    public PaseoGrande() {
        super("PG001", "Paseo Grande", "Servicio de paseo premium para perros en centro grande", 25.0, "Perros");
    }

    @Override
    public boolean esCompatible(String mascota, String regla) {
        return mascota.equalsIgnoreCase("perro") || mascota.equalsIgnoreCase("perros");
    }

    @Override
    public boolean verificarDisponibilidad(String periodo) {
        return true;
    }

    @Override
    public String obtenerNombre() {
        return "Paseo Grande";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 5 * 0.85;
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 20 * 0.80;
        }
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("extendido")) {
                    precio += 8.0;
                } else if (opcion.equalsIgnoreCase("parque_especial")) {
                    precio += 10.0;
                }
            }
        }
        
        return precio;
    }
}
