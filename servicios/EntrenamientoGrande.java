package servicios;

import java.util.List;

public class EntrenamientoGrande extends ServicioAbstracto {

    public EntrenamientoGrande() {
        super("EG001", "Entrenamiento Grande", "Sesiones de entrenamiento profesional en centro grande", 50.0, "Perros, Gatos, Aves");
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
        return "Entrenamiento Grande";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 3 * 0.85;
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 12 * 0.80;
        }
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("avanzado")) {
                    precio += 25.0;
                } else if (opcion.equalsIgnoreCase("personalizado")) {
                    precio += 30.0;
                }
            }
        }
        
        return precio;
    }
}
