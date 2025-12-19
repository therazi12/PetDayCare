package incidentes;

public class SoporteCentroHandler extends BaseHandlerIncidente {

    @Override
    public String manejar(Incidente incidente) {
        if (incidente.getGravedad().equalsIgnoreCase("baja") || 
            incidente.getGravedad().equalsIgnoreCase("media")) {
            
            String resultado = "SOPORTE CENTRO: Incidente #" + incidente.getId() + 
                              " manejado por el centro local.\n" +
                              "  Gravedad: " + incidente.getGravedad() + 
                              "\n  Motivo: " + incidente.getMotivo() +
                              "\n  Acción: Resuelto localmente";
            
            System.out.println("  [Handler] " + resultado);
            return resultado;
        }
        
        System.out.println("  [Handler] Soporte Centro: Escalando a siguiente nivel...");
        return super.manejar(incidente);
    }
}
