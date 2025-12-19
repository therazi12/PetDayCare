package servicios;

import java.util.List;


public class HospedajeGrande extends ServicioAbstracto {

    public HospedajeGrande() {
        super("HG001", "Hospedaje Grande", "Alojamiento nocturno premium para mascotas en centro grande", 55.0, "Perros, Gatos, Aves, Reptiles");
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
        return "Hospedaje Grande";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 7 * 0.85;
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 30 * 0.75;
        }
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("cama_premium")) {
                    precio += 15.0;
                } else if (opcion.equalsIgnoreCase("suite")) {
                    precio += 25.0;
                }
            }
        }
        
        return precio;
    }
}
