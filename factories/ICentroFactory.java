package factories;

import servicios.ServicioAbstracto;


public interface ICentroFactory {
    ServicioAbstracto crearGuarderia();
    ServicioAbstracto crearHospedaje();
    ServicioAbstracto crearPaseo();
    ServicioAbstracto crearEntrenamiento();
    ServicioAbstracto crearBienestar();
}
