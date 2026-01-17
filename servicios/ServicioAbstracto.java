package servicios;

import dominio.Mascota;

import interfaces.IServicioBase;
import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;


public abstract class ServicioAbstracto implements IServicioBase {
    protected String id;
    protected String nombre;
    protected String descripcion;
    protected Money precioBase;
    protected String tiposMascotaAdmitidos;

    public ServicioAbstracto(String id, String nombre, String descripcion, Money precioBase, String tiposMascotaAdmitidos) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        if (precioBase == null) {
            throw new IllegalArgumentException("El precio base no puede ser null");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion != null ? descripcion : "";
        this.precioBase = precioBase;
        this.tiposMascotaAdmitidos = tiposMascotaAdmitidos != null ? tiposMascotaAdmitidos : "";
    }

    public ServicioAbstracto(String id, String nombre, String descripcion, double precioBase, String tiposMascotaAdmitidos) {
        this(id, nombre, descripcion, Money.usd(precioBase), tiposMascotaAdmitidos);
    }

    
    public boolean esCompatible(Mascota mascota, ICompatibilidadStrategy regla) {
        if (regla == null) {
            regla = new strategies.CompatibilidadStrategyBasica();
        }
        return regla.esCompatible(this, mascota);
    }

    
    public abstract boolean verificarDisponibilidad(Periodo periodo);

    
    @Override
    public String obtenerNombre() {
        return nombre;
    }

    
    @Override
    public Money calcularPrecio(Periodo periodo, List<String> opciones, IPricingStrategy pricing) {
        if (periodo == null) {
            throw new IllegalArgumentException("El período no puede ser null");
        }
        if (pricing == null) {
            throw new IllegalArgumentException("La estrategia de pricing no puede ser null");
        }
        return pricing.precioPara(this, periodo, opciones);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Money getPrecioBase() {
        return precioBase;
    }

    public String getTiposMascotaAdmitidos() {
        return tiposMascotaAdmitidos;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s) - %s - %s", nombre, id, precioBase.toString(), descripcion);
    }
}
