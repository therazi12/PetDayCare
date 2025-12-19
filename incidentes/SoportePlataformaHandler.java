package incidentes;


public class SoportePlataformaHandler extends BaseHandlerIncidente {

    @Override
    public String manejar(Incidente incidente) {
        if (incidente.getGravedad().equalsIgnoreCase("alta")) {
            
            String resultado = "SOPORTE PLATAFORMA: Incidente #" + incidente.getId() + 
                              " manejado por el equipo central.\n" +
                              "  Gravedad: " + incidente.getGravedad() + 
                              "\n  Motivo: " + incidente.getMotivo() +
                              "\n  Acción: Intervención directa del equipo de emergencia";
            
            System.out.println("  [Handler] " + resultado);
            return resultado;
        }
        
        System.out.println("  [Handler] Soporte Plataforma: No se puede manejar este tipo de incidente");
        return super.manejar(incidente);
    }
}
