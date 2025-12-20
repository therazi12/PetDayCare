package strategies;

import interfaces.ICompatibilidadStrategy;
import interfaces.IServicioBase;
import servicios.ServicioAbstracto;

/**
 * Estrategia básica de compatibilidad que verifica si el tipo de mascota
 * está en la lista de tipos admitidos del servicio.
 */
public class CompatibilidadStrategyBasica implements ICompatibilidadStrategy {
    
    @Override
    public boolean esCompatible(IServicioBase servicio, Object mascota) {
        if (!(servicio instanceof ServicioAbstracto)) {
            return false;
        }
        
        ServicioAbstracto servicioAbstracto = (ServicioAbstracto) servicio;
        String tiposAdmitidos = servicioAbstracto.getTiposMascotaAdmitidos();
        
        // Si la mascota es un String (compatibilidad temporal)
        if (mascota instanceof String) {
            String tipoMascota = (String) mascota;
            return tiposAdmitidos.toLowerCase().contains(tipoMascota.toLowerCase());
        }
        
        // Si la mascota es un objeto con método getEspecie() o similar
        // (se implementará cuando exista la clase Mascota)
        try {
            java.lang.reflect.Method metodo = mascota.getClass().getMethod("getEspecie");
            String especie = (String) metodo.invoke(mascota);
            return tiposAdmitidos.toLowerCase().contains(especie.toLowerCase());
        } catch (Exception e) {
            // Si no tiene el método, retornar false
            return false;
        }
    }
}

