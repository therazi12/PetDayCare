package factories;

import servicios.*;


public class CentroGrandeFactory implements ICentroFactory {

    @Override
    public ServicioAbstracto crearGuarderia() {
        System.out.println("Creando Guardería para Centro Grande");
        return new GuarderiaGrande();
    }

    @Override
    public ServicioAbstracto crearHospedaje() {
        System.out.println("Creando Hospedaje para Centro Grande");
        return new HospedajeGrande();
    }

    @Override
    public ServicioAbstracto crearPaseo() {
        System.out.println("Creando Paseo para Centro Grande");
        return new PaseoGrande();
    }

    @Override
    public ServicioAbstracto crearEntrenamiento() {
        System.out.println("Creando Entrenamiento para Centro Grande");
        return new EntrenamientoGrande();
    }

    @Override
    public ServicioAbstracto crearBienestar() {
        System.out.println("Creando Bienestar para Centro Grande");
        return new BienestarGrande();
    }
}
