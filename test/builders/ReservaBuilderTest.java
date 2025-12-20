package builders;

import dominio.Centro;
import dominio.Mascota;
import dominio.Usuario;
import enums.MetodoNotificacion;
import estados.Reserva;
import factories.CentroGrandeFactory;
import interfaces.IServicioBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import valueobjects.Periodo;

/**
 * Pruebas unitarias para ReservaBuilder.
 */
@DisplayName("Pruebas para ReservaBuilder")
class ReservaBuilderTest {

    private Usuario usuario;
    private Mascota mascota;
    private Centro centro;
    private Periodo periodo;
    private IServicioBase servicio;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        usuario = new Usuario("U001", "Juan Pérez", "juan@email.com", 
            "123456789", MetodoNotificacion.EMAIL);
        mascota = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 3, "");
        centro = new Centro("C001", "Centro Test", "Dirección Test", 50);
        periodo = new Periodo(
            LocalDateTime.of(2024, 1, 1, 10, 0),
            LocalDateTime.of(2024, 1, 1, 18, 0)
        );
        
        CentroGrandeFactory factory = new CentroGrandeFactory();
        servicio = factory.crearGuarderia();
    }

    @Test
    @DisplayName("Construir Reserva con todos los datos")
    void construirReservaCompleta() {
        Reserva reserva = new ReservaBuilder()
            .setUsuario(usuario)
            .setMascota(mascota)
            .setCentro(centro)
            .setPeriodo(periodo)
            .addService(servicio)
            .build();
        
        assertNotNull(reserva);
        assertEquals(usuario, reserva.getUsuario());
        assertEquals(mascota, reserva.getMascota());
        assertEquals(centro, reserva.getCentro());
    }

    @Test
    @DisplayName("Construir Reserva con múltiples servicios")
    void construirReservaConMultiplesServicios() {
        CentroGrandeFactory factory = new CentroGrandeFactory();
        IServicioBase servicio1 = factory.crearGuarderia();
        IServicioBase servicio2 = factory.crearPaseo();
        
        Reserva reserva = new ReservaBuilder()
            .setUsuario(usuario)
            .setMascota(mascota)
            .setCentro(centro)
            .setPeriodo(periodo)
            .addService(servicio1)
            .addService(servicio2)
            .build();
        
        assertEquals(2, reserva.getLineas().size());
    }

    @Test
    @DisplayName("Lanzar excepción al construir sin usuario")
    void construirSinUsuario() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservaBuilder()
                .setMascota(mascota)
                .setCentro(centro)
                .setPeriodo(periodo)
                .addService(servicio)
                .build();
        });
    }

    @Test
    @DisplayName("Lanzar excepción al construir sin servicios")
    void construirSinServicios() {
        assertThrows(IllegalStateException.class, () -> {
            new ReservaBuilder()
                .setUsuario(usuario)
                .setMascota(mascota)
                .setCentro(centro)
                .setPeriodo(periodo)
                .build();
        });
    }

    @Test
    @DisplayName("Lanzar excepción al agregar servicio null")
    void agregarServicioNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ReservaBuilder().addService(null);
        });
    }

    @Test
    @DisplayName("Verificar interfaz fluida del builder")
    void interfazFluida() {
        ReservaBuilder builder = new ReservaBuilder()
            .setUsuario(usuario)
            .setMascota(mascota)
            .setCentro(centro)
            .setPeriodo(periodo)
            .addService(servicio);
        
        assertNotNull(builder);
        Reserva reserva = builder.build();
        assertNotNull(reserva);
    }
}

