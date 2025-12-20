import adapters.*;
import builders.*;
import decoradores.*;
import dominio.*;
import enums.MetodoNotificacion;
import estados.*;
import factories.*;
import incidentes.*;
import interfaces.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import notificaciones.*;
import servicios.*;
import strategies.*;
import valueobjects.*;

/**
 * Clase principal que demuestra el funcionamiento del sistema PetDayCare.
 * Integra todos los patrones de diseño implementados.
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("SISTEMA DE GESTIÓN DE CENTRO DE MASCOTAS - PETDAYCARE");
        System.out.println("=".repeat(80));
        
        // ========================================================================
        // 1. PATRÓN ABSTRACT FACTORY - Creación de Servicios
        // ========================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("1. PATRÓN ABSTRACT FACTORY - Creación de Servicios");
        System.out.println("=".repeat(80));
        
        ICentroFactory centroGrande = new CentroGrandeFactory();
        ICentroFactory centroPequeno = new CentroPequenaFactory();
        
        System.out.println("\n--- Servicios del Centro Grande ---");
        IServicioBase guarderiaGrande = centroGrande.crearGuarderia();
        IServicioBase hospedajeGrande = centroGrande.crearHospedaje();
        System.out.println("Guardería: " + guarderiaGrande.obtenerNombre());
        System.out.println("Hospedaje: " + hospedajeGrande.obtenerNombre());
        
        System.out.println("\n--- Servicios del Centro Pequeño ---");
        IServicioBase paseoPequeno = centroPequeno.crearPaseo();
        System.out.println("Paseo: " + paseoPequeno.obtenerNombre());

        // ========================================================================
        // 2. PATRÓN DECORATOR - Añadir Extras a Servicios
        // ========================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("2. PATRÓN DECORATOR - Añadir Extras a Servicios");
        System.out.println("=".repeat(80));
        
        // Crear período y estrategia de pricing
        Periodo periodoDiario = new Periodo(
            LocalDateTime.of(2024, 1, 15, 10, 0),
            LocalDateTime.of(2024, 1, 16, 18, 0)
        );
        IPricingStrategy pricingStandard = new PricingStrategyStandard();
        List<String> opciones = Arrays.asList("juegos", "grooming");
        
        System.out.println("\n--- Servicio Base ---");
        System.out.println(guarderiaGrande.obtenerNombre());
        Money precioBase = guarderiaGrande.calcularPrecio(periodoDiario, opciones, pricingStandard);
        System.out.println("Precio base (diario): " + precioBase.toString());
        
        System.out.println("\n--- Servicio Decorado (Guardería + Cámara en Vivo + Veterinaria) ---");
        IServicioBase guarderiaDecorada = new AtencionVeterinariaDecorator(
            (ServicioAbstracto) new CamaraEnVivoDecorator((ServicioAbstracto) guarderiaGrande)
        );
        System.out.println(guarderiaDecorada.obtenerNombre());
        Money precioDecorado = guarderiaDecorada.calcularPrecio(periodoDiario, opciones, pricingStandard);
        System.out.println("Precio total decorado: " + precioDecorado.toString());
        
        System.out.println("\n--- Servicio Triple Decorado (Hospedaje + Todos los Extras) ---");
        Periodo periodoSemanal = new Periodo(
            LocalDateTime.of(2024, 1, 15, 10, 0),
            LocalDateTime.of(2024, 1, 22, 18, 0)
        );
        IServicioBase hospedajeCompleto = new ReporteTiempoRealDecorator(
            new AtencionVeterinariaDecorator(
                new CamaraEnVivoDecorator((ServicioAbstracto) hospedajeGrande)
            )
        );
        System.out.println(hospedajeCompleto.obtenerNombre());
        IPricingStrategy pricingPremium = new PricingStrategyPremium();
        Money precioCompleto = hospedajeCompleto.calcularPrecio(periodoSemanal, 
            Arrays.asList("suite"), pricingPremium);
        System.out.println("Precio semanal completo: " + precioCompleto.toString());

        // ========================================================================
        // 3. PATRÓN BUILDER - Construcción de Reservas
        // ========================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("3. PATRÓN BUILDER - Construcción de Reservas");
        System.out.println("=".repeat(80));
        
        // Crear entidades de dominio
        Usuario usuario1 = new Usuario("U001", "Juan Pérez", "juan@email.com", 
            "123456789", MetodoNotificacion.EMAIL);
        Mascota mascota1 = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 3, "");
        Centro centro1 = new Centro("C001", "Centro Premium", "Av. Principal 123", 50);
        
        System.out.println("\n--- Creando Reserva con Builder ---");
        Reserva reserva1 = new ReservaBuilder()
            .setUsuario(usuario1)
            .setMascota(mascota1)
            .setCentro(centro1)
            .setPeriodo(periodoDiario)
            .addService(guarderiaDecorada)
            .build();
        System.out.println(reserva1);

        // ========================================================================
        // 4. PATRÓN STATE - Gestión de Estados de Reservas
        // ========================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("4. PATRÓN STATE - Gestión de Estados de Reservas");
        System.out.println("=".repeat(80));
        
        // Configurar servicios para pagos y notificaciones
        IPagoProcessor pagoProcessor = new PayPalAdapter("test-api-key", "test-api-secret");
        ICanalNotificacion canalEmail = new EmailCanal("smtp.petdaycare.com");
        ICanalNotificacion canalMensajeria = new MensajeriaCanal("api.petdaycare.com");
        INotifier notificador = new Notificador(canalEmail, canalMensajeria);
        
        // Crear política de cancelación
        PoliticaCancelacion politica = new PoliticaCancelacion(
            "POL001", 
            "Política estándar: 24 horas gratis, luego 15% de penalidad",
            24, 
            Porcentaje.desdePorcentaje(15)
        );
        centro1.definirPoliticaCancelacion(politica);
        
        System.out.println("\n--- Estado Inicial: Pendiente ---");
        System.out.println("Estado: " + reserva1.getEstado().obtenerNombreEstado());
        
        System.out.println("\n--- Confirmando Reserva (Pendiente -> Confirmada) ---");
        reserva1.confirmar(pagoProcessor, notificador, politica);
        System.out.println("Estado: " + reserva1.getEstado().obtenerNombreEstado());
        
        System.out.println("\n--- Creando Reserva 2 ---");
        Usuario usuario2 = new Usuario("U002", "María González", "maria@email.com", 
            "987654321", MetodoNotificacion.MENSAJERIA);
        Mascota mascota2 = new Mascota("M002", "Luna", "Gato", "Persa", 
            "Mediano", 2, "");
        Reserva reserva2 = new ReservaBuilder()
            .setUsuario(usuario2)
            .setMascota(mascota2)
            .setCentro(centro1)
            .setPeriodo(periodoDiario)
            .addService(paseoPequeno)
            .build();
        System.out.println(reserva2);
        
        System.out.println("\n--- Cancelando Reserva 2 (Pendiente -> Cancelada) ---");
        reserva2.cancelar("Cliente cambió de planes", politica, pagoProcessor, notificador);
        System.out.println("Estado: " + reserva2.getEstado().obtenerNombreEstado());
        
        System.out.println("\n--- Creando Reserva 3 y cancelándola después de confirmar ---");
        Usuario usuario3 = new Usuario("U003", "Carlos Rodríguez", "carlos@email.com", 
            "555555555", MetodoNotificacion.EMAIL);
        Mascota mascota3 = new Mascota("M003", "Rocky", "Perro", "Bulldog", 
            "Mediano", 5, "");
        Reserva reserva3 = new ReservaBuilder()
            .setUsuario(usuario3)
            .setMascota(mascota3)
            .setCentro(centro1)
            .setPeriodo(periodoSemanal)
            .addService(hospedajeCompleto)
            .build();
        System.out.println(reserva3);
        reserva3.confirmar(pagoProcessor, notificador, politica);
        reserva3.cancelar("Emergencia familiar", politica, pagoProcessor, notificador);
        System.out.println("Estado final: " + reserva3.getEstado().obtenerNombreEstado());

        // ========================================================================
        // 5. PATRÓN CHAIN OF RESPONSIBILITY - Manejo de Incidentes
        // ========================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("5. PATRÓN CHAIN OF RESPONSIBILITY - Manejo de Incidentes");
        System.out.println("=".repeat(80));

        IHandlerIncidente soporteCentro = new SoporteCentroHandler();
        IHandlerIncidente soportePlataforma = new SoportePlataformaHandler();
        soporteCentro.setNext(soportePlataforma);
        
        System.out.println("\n--- Incidente de Baja Gravedad ---");
        Incidente incidente1 = new Incidente("I001", "Mascota mojó el piso", "baja", reserva1.getId());
        incidente1.reportar();
        String resultado1 = soporteCentro.manejar(incidente1);
        System.out.println("Resultado: " + resultado1);
        
        System.out.println("\n--- Incidente de Media Gravedad ---");
        Incidente incidente2 = new Incidente("I002", "Mascota se escapó del área de juegos", 
            "media", reserva2.getId());
        incidente2.reportar();
        String resultado2 = soporteCentro.manejar(incidente2);
        System.out.println("Resultado: " + resultado2);
        
        System.out.println("\n--- Incidente de Alta Gravedad (Escalado) ---");
        Incidente incidente3 = new Incidente("I003", 
            "Mascota requiere atención veterinaria urgente", "alta", reserva3.getId());
        incidente3.reportar();
        String resultado3 = soporteCentro.manejar(incidente3);
        System.out.println("Resultado: " + resultado3);
   
        // ========================================================================
        // 6. FLUJO COMPLETO DEL SISTEMA
        // ========================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("6. FLUJO COMPLETO DEL SISTEMA");
        System.out.println("=".repeat(80));
        
        System.out.println("\n--- Paso 1: Cliente solicita servicio en centro grande ---");
        ICentroFactory factory = new CentroGrandeFactory();
        IServicioBase entrenamientoBase = factory.crearEntrenamiento();
        System.out.println("Servicio creado: " + entrenamientoBase.obtenerNombre());
        
        System.out.println("\n--- Paso 2: Se agregan servicios adicionales (Decoradores) ---");
        IServicioBase entrenamientoPremium = new AtencionVeterinariaDecorator(
            new ReporteTiempoRealDecorator((ServicioAbstracto) entrenamientoBase)
        );
        System.out.println("Servicio decorado: " + entrenamientoPremium.obtenerNombre());
        
        System.out.println("\n--- Paso 3: Se calcula el precio ---");
        Periodo periodoMensual = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 2, 1, 10, 0)
        );
        Money precioFinal = entrenamientoPremium.calcularPrecio(periodoMensual, 
            Arrays.asList("personalizado", "avanzado"), pricingPremium);
        System.out.println("Precio final mensual: " + precioFinal.toString());
        
        System.out.println("\n--- Paso 4: Se crea la reserva (Estado: Pendiente) ---");
        Usuario usuario4 = new Usuario("U004", "Ana Martínez", "ana@email.com", 
            "111222333", MetodoNotificacion.EMAIL);
        Mascota mascota4 = new Mascota("M004", "Buddy", "Perro", "Golden Retriever", 
            "Grande", 2, "");
        Reserva reservaFinal = new ReservaBuilder()
            .setUsuario(usuario4)
            .setMascota(mascota4)
            .setCentro(centro1)
            .setPeriodo(periodoMensual)
            .addService(entrenamientoPremium)
            .build();
        System.out.println(reservaFinal);
        System.out.println("Estado: " + reservaFinal.getEstado().obtenerNombreEstado());
        
        System.out.println("\n--- Paso 5: Cliente confirma la reserva (Estado: Confirmada) ---");
        reservaFinal.confirmar(pagoProcessor, notificador, politica);
        System.out.println("Estado: " + reservaFinal.getEstado().obtenerNombreEstado());
        
        System.out.println("\n--- Paso 6: Ocurre un incidente durante el servicio ---");
        Incidente incidenteFinal = new Incidente("I004", 
            "Mascota mostró comportamiento agresivo durante entrenamiento", 
            "media", reservaFinal.getId());
        incidenteFinal.reportar();
        String resultadoFinal = soporteCentro.manejar(incidenteFinal);
        System.out.println("Resultado: " + resultadoFinal);
        
        // ========================================================================
        // RESUMEN
        // ========================================================================
        System.out.println("\n" + "=".repeat(80));
        System.out.println("RESUMEN DEL SISTEMA");
        System.out.println("=".repeat(80));
        System.out.println("✓ Abstract Factory: Creación flexible de servicios según tipo de centro");
        System.out.println("✓ Decorator: Extensión dinámica de funcionalidades de servicios");
        System.out.println("✓ Builder: Construcción paso a paso de reservas complejas");
        System.out.println("✓ State: Gestión de estados de reservas con transiciones controladas");
        System.out.println("✓ Chain of Responsibility: Manejo escalado de incidentes");
        System.out.println("✓ Adapter: Integración con sistemas de pago externos (PayPal)");
        System.out.println("✓ Strategy: Estrategias flexibles de pricing y compatibilidad");
        System.out.println("=".repeat(80));
    }
}
