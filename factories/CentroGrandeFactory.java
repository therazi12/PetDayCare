package factories;

import interfaces.IServicioBase;
import servicios.*;


public class CentroGrandeFactory implements ICentroFactory {

    @Override
    public IServicioBase crearGuarderia() {
        System.out.println("Creando Guardería para Centro Grande");
        return new GuarderiaGrande();
    }

    @Override
    public IServicioBase crearHospedaje() {
        System.out.println("Creando Hospedaje para Centro Grande");
        return new HospedajeGrande();
    }

    @Override
    public IServicioBase crearPaseo() {
        System.out.println("Creando Paseo para Centro Grande");
        return new PaseoGrande();
    }

    @Override
    public IServicioBase crearEntrenamiento() {
        System.out.println("Creando Entrenamiento para Centro Grande");
        return new EntrenamientoGrande();
    }

    @Override
    public IServicioBase crearBienestar() {
        System.out.println("Creando Bienestar para Centro Grande");
        return new BienestarGrande();
    }
}
