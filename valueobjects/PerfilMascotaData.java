package valueobjects;

import java.util.Objects;

/**
 * Value Object que representa los datos del perfil de una mascota.
 * Inmutable y con validaciones.
 */
public class PerfilMascotaData {
    private final String especie;
    private final String raza;
    private final String tamaño;
    private final int edad;
    private final String necesidadesEspeciales;

    public PerfilMascotaData(String especie, String raza, String tamaño, int edad, String necesidadesEspeciales) {
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("La especie no puede ser null o vacía");
        }
        if (raza == null || raza.trim().isEmpty()) {
            throw new IllegalArgumentException("La raza no puede ser null o vacía");
        }
        if (tamaño == null || tamaño.trim().isEmpty()) {
            throw new IllegalArgumentException("El tamaño no puede ser null o vacío");
        }
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        
        this.especie = especie.trim();
        this.raza = raza.trim();
        this.tamaño = tamaño.trim();
        this.edad = edad;
        this.necesidadesEspeciales = necesidadesEspeciales != null ? necesidadesEspeciales.trim() : "";
    }

    // Getters (métodos que retornan valores según el diagrama)
    public String especie() {
        return especie;
    }

    public String raza() {
        return raza;
    }

    public String tamaño() {
        return tamaño;
    }

    public int edad() {
        return edad;
    }

    public String necesidadesEspeciales() {
        return necesidadesEspeciales;
    }

    // Métodos de utilidad
    public boolean tieneNecesidadesEspeciales() {
        return necesidadesEspeciales != null && !necesidadesEspeciales.isEmpty();
    }

    public boolean esCachorro() {
        return edad < 1;
    }

    public boolean esAdulto() {
        return edad >= 1 && edad < 7;
    }

    public boolean esSenior() {
        return edad >= 7;
    }

    public boolean esTamañoGrande() {
        return tamaño.equalsIgnoreCase("grande") || tamaño.equalsIgnoreCase("large");
    }

    public boolean esTamañoMediano() {
        return tamaño.equalsIgnoreCase("mediano") || tamaño.equalsIgnoreCase("medium") || 
               tamaño.equalsIgnoreCase("mediana");
    }

    public boolean esTamañoPequeño() {
        return tamaño.equalsIgnoreCase("pequeño") || tamaño.equalsIgnoreCase("small") || 
               tamaño.equalsIgnoreCase("pequeña");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PerfilMascotaData that = (PerfilMascotaData) obj;
        return edad == that.edad &&
               Objects.equals(especie, that.especie) &&
               Objects.equals(raza, that.raza) &&
               Objects.equals(tamaño, that.tamaño) &&
               Objects.equals(necesidadesEspeciales, that.necesidadesEspeciales);
    }

    @Override
    public int hashCode() {
        return Objects.hash(especie, raza, tamaño, edad, necesidadesEspeciales);
    }

    @Override
    public String toString() {
        return String.format("PerfilMascotaData[especie=%s, raza=%s, tamaño=%s, edad=%d, necesidadesEspeciales=%s]",
                especie, raza, tamaño, edad, necesidadesEspeciales);
    }
}

