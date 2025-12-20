package factories;

import interfaces.IServicioBase;
import servicios.*;


public class CentroPequenaFactory implements ICentroFactory {

    @Override
    public IServicioBase crearGuarderia() {
        System.out.println("Creando Guardería para Centro Pequeño");
        return new GuarderiaPequena();
    }

    @Override
    public IServicioBase crearHospedaje() {
        System.out.println("Creando Hospedaje para Centro Pequeño");
        return new HospedajePequena();
    }

    @Override
    public IServicioBase crearPaseo() {
        System.out.println("Creando Paseo para Centro Pequeño");
        return new PaseoPequena();
    }

    @Override
    public IServicioBase crearEntrenamiento() {
        System.out.println("Creando Entrenamiento para Centro Pequeño");
        return new EntrenamientoPequena();
    }

    @Override
    public IServicioBase crearBienestar() {
        System.out.println("Creando Bienestar para Centro Pequeño");
        return new BienestarPequena();
    }
}
