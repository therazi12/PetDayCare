package dominio;

import roles.IRolEmpleado;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Clase que representa un empleado del sistema PetDayCare.
 */
public class Empleado {
    private String id;
    private String centroId;
    private String nombre;
    private IRolEmpleado rol;
    private Set<String> reservasAsignadas; // IDs de reservas asignadas

    public Empleado(String id, String centroId, String nombre, IRolEmpleado rol) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (centroId == null || centroId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del centro no puede ser null o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser null");
        }
        
        this.id = id;
        this.centroId = centroId;
        this.nombre = nombre;
        this.rol = rol;
        this.reservasAsignadas = new HashSet<>();
    }

    /**
     * Asigna el empleado a una reserva.
     */
    public void asignarA(String reservaId) {
        if (reservaId == null || reservaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de reserva no puede ser null o vacío");
        }
        reservasAsignadas.add(reservaId);
        System.out.println("  [Empleado] " + nombre + " asignado a reserva #" + reservaId);
    }

    /**
     * Libera al empleado de una reserva.
     */
    public void liberarDe(String reservaId) {
        if (reservaId == null || reservaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de reserva no puede ser null o vacío");
        }
        if (reservasAsignadas.remove(reservaId)) {
            System.out.println("  [Empleado] " + nombre + " liberado de reserva #" + reservaId);
        } else {
            System.out.println("  [Empleado] " + nombre + " no estaba asignado a reserva #" + reservaId);
        }
    }

    /**
     * Verifica si el empleado está asignado a una reserva específica.
     */
    public boolean estaAsignadoA(String reservaId) {
        return reservasAsignadas.contains(reservaId);
    }

    /**
     * Obtiene el número de reservas asignadas al empleado.
     */
    public int getNumeroReservasAsignadas() {
        return reservasAsignadas.size();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCentroId() {
        return centroId;
    }

    public String getNombre() {
        return nombre;
    }

    public IRolEmpleado getRol() {
        return rol;
    }

    public Set<String> getReservasAsignadas() {
        return new HashSet<>(reservasAsignadas); // Retornar copia para inmutabilidad
    }

    // Setters
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        this.nombre = nombre;
    }

    public void setRol(IRolEmpleado rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser null");
        }
        this.rol = rol;
    }

    /**
     * Verifica si el empleado puede configurar el centro según su rol.
     */
    public boolean puedeConfigurarCentro() {
        return rol.puedeConfigurarCentro();
    }

    /**
     * Verifica si el empleado puede gestionar el cuidado según su rol.
     */
    public boolean puedeGestionarCuidado() {
        return rol.puedeGestionarCuidado();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Empleado empleado = (Empleado) obj;
        return Objects.equals(id, empleado.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Empleado[id=%s, nombre=%s, centroId=%s, rol=%s, reservasAsignadas=%d]",
                id, nombre, centroId, rol.obtenerNombreRol(), reservasAsignadas.size());
    }
}

