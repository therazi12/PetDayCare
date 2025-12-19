package servicios;

import java.util.List;

public class BienestarGrande extends ServicioAbstracto {

    public BienestarGrande() {
        super("BG001", "Bienestar Grande", "Servicios de spa y bienestar premium en centro grande", 65.0, "Perros, Gatos, Aves");
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
        return "Bienestar Grande";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("masaje")) {
                    precio += 25.0;
                } else if (opcion.equalsIgnoreCase("aromaterapia")) {
                    precio += 15.0;
                } else if (opcion.equalsIgnoreCase("hidromasaje")) {
                    precio += 30.0;
                }
            }
        }
        
        return precio;
    }
}
