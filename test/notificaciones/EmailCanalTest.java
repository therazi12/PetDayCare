import notificaciones.EmailCanal;
import dominio.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class EmailCanalTest {

	@Test
	void testEnviarMensaje_Normal() {
		EmailCanal canal = new EmailCanal("smtp.test.com");
		Usuario usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getEmail()).thenReturn("test@mail.com");
		assertDoesNotThrow(() -> canal.enviarMensaje("Hola", usuario));
	}

	@Test
	void testEnviarMensaje_MensajeNull() {
		EmailCanal canal = new EmailCanal("smtp.test.com");
		Usuario usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getEmail()).thenReturn("test@mail.com");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> canal.enviarMensaje(null, usuario));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	@Test
	void testEnviarMensaje_UsuarioNull() {
		EmailCanal canal = new EmailCanal("smtp.test.com");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> canal.enviarMensaje("Hola", null));
		assertTrue(ex.getMessage().toLowerCase().contains("destinatario"));
	}

	@Test
	void testEnviarMensaje_UsuarioSinEmail() {
		EmailCanal canal = new EmailCanal("smtp.test.com");
		Usuario usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getEmail()).thenReturn("");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> canal.enviarMensaje("Hola", usuario));
		assertTrue(ex.getMessage().toLowerCase().contains("email"));
	}
}
