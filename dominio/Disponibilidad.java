package dominio;

import java.util.Objects;
import valueobjects.Periodo;

/**
 * Representa la disponibilidad de un centro para un período específico.
 * Gestiona cupos totales y ocupados.
 */
public class Disponibilidad {
    private String id;
    private Periodo rango;
    private int cuposTotales;
    private int cuposOcupados;

    public Disponibilidad(String id, Periodo rango, int cuposTotales) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (rango == null) {
            throw new IllegalArgumentException("El rango no puede ser null");
        }
        if (cuposTotales <= 0) {
            throw new IllegalArgumentException("Los cupos totales deben ser mayores que cero");
        }
        
        this.id = id;
        this.rango = rango;
        this.cuposTotales = cuposTotales;
        this.cuposOcupados = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public Periodo getRango() {
        return rango;
    }

    public int getCuposTotales() {
        return cuposTotales;
    }

    public int getCuposOcupados() {
        return cuposOcupados;
    }

    public int getCuposDisponibles() {
        return cuposTotales - cuposOcupados;
    }

    /**
     * Verifica si hay cupo disponible para la cantidad solicitada.
     */
    public boolean hayCupoPara(int cuantos) {
        if (cuantos <= 0) {
            return false;
        }
        return (cuposOcupados + cuantos) <= cuposTotales;
    }

    /**
     * Bloquea una cantidad de cupos.
     */
    public void bloquear(int cuantos) {
        if (cuantos <= 0) {
            throw new IllegalArgumentException("La cantidad a bloquear debe ser mayor que cero");
        }
        if (!hayCupoPara(cuantos)) {
            throw new IllegalStateException("No hay suficientes cupos disponibles. Disponibles: " + getCuposDisponibles() + ", Solicitados: " + cuantos);
        }
        this.cuposOcupados += cuantos;
    }

    /**
     * Libera una cantidad de cupos.
     */
    public void liberar(int cuantos) {
        if (cuantos <= 0) {
            throw new IllegalArgumentException("La cantidad a liberar debe ser mayor que cero");
        }
        if (cuposOcupados < cuantos) {
            throw new IllegalStateException("No se pueden liberar más cupos de los que están ocupados. Ocupados: " + cuposOcupados + ", Intentando liberar: " + cuantos);
        }
        this.cuposOcupados -= cuantos;
    }

    /**
     * Verifica si la disponibilidad está completamente ocupada.
     */
    public boolean estaCompleta() {
        return cuposOcupados >= cuposTotales;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Disponibilidad that = (Disponibilidad) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Disponibilidad[id=%s, rango=%s, cupos=%d/%d]",
                id, rango.toString(), cuposOcupados, cuposTotales);
    }
}

