package factories;

import servicios.*;


public class CentroPequenaFactory implements ICentroFactory {

    @Override
    public ServicioAbstracto crearGuarderia() {
        System.out.println("Creando Guardería para Centro Pequeño");
        return new GuarderiaPequena();
    }

    @Override
    public ServicioAbstracto crearHospedaje() {
        System.out.println("Creando Hospedaje para Centro Pequeño");
        return new HospedajePequena();
    }

    @Override
    public ServicioAbstracto crearPaseo() {
        System.out.println("Creando Paseo para Centro Pequeño");
        return new PaseoPequena();
    }

    @Override
    public ServicioAbstracto crearEntrenamiento() {
        System.out.println("Creando Entrenamiento para Centro Pequeño");
        return new EntrenamientoPequena();
    }

    @Override
    public ServicioAbstracto crearBienestar() {
        System.out.println("Creando Bienestar para Centro Pequeño");
        return new BienestarPequena();
    }
}
