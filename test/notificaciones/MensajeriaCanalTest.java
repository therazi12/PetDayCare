import notificaciones.MensajeriaCanal;
import dominio.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class MensajeriaCanalTest {

	@Test
	void testEnviarMensaje_Normal() {
		MensajeriaCanal canal = new MensajeriaCanal("api.test.com");
		Usuario usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getTelefono()).thenReturn("1234567890");
		assertDoesNotThrow(() -> canal.enviarMensaje("Hola", usuario));
	}

	@Test
	void testEnviarMensaje_MensajeNull() {
		MensajeriaCanal canal = new MensajeriaCanal("api.test.com");
		Usuario usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getTelefono()).thenReturn("1234567890");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> canal.enviarMensaje(null, usuario));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	@Test
	void testEnviarMensaje_UsuarioNull() {
		MensajeriaCanal canal = new MensajeriaCanal("api.test.com");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> canal.enviarMensaje("Hola", null));
		assertTrue(ex.getMessage().toLowerCase().contains("destinatario"));
	}

	@Test
	void testEnviarMensaje_UsuarioSinTelefono() {
		MensajeriaCanal canal = new MensajeriaCanal("api.test.com");
		Usuario usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getTelefono()).thenReturn("");
		Exception ex = assertThrows(IllegalArgumentException.class, () -> canal.enviarMensaje("Hola", usuario));
		assertTrue(ex.getMessage().toLowerCase().contains("teléfono"));
	}
}
