package estados;

import dominio.Centro;
import dominio.LineaReserva;
import dominio.Mascota;
import dominio.Pago;
import dominio.PoliticaCancelacion;
import dominio.Usuario;
import incidentes.IHandlerIncidente;
import incidentes.Incidente;
import interfaces.ICanalNotificacion;
import interfaces.INotifier;
import interfaces.IPagoProcessor;
import interfaces.IPricingStrategy;
import interfaces.IServicioBase;
import notificaciones.Notificador;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import valueobjects.Money;
import valueobjects.Periodo;

/**
 * Clase que representa una reserva en el sistema PetDayCare.
 * Refactorizada para usar entidades de dominio y soportar múltiples servicios.
 */
public class Reserva {
    private String id;
    private Usuario usuario;
    private Mascota mascota;
    private Centro centro;
    private Periodo periodo;
    private LocalDateTime fechaCreacion;
    private IEstadoReserva estado;
    private Money total;
    private List<LineaReserva> lineas;

    /**
     * Constructor principal para crear una reserva completa.
     */
    public Reserva(Usuario usuario, Mascota mascota, Centro centro, Periodo periodo, List<IServicioBase> servicios) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser null");
        }
        if (centro == null) {
            throw new IllegalArgumentException("El centro no puede ser null");
        }
        if (periodo == null) {
            throw new IllegalArgumentException("El período no puede ser null");
        }
        if (servicios == null || servicios.isEmpty()) {
            throw new IllegalArgumentException("La reserva debe tener al menos un servicio");
        }
        
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.usuario = usuario;
        this.mascota = mascota;
        this.centro = centro;
        this.periodo = periodo;
        this.fechaCreacion = LocalDateTime.now();
        this.estado = new EstadoReservaPendiente();
        this.lineas = new ArrayList<>();
        
        // Crear líneas de reserva para cada servicio
        for (IServicioBase servicio : servicios) {
            agregarLinea(servicio);
        }
        
        // Calcular total inicial (se puede recalcular después con una estrategia)
        recalcularTotal(null);
    }

    /**
     * Constructor de compatibilidad para código legacy.
     * @deprecated Usar el constructor con entidades de dominio o ReservaBuilder
     */
    @Deprecated
    public Reserva(String id, String clienteNombre, String mascotaNombre, servicios.ServicioAbstracto servicio) {
        // Este constructor se mantiene para compatibilidad pero debería migrarse
        this.id = id != null ? id : UUID.randomUUID().toString().substring(0, 8);
        // Crear objetos temporales - en producción estos deberían venir del repositorio
        this.usuario = new Usuario("temp-" + id, clienteNombre, "temp@email.com", "", enums.MetodoNotificacion.EMAIL);
        this.mascota = new Mascota("temp-" + id, mascotaNombre, "Perro", "Mestizo", "Mediano", 3, "");
        this.centro = new Centro("temp-" + id, "Centro Temporal", "Dirección Temporal", 10);
        this.periodo = new Periodo(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        this.fechaCreacion = LocalDateTime.now();
        this.estado = new EstadoReservaPendiente();
        this.lineas = new ArrayList<>();
        
        if (servicio != null) {
            agregarLinea(servicio);
        }
        
        recalcularTotal(null);
    }

    /**
     * Agrega una línea de servicio a la reserva.
     */
    public LineaReserva agregarLinea(IServicioBase servicio) {
        if (servicio == null) {
            throw new IllegalArgumentException("El servicio no puede ser null");
        }
        
        // Calcular precio usando el precio base del servicio (se puede mejorar con estrategia)
        Money precio = servicio instanceof servicios.ServicioAbstracto 
            ? ((servicios.ServicioAbstracto) servicio).getPrecioBase()
            : Money.usd(0);
        
        String lineaId = UUID.randomUUID().toString().substring(0, 8);
        LineaReserva linea = new LineaReserva(lineaId, servicio, precio);
        this.lineas.add(linea);
        
        return linea;
    }

    /**
     * Recalcula el total de la reserva usando una estrategia de pricing.
     */
    public void recalcularTotal(IPricingStrategy pricing) {
        Money nuevoTotal = Money.usd(0);
        
        for (LineaReserva linea : lineas) {
            if (pricing != null) {
                // Recalcular precio usando la estrategia
                Money precioRecalculado = linea.getServicio().calcularPrecio(periodo, null, pricing);
                linea.establecerPrecio(precioRecalculado);
                nuevoTotal = nuevoTotal.sumar(precioRecalculado);
            } else {
                // Usar precio actual de la línea
                nuevoTotal = nuevoTotal.sumar(linea.getPrecio());
            }
        }
        
        this.total = nuevoTotal;
    }

    /**
     * Confirma la reserva procesando el pago y enviando notificaciones.
     */
    public void confirmar(IPagoProcessor pagos, INotifier notificador, PoliticaCancelacion politica) {
        if (pagos == null) {
            throw new IllegalArgumentException("El procesador de pagos no puede ser null");
        }
        if (notificador == null) {
            throw new IllegalArgumentException("El notificador no puede ser null");
        }
        
        // El estado maneja la confirmación
        estado.manejarConfirmacion(this);
        
        // Crear y procesar pago
        Pago pago = new Pago(UUID.randomUUID().toString().substring(0, 8), this.id, this.total);
        pago.autorizar(pagos);
        pago.capturar(pagos);
        
        // Enviar notificación según preferencia del usuario
        String mensajeConfirmacion = String.format(
            "Su reserva #%s ha sido confirmada exitosamente. Total: %s. Período: %s",
            id, total.toString(), periodo.toString()
        );
        
        if (notificador instanceof Notificador) {
            ((Notificador) notificador).enviarSegunPreferencia(usuario, mensajeConfirmacion);
        } else {
            // Fallback: usar método genérico si no es Notificador
            ICanalNotificacion canal = obtenerCanalPorPreferencia(usuario);
            notificador.enviar(usuario, canal, mensajeConfirmacion);
        }
        
        System.out.println("  [Reserva] Reserva #" + id + " confirmada. Total: " + total.toString());
    }
    
    /**
     * Método auxiliar para obtener el canal según la preferencia del usuario.
     */
    private ICanalNotificacion obtenerCanalPorPreferencia(Usuario usuario) {
        // Por defecto usar email si no se puede determinar
        return new notificaciones.EmailCanal("smtp.petdaycare.com");
    }

    /**
     * Cancela la reserva aplicando la política de cancelación y procesando reembolsos.
     */
    public void cancelar(String motivo, PoliticaCancelacion politica, IPagoProcessor pagos, INotifier notificador) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de cancelación no puede ser null o vacío");
        }
        if (politica == null) {
            throw new IllegalArgumentException("La política de cancelación no puede ser null");
        }
        if (pagos == null) {
            throw new IllegalArgumentException("El procesador de pagos no puede ser null");
        }
        if (notificador == null) {
            throw new IllegalArgumentException("El notificador no puede ser null");
        }
        
        // Calcular penalidad
        Money penalidad = politica.calcularPenalidad(this, LocalDateTime.now());
        Money montoReembolso = total.restar(penalidad);
        
        // El estado maneja la cancelación
        estado.manejarCancelacion(this, motivo);
        
        // Procesar reembolso si hay monto a reembolsar
        if (montoReembolso.getMonto().compareTo(java.math.BigDecimal.ZERO) > 0) {
            // Buscar el pago asociado (en producción esto vendría de un repositorio)
            // Por ahora simulamos el reembolso
            String referenciaPago = "pago-" + id;
            try {
                pagos.reembolsar(referenciaPago, montoReembolso);
                System.out.println("  [Reserva] Reembolso procesado: " + montoReembolso.toString());
            } catch (Exception e) {
                System.out.println("  [Reserva] Error al procesar reembolso: " + e.getMessage());
            }
        } else {
            System.out.println("  [Reserva] No hay monto a reembolsar (penalidad completa)");
        }
        
        // Enviar notificación según preferencia del usuario
        String mensajeCancelacion = String.format(
            "Su reserva #%s ha sido cancelada. Motivo: %s. Penalidad aplicada: %s. Reembolso: %s",
            id, motivo, penalidad.toString(), montoReembolso.toString()
        );
        
        if (notificador instanceof Notificador) {
            ((Notificador) notificador).enviarSegunPreferencia(usuario, mensajeCancelacion);
        } else {
            // Fallback: usar método genérico si no es Notificador
            ICanalNotificacion canal = obtenerCanalPorPreferencia(usuario);
            notificador.enviar(usuario, canal, mensajeCancelacion);
        }
        
        System.out.println("  [Reserva] Reserva #" + id + " cancelada. Motivo: " + motivo);
        System.out.println("  [Reserva] Penalidad: " + penalidad.toString() + ", Reembolso: " + montoReembolso.toString());
    }

    /**
     * Reporta un incidente relacionado con esta reserva usando el handler de incidentes.
     */
    public String reportarIncidente(String motivo, String gravedad, IHandlerIncidente handler) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo del incidente no puede ser null o vacío");
        }
        if (gravedad == null || gravedad.trim().isEmpty()) {
            throw new IllegalArgumentException("La gravedad del incidente no puede ser null o vacía");
        }
        if (handler == null) {
            throw new IllegalArgumentException("El handler de incidentes no puede ser null");
        }
        
        Incidente incidente = new Incidente(UUID.randomUUID().toString().substring(0, 8), motivo, gravedad, this.id);
        String resultado = handler.manejar(incidente);
        
        System.out.println("  [Reserva] Incidente reportado para reserva #" + id);
        return resultado;
    }

    public void setEstado(IEstadoReserva nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("  [Estado] Reserva " + id + " cambió a: " + nuevoEstado.obtenerNombreEstado());
    }

    // Métodos de compatibilidad legacy
    @Deprecated
    public void confirmar() {
        // Para compatibilidad con código legacy - usar confirmar(pagos, notificador, politica) en su lugar
        estado.manejarConfirmacion(this);
    }

    @Deprecated
    public void cancelar(String motivo) {
        // Para compatibilidad con código legacy - usar cancelar(motivo, politica, pagos, notificador) en su lugar
        estado.manejarCancelacion(this, motivo);
    }

    // Getters
    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public Centro getCentro() {
        return centro;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public IEstadoReserva getEstado() {
        return estado;
    }

    public Money getTotal() {
        return total;
    }

    public List<LineaReserva> getLineas() {
        return new ArrayList<>(lineas); // Retornar copia para inmutabilidad
    }

    // Getters de compatibilidad legacy
    @Deprecated
    public String getClienteNombre() {
        return usuario != null ? usuario.getNombre() : "";
    }

    @Deprecated
    public String getMascotaNombre() {
        return mascota != null ? mascota.getNombre() : "";
    }

    @Deprecated
    public LocalDateTime getFechaReserva() {
        return fechaCreacion;
    }

    @Deprecated
    public IEstadoReserva getEstadoActual() {
        return estado;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("Reserva #%s - Usuario: %s, Mascota: %s, Centro: %s, Estado: %s, Total: %s, Fecha: %s",
                id, usuario.getNombre(), mascota.getNombre(), centro.getNombre(), 
                estado.obtenerNombreEstado(), total.toString(), fechaCreacion.format(formatter));
    }
}
