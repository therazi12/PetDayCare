import servicios.*;
import factories.*;
import decoradores.*;
import estados.*;
import incidentes.*;
import java.util.Arrays;
import java.util.List;

/**
 * Clase principal que demuestra el funcionamiento del sistema
 * Integra todos los patrones de diseño:
 * - Factory Method
 * - Decorator
 * - State
 * - Chain of Responsibility
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("SISTEMA DE GESTIÓN DE CENTRO DE MASCOTAS");
        System.out.println("=".repeat(80));
        

        System.out.println("\n" + "=".repeat(80));
        System.out.println("Creación de Servicios");
        System.out.println("=".repeat(80));
        
        ICentroFactory centroGrande = new CentroGrandeFactory();
        ICentroFactory centroPequeno = new CentroPequenaFactory();
        
        System.out.println("\n--- Servicios del Centro Grande ---");
        ServicioAbstracto guarderiaGrande = centroGrande.crearGuarderia();
        ServicioAbstracto hospedajeGrande = centroGrande.crearHospedaje();
        
        System.out.println("\n--- Servicios del Centro Pequeño ---");
        ServicioAbstracto paseoPequeno = centroPequeno.crearPaseo();
        

        System.out.println("\n" + "=".repeat(80));
        System.out.println(" Añade Extras a Servicios");
        System.out.println("=".repeat(80));
        
        System.out.println("\n--- Servicio Base ---");
        System.out.println(guarderiaGrande);
        List<String> opciones = Arrays.asList("juegos", "grooming");
        double precioBase = guarderiaGrande.calcularPrecio("diario", opciones, "standard");
        System.out.println("Precio base (diario): $" + precioBase);
        
        System.out.println("\n--- Servicio Decorado (Guardería + Veterinaria + Camas) ---");
        ServicioAbstracto guarderiaDecorada = new AtencionVeterinariaDecorator(
            new CamasEnvioDecorator(guarderiaGrande)
        );
        System.out.println(guarderiaDecorada);
        double precioDecorado = guarderiaDecorada.calcularPrecio("diario", opciones, "standard");
        System.out.println("Precio total decorado: $" + precioDecorado);
        
        System.out.println("\n--- Servicio Triple Decorado (Hospedaje + Todos los Extras) ---");
        ServicioAbstracto hospedajeCompleto = new RecorteTermoRicosDecorator(
            new AtencionVeterinariaDecorator(
                new CamasEnvioDecorator(hospedajeGrande)
            )
        );
        System.out.println(hospedajeCompleto);
        double precioCompleto = hospedajeCompleto.calcularPrecio("semanal", 
            Arrays.asList("suite"), "premium");
        System.out.println("Precio semanal completo: $" + precioCompleto);

        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. PATRÓN STATE - Gestión de Estados de Reservas");
        System.out.println("=".repeat(80));
        
        System.out.println("\n--- Creando Reserva 1 ---");
        Reserva reserva1 = new Reserva("R001", "Juan Pérez", "Max", guarderiaDecorada);
        System.out.println(reserva1);
        
        System.out.println("\n--- Confirmando Reserva 1 (Pendiente -> Confirmada) ---");
        reserva1.confirmar();
        System.out.println(reserva1);
        
        System.out.println("\n--- Intentando confirmar de nuevo (ya está confirmada) ---");
        reserva1.confirmar();
        
        System.out.println("\n--- Creando Reserva 2 ---");
        Reserva reserva2 = new Reserva("R002", "María González", "Luna", paseoPequeno);
        System.out.println(reserva2);
        
        System.out.println("\n--- Cancelando Reserva 2 (Pendiente -> Cancelada) ---");
        reserva2.cancelar("Cliente cambió de planes");
        System.out.println(reserva2);
        
        System.out.println("\n--- Intentando confirmar reserva cancelada (no permitido) ---");
        reserva2.confirmar();
        
        System.out.println("\n--- Creando Reserva 3 y cancelándola después de confirmar ---");
        Reserva reserva3 = new Reserva("R003", "Carlos Rodríguez", "Rocky", hospedajeCompleto);
        System.out.println(reserva3);
        reserva3.confirmar();
        reserva3.cancelar("Emergencia familiar");
        System.out.println(reserva3);
        

        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. PATRÓN CHAIN OF RESPONSIBILITY - Manejo de Incidentes");
        System.out.println("=".repeat(80));

        IHandlerIncidente soporteCentro = new SoporteCentroHandler();
        IHandlerIncidente soportePlataforma = new SoportePlataformaHandler();
        soporteCentro.setNext(soportePlataforma);
        
        System.out.println("\n--- Incidente de Baja Gravedad ---");
        Incidente incidente1 = new Incidente("I001", "Mascota mojó el piso", "baja", "R001");
        incidente1.reportar();
        String resultado1 = soporteCentro.manejar(incidente1);
        
        System.out.println("\n--- Incidente de Media Gravedad ---");
        Incidente incidente2 = new Incidente("I002", "Mascota se escapó del área de juegos", "media", "R002");
        incidente2.reportar();
        String resultado2 = soporteCentro.manejar(incidente2);
        
        System.out.println("\n--- Incidente de Alta Gravedad (Escalado) ---");
        Incidente incidente3 = new Incidente("I003", "Mascota requiere atención veterinaria urgente", "alta", "R003");
        incidente3.reportar();
        String resultado3 = soporteCentro.manejar(incidente3);
        
   
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. FLUJO COMPLETO DEL SISTEMA");
        System.out.println("=".repeat(80));
        
        System.out.println("\n--- Paso 1: Cliente solicita servicio en centro grande ---");
        ICentroFactory factory = new CentroGrandeFactory();
        ServicioAbstracto entrenamientoBase = factory.crearEntrenamiento();
        
        System.out.println("\n--- Paso 2: Se agregan servicios adicionales (Decoradores) ---");
        ServicioAbstracto entrenamientoPremium = new AtencionVeterinariaDecorator(
            new RecorteTermoRicosDecorator(entrenamientoBase)
        );
        System.out.println(entrenamientoPremium);
        
        System.out.println("\n--- Paso 3: Se calcula el precio ---");
        double precioFinal = entrenamientoPremium.calcularPrecio("mensual", 
            Arrays.asList("personalizado", "avanzado"), "premium");
        System.out.println("Precio final mensual: $" + precioFinal);
        
        System.out.println("\n--- Paso 4: Se crea la reserva (Estado: Pendiente) ---");
        Reserva reservaFinal = new Reserva("R004", "Ana Martínez", "Buddy", entrenamientoPremium);
        System.out.println(reservaFinal);
        
        System.out.println("\n--- Paso 5: Cliente confirma la reserva (Estado: Confirmada) ---");
        reservaFinal.confirmar();
        
        System.out.println("\n--- Paso 6: Ocurre un incidente durante el servicio ---");
        Incidente incidenteFinal = new Incidente("I004", 
            "Mascota mostró comportamiento agresivo durante entrenamiento", 
            "media", "R004");
        incidenteFinal.reportar();
        soporteCentro.manejar(incidenteFinal);
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RESUMEN DEL SISTEMA");
        System.out.println("=".repeat(80));
        System.out.println("✓ Factory: Creación flexible de servicios según tipo de centro");
        System.out.println("✓ Decorator: Extensión dinámica de funcionalidades de servicios");
        System.out.println("✓ State: Gestión de estados de reservas con transiciones controladas");
        System.out.println("✓ Chain of Responsibility: Manejo escalado de incidentes");
        System.out.println("=".repeat(80));
    }
}
