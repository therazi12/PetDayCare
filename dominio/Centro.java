package dominio;

import factories.ICentroFactory;
import interfaces.IServicioBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Clase que representa un centro de cuidado de mascotas en el sistema PetDayCare.
 */
public class Centro {
    private String id;
    private String nombre;
    private String direccion;
    private int capacidad;
    private ICentroFactory servicioFactory;
    private PoliticaCancelacion politicaCancelacion;
    private Map<String, Disponibilidad> disponibilidades;

    public Centro(String id, String nombre, String direccion, int capacidad) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser null o vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede ser null o vacía");
        }
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.disponibilidades = new HashMap<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ICentroFactory getServicioFactory() {
        return servicioFactory;
    }

    // Setters
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser null o vacío");
        }
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección no puede ser null o vacía");
        }
        this.direccion = direccion;
    }

    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor que cero");
        }
        this.capacidad = capacidad;
    }

    /**
     * Asigna una factory de servicios al centro.
     * Esto determina qué tipo de servicios (pequeños o grandes) puede crear el centro.
     */
    public void asignarFabrica(ICentroFactory factory) {
        if (factory == null) {
            throw new IllegalArgumentException("La factory no puede ser null");
        }
        this.servicioFactory = factory;
        System.out.println("  [Centro] Factory asignada al centro " + nombre);
    }

    /**
     * Define la política de cancelación del centro.
     */
    public void definirPoliticaCancelacion(PoliticaCancelacion politica) {
        if (politica == null) {
            throw new IllegalArgumentException("La política de cancelación no puede ser null");
        }
        this.politicaCancelacion = politica;
        System.out.println("  [Centro] Política de cancelación definida para el centro " + nombre);
    }

    /**
     * Programa una disponibilidad para el centro.
     */
    public void programarDisponibilidad(Disponibilidad disponibilidad) {
        if (disponibilidad == null) {
            throw new IllegalArgumentException("La disponibilidad no puede ser null");
        }
        String key = disponibilidad.getId();
        disponibilidades.put(key, disponibilidad);
        System.out.println("  [Centro] Disponibilidad programada para el centro " + nombre);
    }

    /**
     * Obtiene la política de cancelación del centro.
     */
    public PoliticaCancelacion getPoliticaCancelacion() {
        return politicaCancelacion;
    }

    /**
     * Obtiene todas las disponibilidades del centro.
     */
    public Map<String, Disponibilidad> getDisponibilidades() {
        return new HashMap<>(disponibilidades); // Retornar copia para inmutabilidad
    }

    /**
     * Obtiene un servicio del tipo especificado usando la factory del centro.
     * 
     * @param tipo El tipo de servicio a obtener: "guarderia", "hospedaje", "paseo", "entrenamiento", "bienestar"
     * @return El servicio solicitado o null si el tipo no es válido o no hay factory asignada
     */
    public IServicioBase obtenerServicio(String tipo) {
        if (servicioFactory == null) {
            throw new IllegalStateException("El centro no tiene una factory asignada");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de servicio no puede ser null o vacío");
        }
        
        tipo = tipo.toLowerCase().trim();
        
        switch (tipo) {
            case "guarderia":
                return servicioFactory.crearGuarderia();
            case "hospedaje":
                return servicioFactory.crearHospedaje();
            case "paseo":
                return servicioFactory.crearPaseo();
            case "entrenamiento":
                return servicioFactory.crearEntrenamiento();
            case "bienestar":
                return servicioFactory.crearBienestar();
            default:
                throw new IllegalArgumentException("Tipo de servicio no válido: " + tipo + 
                    ". Tipos válidos: guarderia, hospedaje, paseo, entrenamiento, bienestar");
        }
    }

    /**
     * Verifica si el centro tiene capacidad disponible.
     */
    public boolean tieneCapacidadDisponible(int cantidadRequerida) {
        return cantidadRequerida > 0 && cantidadRequerida <= capacidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Centro centro = (Centro) obj;
        return Objects.equals(id, centro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Centro[id=%s, nombre=%s, direccion=%s, capacidad=%d, tieneFactory=%s]",
                id, nombre, direccion, capacidad, servicioFactory != null);
    }
}

