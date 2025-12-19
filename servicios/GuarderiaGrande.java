package servicios;

import java.util.List;


public class GuarderiaGrande extends ServicioAbstracto {

    public GuarderiaGrande() {
        super("GG001", "Guardería Grande", "Cuidado diario premium para mascotas en centro grande", 40.0, "Perros, Gatos, Aves");
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
        return "Guardería Grande";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 5 * 0.85;
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 20 * 0.75;
        }
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("juegos")) {
                    precio += 8.0;
                } else if (opcion.equalsIgnoreCase("entrenamiento")) {
                    precio += 15.0;
                } else if (opcion.equalsIgnoreCase("grooming")) {
                    precio += 12.0;
                }
            }
        }
        
        return precio;
    }
}
