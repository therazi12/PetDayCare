package servicios;

import java.util.List;

public class GuarderiaPequena extends ServicioAbstracto {

    public GuarderiaPequena() {
        super("GP001", "Guardería Pequeña", "Cuidado diario para mascotas en centro pequeño", 25.0, "Perros, Gatos");
    }

    @Override
    public boolean esCompatible(String mascota, String regla) {
        return tiposMascotaAdmitidos.toLowerCase().contains(mascota.toLowerCase());
    }

    @Override
    public boolean verificarDisponibilidad(String periodo) {
        // Lógica simplificada - siempre hay disponibilidad en centros pequeños
        return true;
    }

    @Override
    public String obtenerNombre() {
        return "Guardería Pequeña";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        // Ajuste por período
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 5 * 0.9; // 5 días con 10% descuento
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 20 * 0.8; // 20 días con 20% descuento
        }
        
        // Opciones adicionales
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("juegos")) {
                    precio += 5.0;
                } else if (opcion.equalsIgnoreCase("entrenamiento")) {
                    precio += 10.0;
                }
            }
        }
        
        return precio;
    }
}
