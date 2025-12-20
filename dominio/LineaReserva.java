package dominio;

import interfaces.IServicioBase;
import java.util.Objects;
import valueobjects.Money;

/**
 * Representa una línea individual de servicio dentro de una reserva.
 * Cada línea contiene un servicio y su precio calculado.
 */
public class LineaReserva {
    private String id;
    private IServicioBase servicio;
    private Money precio;

    public LineaReserva(String id, IServicioBase servicio, Money precio) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser null");
        }
        if (precio == null) {
            throw new IllegalArgumentException("El precio no puede ser null");
        }
        
        this.id = id;
        this.servicio = servicio;
        this.precio = precio;
    }

    // Getters
    public String getId() {
        return id;
    }

    public IServicioBase getServicio() {
        return servicio;
    }

    public Money getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la línea de reserva.
     */
    public void establecerPrecio(Money nuevoPrecio) {
        if (nuevoPrecio == null) {
            throw new IllegalArgumentException("El precio no puede ser null");
        }
        this.precio = nuevoPrecio;
    }

    /**
     * Obtiene el servicio asociado a esta línea.
     */
    public IServicioBase obtenerServicio() {
        return servicio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LineaReserva that = (LineaReserva) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("LineaReserva[id=%s, servicio=%s, precio=%s]",
                id, servicio.obtenerNombre(), precio.toString());
    }
}

