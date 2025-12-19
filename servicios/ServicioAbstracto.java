package servicios;

import java.util.List;

public abstract class ServicioAbstracto {
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected double precioBase;
    protected String tiposMascotaAdmitidos;

    public ServicioAbstracto(String id, String nombre, String descripcion, double precioBase, String tiposMascotaAdmitidos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.tiposMascotaAdmitidos = tiposMascotaAdmitidos;
    }

 
    public abstract boolean esCompatible(String mascota, String regla);
    public abstract boolean verificarDisponibilidad(String periodo);
    public abstract String obtenerNombre();
    public abstract double calcularPrecio(String periodo, List<String> opciones, String pricingStrategy);

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public String getTiposMascotaAdmitidos() {
        return tiposMascotaAdmitidos;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s) - $%.2f - %s", nombre, id, precioBase, descripcion);
    }
}
