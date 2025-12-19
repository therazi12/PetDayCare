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
}
