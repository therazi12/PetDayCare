package incidentes;

public interface IHandlerIncidente {
    IHandlerIncidente setNext(IHandlerIncidente handler);
    String manejar(Incidente incidente);
}
