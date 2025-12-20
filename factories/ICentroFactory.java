package factories;

import interfaces.IServicioBase;

/**
 * Interfaz Abstract Factory para crear familias de servicios relacionados
 * según el tipo de centro (pequeño o grande).
 */
public interface ICentroFactory {
    IServicioBase crearGuarderia();
    IServicioBase crearHospedaje();
    IServicioBase crearPaseo();
    IServicioBase crearEntrenamiento();
    IServicioBase crearBienestar();
}
