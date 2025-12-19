package servicios;

import java.util.List;

public class PaseoPequena extends ServicioAbstracto {

    public PaseoPequena() {
        super("PP001", "Paseo Pequeña", "Servicio de paseo para perros en centro pequeño", 15.0, "Perros");
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
        return "Paseo Pequeña";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 5 * 0.9;
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 20 * 0.85;
        }
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("extendido")) {
                    precio += 5.0;
                }
            }
        }
        
        return precio;
    }
}
