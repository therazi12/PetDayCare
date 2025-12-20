package roles;

/**
 * Interfaz que define el contrato para los roles de empleado en el sistema.
 */
public interface IRolEmpleado {
    /**
     * Obtiene el nombre del rol.
     * @return El nombre del rol
     */
    String obtenerNombreRol();

    /**
     * Indica si el rol tiene permisos para configurar el centro.
     * @return true si puede configurar el centro, false en caso contrario
     */
    boolean puedeConfigurarCentro();

    /**
     * Indica si el rol tiene permisos para gestionar el cuidado de mascotas.
     * @return true si puede gestionar cuidado, false en caso contrario
     */
    boolean puedeGestionarCuidado();
}

