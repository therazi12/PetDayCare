package servicios;

import java.util.List;


public class HospedajePequena extends ServicioAbstracto {

    public HospedajePequena() {
        super("HP001", "Hospedaje Pequeña", "Alojamiento nocturno para mascotas en centro pequeño", 35.0, "Perros, Gatos");
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
        return "Hospedaje Pequeña";
    }

    @Override
    public double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy) {
        double precio = precioBase;
        
        if (periodo.equalsIgnoreCase("semanal")) {
            precio *= 7 * 0.9;
        } else if (periodo.equalsIgnoreCase("mensual")) {
            precio *= 30 * 0.8;
        }
        
        if (opciones != null) {
            for (String opcion : opciones) {
                if (opcion.equalsIgnoreCase("cama_premium")) {
                    precio += 10.0;
                }
            }
        }
        
        return precio;
    }
}
