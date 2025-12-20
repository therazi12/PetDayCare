package roles;

/**
 * Implementación del rol de Administrador.
 * Tiene todos los permisos del sistema.
 */
public class RolAdministrador implements IRolEmpleado {

    @Override
    public String obtenerNombreRol() {
        return "Administrador";
    }

    @Override
    public boolean puedeConfigurarCentro() {
        return true; // Los administradores pueden configurar el centro
    }

    @Override
    public boolean puedeGestionarCuidado() {
        return true; // Los administradores pueden gestionar el cuidado
    }

    @Override
    public String toString() {
        return obtenerNombreRol();
    }
}

