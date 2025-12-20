package dominio;

import interfaces.IPagoProcessor;
import interfaces.IPricingStrategy;
import interfaces.IServicioBase;
import java.util.ArrayList;
import java.util.Objects;
import valueobjects.Money;
import valueobjects.Periodo;

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
     * Establece el precio de la línea de reserva usando una estrategia de pricing.
     * Según el diagrama UML, este método debe recibir IPricingStrategy.
     */
    public void establecerPrecio(IPricingStrategy pricing) {
        if (pricing == null) {
            throw new IllegalArgumentException("La estrategia de pricing no puede ser null");
        }
        // Para calcular el precio necesitamos un período, pero no lo tenemos aquí
        // Por ahora mantenemos el precio actual o lo calculamos con un período por defecto
        // En producción, esto debería recibir también el período
        Periodo periodoPorDefecto = new Periodo(
            java.time.LocalDateTime.now(),
            java.time.LocalDateTime.now().plusDays(1)
        );
        this.precio = servicio.calcularPrecio(periodoPorDefecto, new ArrayList<>(), pricing);
    }

    /**
     * Establece el precio de la línea de reserva directamente.
     * Método de conveniencia para establecer precio sin estrategia.
     */
    public void establecerPrecio(Money nuevoPrecio) {
        if (nuevoPrecio == null) {
            throw new IllegalArgumentException("El precio no puede ser null");
        }
        this.precio = nuevoPrecio;
    }

    /**
     * Captura el pago de esta línea de reserva usando un procesador de pagos.
     */
    public void capturar(IPagoProcessor proc) {
        if (proc == null) {
            throw new IllegalArgumentException("El procesador de pagos no puede ser null");
        }
        String referencia = "linea-" + id;
        proc.capturar(referencia);
        System.out.println("  [LineaReserva] Pago capturado para línea #" + id);
    }

    /**
     * Reembolsa el pago de esta línea de reserva usando un procesador de pagos.
     */
    public void reembolsar(IPagoProcessor proc) {
        if (proc == null) {
            throw new IllegalArgumentException("El procesador de pagos no puede ser null");
        }
        String referencia = "linea-" + id;
        proc.reembolsar(referencia, precio);
        System.out.println("  [LineaReserva] Reembolso procesado para línea #" + id + ". Monto: " + precio.toString());
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

