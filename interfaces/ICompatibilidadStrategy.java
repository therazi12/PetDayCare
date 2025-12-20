package interfaces;

/**
 * Interfaz que define la estrategia de verificación de compatibilidad
 * entre servicios y mascotas.
 */
public interface ICompatibilidadStrategy {
    /**
     * Verifica si un servicio es compatible con una mascota específica.
     * 
     * @param servicio El servicio a verificar
     * @param mascota La mascota para la cual se verifica la compatibilidad
     * @return true si el servicio es compatible con la mascota, false en caso contrario
     */
    boolean esCompatible(IServicioBase servicio, Object mascota);
}

