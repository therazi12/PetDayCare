package servicios;

import java.util.List;


public class EntrenamientoPequena extends ServicioAbstracto {

    public EntrenamientoPequena() {
        super("EP001", "Entrenamiento Pequeña", "Sesiones de entrenamiento básico en centro pequeño", 30.0, "Perros, Gatos");
    }

    @Override
    public boolean esCompatible(String mascota, String regla) {
        return tiposMascotaAdmitidos.toLowerCase().contains(mascota.toLowerCase());
    }

    @Override
    public boolean verificarDisponibilidad(String periodo) {
        return true;
    }

    @Override
    public String obtenerNombre() {
        return "Entrenamiento Pequeña";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 3 * 0.9;
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 12 * 0.85;
        }
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("avanzado")) {
                    precio += 15.0;
                }
            }
        }
        
        return precio;
    }
}
