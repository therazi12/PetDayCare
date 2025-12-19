package servicios;

import java.util.List;


public class BienestarPequena extends ServicioAbstracto {

    public BienestarPequena() {
        super("BP001", "Bienestar Pequeña", "Servicios de spa y bienestar en centro pequeño", 40.0, "Perros, Gatos");
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
        return "Bienestar Pequeña";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("masaje")) {
                    precio += 15.0;
                } else if (opcion.equalsIgnoreCase("aromaterapia")) {
                    precio += 10.0;
                }
            }
        }
        
        return precio;
    }
}
