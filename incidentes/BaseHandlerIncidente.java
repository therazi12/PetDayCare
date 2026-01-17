package incidentes;


public abstract class BaseHandlerIncidente implements IHandlerIncidente {
    protected IHandlerIncidente nextHandler;

    @Override
    public IHandlerIncidente setNext(IHandlerIncidente handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public String manejar(Incidente incidente) {
        if (nextHandler != null) {
            return nextHandler.manejar(incidente);
        }
        return "No se pudo manejar el incidente: " + incidente.getId();
    }


    protected String formatearRespuesta(String origen, Incidente incidente, String accion) {
        return origen.toUpperCase() + ": Incidente #" + incidente.getId() + 
               " manejado por " + origen.toLowerCase() + ".\n" +
               "  Gravedad: " + incidente.getGravedad() + 
               "\n  Motivo: " + incidente.getMotivo() +
               "\n  Acción: " + accion;
    }
}