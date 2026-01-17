package dominio;

import enums.MetodoNotificacion;
import java.util.Objects;

/**
 * Clase que representa un usuario del sistema PetDayCare.
 */
public class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private MetodoNotificacion medioPreferidoNotificacion;

    public Usuario(String id, String nombre, String email, String telefono, MetodoNotificacion medioPreferidoNotificacion) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser null o vacío");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email debe tener un formato válido");
        }
        if (medioPreferidoNotificacion == null) {
            throw new IllegalArgumentException("El método de notificación preferido no puede ser null");
        }
        
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono != null ? telefono : "";
        this.medioPreferidoNotificacion = medioPreferidoNotificacion;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public MetodoNotificacion getMedioPreferidoNotificacion() {
        return medioPreferidoNotificacion;
    }

    // Setters
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser null o vacío");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email debe tener un formato válido");
        }
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono != null ? telefono : "";
    }

    public void setMedioPreferidoNotificacion(MetodoNotificacion medioPreferidoNotificacion) {
        if (medioPreferidoNotificacion == null) {
            throw new IllegalArgumentException("El método de notificación preferido no puede ser null");
        }
        this.medioPreferidoNotificacion = medioPreferidoNotificacion;
    }


    public void actualizarContacto(String email, String telefono) {
        setEmail(email);
        setTelefono(telefono);
    }


    public void registrarMascota(dominio.Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser null");
        }
        System.out.println("  [Usuario] " + nombre + " registró la mascota: " + mascota.getNombre());
    }

    public void recibirNotificacion(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        
        System.out.println("  [Usuario] " + nombre + " recibió notificación por " + 
        medioPreferidoNotificacion + ":");
        System.out.println("    >> " + mensaje);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Usuario[id=%s, nombre=%s, email=%s, telefono=%s, notificacion=%s]",
                id, nombre, email, telefono, medioPreferidoNotificacion);
    }
}