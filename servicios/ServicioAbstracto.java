package servicios;

import interfaces.IServicioBase;
import interfaces.ICompatibilidadStrategy;
import interfaces.IPricingStrategy;
import valueobjects.Money;
import valueobjects.Periodo;
import java.util.List;

/**
 * Clase abstracta base para todos los servicios del sistema.
 * Implementa IServicioBase y proporciona funcionalidad común.
 */
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

    // Constructor de conveniencia para mantener compatibilidad con código existente
    public ServicioAbstracto(String id, String nombre, String descripcion, double precioBase, String tiposMascotaAdmitidos) {
        this(id, nombre, descripcion, Money.usd(precioBase), tiposMascotaAdmitidos);
    }

    /**
     * Verifica si el servicio es compatible con una mascota usando una estrategia de compatibilidad.
     * @param mascota La mascota a verificar (puede ser String temporalmente o objeto Mascota)
     * @param regla La estrategia de compatibilidad a utilizar
     * @return true si es compatible, false en caso contrario
     */
    public abstract boolean esCompatible(Object mascota, ICompatibilidadStrategy regla);

    /**
     * Verifica la disponibilidad del servicio para un período dado.
     * @param periodo El período para el cual se verifica la disponibilidad
     * @return true si está disponible, false en caso contrario
     */
    public abstract boolean verificarDisponibilidad(Periodo periodo);

    /**
     * Obtiene el nombre del servicio.
     * Implementación por defecto que retorna el nombre almacenado.
     */
    @Override
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Calcula el precio del servicio usando una estrategia de pricing.
     * Las clases hijas pueden sobrescribir este método para lógica específica.
     */
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
