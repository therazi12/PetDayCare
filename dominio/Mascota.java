package dominio;

import java.util.Objects;
import valueobjects.PerfilMascotaData;

/**
 * Clase que representa una mascota en el sistema PetDayCare.
 */
public class Mascota {
    private String id;
    private String especie;
    private String nombre;
    private String raza;
    private String tamaño;
    private int edad;
    private String necesidadesEspeciales;

    public Mascota(String id, String nombre, String especie, String raza, String tamaño, int edad, String necesidadesEspeciales) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
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
        
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.tamaño = tamaño;
        this.edad = edad;
        this.necesidadesEspeciales = necesidadesEspeciales != null ? necesidadesEspeciales : "";
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getEspecie() {
        return especie;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }

    public String getTamaño() {
        return tamaño;
    }

    public int getEdad() {
        return edad;
    }

    public String getNecesidadesEspeciales() {
        return necesidadesEspeciales;
    }

    /**
     * Actualiza el perfil de la mascota usando un objeto PerfilMascotaData.
     */
    public void actualizarPerfil(PerfilMascotaData datos) {
        if (datos == null) {
            throw new IllegalArgumentException("Los datos del perfil no pueden ser null");
        }
        this.especie = datos.especie();
        this.raza = datos.raza();
        this.tamaño = datos.tamaño();
        this.edad = datos.edad();
        this.necesidadesEspeciales = datos.necesidadesEspeciales();
    }

    /**
     * Crea un objeto PerfilMascotaData a partir de los datos actuales de la mascota.
     */
    public PerfilMascotaData obtenerPerfil() {
        return new PerfilMascotaData(especie, raza, tamaño, edad, necesidadesEspeciales);
    }

    /**
     * Verifica si la mascota tiene necesidades especiales.
     */
    public boolean tieneNecesidadesEspeciales() {
        return necesidadesEspeciales != null && !necesidadesEspeciales.trim().isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mascota mascota = (Mascota) obj;
        return Objects.equals(id, mascota.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Mascota[id=%s, nombre=%s, especie=%s, raza=%s, tamaño=%s, edad=%d, necesidadesEspeciales=%s]",
                id, nombre, especie, raza, tamaño, edad, necesidadesEspeciales);
    }
}

