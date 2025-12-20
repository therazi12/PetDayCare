package roles;

/**
 * Implementación del rol de Cuidador.
 * Puede gestionar el cuidado pero no configurar el centro.
 */
public class RolCuidador implements IRolEmpleado {

    @Override
    public String obtenerNombreRol() {
        return "Cuidador";
    }

    @Override
    public boolean puedeConfigurarCentro() {
        return false; // Los cuidadores no pueden configurar el centro
    }

    @Override
    public boolean puedeGestionarCuidado() {
        return true; // Los cuidadores pueden gestionar el cuidado
    }

    @Override
    public String toString() {
        return obtenerNombreRol();
    }
}

