package dominio;

import enums.MetodoNotificacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Usuario.
 */
@DisplayName("Pruebas para Usuario")
class UsuarioTest {

    @Test
    @DisplayName("Crear Usuario con datos válidos")
    void crearUsuarioValido() {
        Usuario usuario = new Usuario("U001", "Juan Pérez", "juan@email.com", 
            "123456789", MetodoNotificacion.EMAIL);
        
        assertEquals("U001", usuario.getId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@email.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefono());
        assertEquals(MetodoNotificacion.EMAIL, usuario.getMedioPreferidoNotificacion());
    }

    @Test
    @DisplayName("Lanzar excepción al crear Usuario con ID null")
    void crearUsuarioConIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario(null, "Juan", "juan@email.com", "123", MetodoNotificacion.EMAIL);
        });
    }

    @Test
    @DisplayName("Lanzar excepción al crear Usuario con email inválido")
    void crearUsuarioConEmailInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("U001", "Juan", "emailinvalido", "123", MetodoNotificacion.EMAIL);
        });
    }

    @Test
    @DisplayName("Lanzar excepción al crear Usuario con método de notificación null")
    void crearUsuarioConMetodoNotificacionNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Usuario("U001", "Juan", "juan@email.com", "123", null);
        });
    }

    @Test
    @DisplayName("Registrar mascota")
    void registrarMascota() {
        Usuario usuario = new Usuario("U001", "Juan", "juan@email.com", 
            "123", MetodoNotificacion.EMAIL);
        Mascota mascota = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 3, "");
        
        assertDoesNotThrow(() -> {
            usuario.registrarMascota(mascota);
        });
    }

    @Test
    @DisplayName("Lanzar excepción al registrar mascota null")
    void registrarMascotaNull() {
        Usuario usuario = new Usuario("U001", "Juan", "juan@email.com", 
            "123", MetodoNotificacion.EMAIL);
        
        assertThrows(IllegalArgumentException.class, () -> {
            usuario.registrarMascota(null);
        });
    }

    @Test
    @DisplayName("Verificar igualdad con equals")
    void testEquals() {
        Usuario usuario1 = new Usuario("U001", "Juan", "juan@email.com", 
            "123", MetodoNotificacion.EMAIL);
        Usuario usuario2 = new Usuario("U001", "Juan", "juan@email.com", 
            "123", MetodoNotificacion.EMAIL);
        
        assertEquals(usuario1, usuario2);
    }

    @Test
    @DisplayName("Verificar hashCode")
    void testHashCode() {
        Usuario usuario1 = new Usuario("U001", "Juan", "juan@email.com", 
            "123", MetodoNotificacion.EMAIL);
        Usuario usuario2 = new Usuario("U001", "Juan", "juan@email.com", 
            "123", MetodoNotificacion.EMAIL);
        
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}

