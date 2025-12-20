package builders;

import dominio.Centro;
import dominio.Mascota;
import dominio.Usuario;
import estados.Reserva;
import interfaces.IServicioBase;
import java.util.ArrayList;
import java.util.List;
import valueobjects.Periodo;

/**
 * ReservaBuilder se encarga de la construcción paso a paso de una Reserva
 * en el sistema PetDayCare, eliminando la necesidad de los antiguos ValueObjects.
 */
public class ReservaBuilder {

    // ATRIBUTOS (Estado interno de construcción)
    private Usuario usuario;
    private Mascota mascota;
    private Centro centro;
    private Periodo periodo;
    private List<IServicioBase> lineasReserva;

    // CONSTRUCTOR DEL BUILDER
    public ReservaBuilder() {
        // Inicializamos la lista para evitar NullPointerException al usar addService
        this.lineasReserva = new ArrayList<>();
    }

    // MÉTODOS DE CONFIGURACIÓN (Fluent Interface)

    public ReservaBuilder setUsuario(Usuario u) {
        this.usuario = u;
        return this; // Permite el encadenamiento de métodos
    }

    public ReservaBuilder setMascota(Mascota m) {
        this.mascota = m;
        return this;
    }

    public ReservaBuilder setCentro(Centro c) {
        this.centro = c;
        return this;
    }

    public ReservaBuilder setPeriodo(Periodo p) {
        this.periodo = p;
        return this;
    }

    /**
     * addService recibe un objeto que implementa IServicioBase.
     * Gracias al patrón DECORATOR, este objeto ya puede venir "envuelto"
     * con opciones adicionales (cámaras, masajes, etc.).
     */
    public ReservaBuilder addService(IServicioBase s) {
        if (s == null) {
            throw new IllegalArgumentException("El servicio no puede ser null");
        }
        this.lineasReserva.add(s);
        return this;
    }

    // MÉTODO DE FINALIZACIÓN

    /**
     * El método build es el paso final. No recibe parámetros porque utiliza
     * los atributos internos previamente configurados.
     */
    public Reserva build() {
        // Aquí se podría incluir lógica de validación antes de instanciar
        if (usuario == null || mascota == null || centro == null || periodo == null) {
            throw new IllegalStateException("Faltan datos obligatorios para crear la reserva.");
        }
        
        if (lineasReserva.isEmpty()) {
            throw new IllegalStateException("La reserva debe tener al menos un servicio.");
        }
        
        // Retorna el producto final: una instancia de Reserva configurada
        return new Reserva(this.usuario, this.mascota, this.centro, this.periodo, this.lineasReserva);
    }
}

