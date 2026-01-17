package refactor_verification;

import dominio.Usuario;
import enums.MetodoNotificacion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Verificación: Data Class en Usuario.java
 */
@DisplayName("Verificación Refactorización: Data Class en Usuario")
class UsuarioRefactorTest {

    @Test
    @DisplayName("Verificar método recibirNotificacion")
    void probarComportamientoUsuario() {
        // ENTRADA: Crear Usuario
        Usuario usuario = new Usuario("U1", "Test User", "email@test.com", "123", MetodoNotificacion.EMAIL);
        
        // ACCIÓN: Llamar al nuevo método recibirNotificacion
        assertDoesNotThrow(() -> {
            usuario.recibirNotificacion("Mensaje de prueba");
        }, "El método recibirNotificacion debería ejecutarse sin lanzar excepciones");
        
        // SALIDA ESPERADA:
        // No hay retorno, pero la ejecución exitosa indica que la lógica fue incorporada
    }
}
