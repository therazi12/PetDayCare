package incidentes;

public class SoporteCentroHandler extends BaseHandlerIncidente {

    @Override
    public String manejar(Incidente incidente) {
        if (incidente.esGravedadLeve()) {
            
            String resultado = formatearRespuesta("Soporte Centro", incidente, "Resuelto localmente");
            
            System.out.println("  [Handler] " + resultado);
            return resultado;
        }
        
        System.out.println("  [Handler] Soporte Centro: Escalando a siguiente nivel...");
        return super.manejar(incidente);
    }
}
