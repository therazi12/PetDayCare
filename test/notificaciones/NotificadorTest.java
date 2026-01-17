import notificaciones.Notificador;
import interfaces.ICanalNotificacion;
import dominio.Usuario;
import enums.MetodoNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class NotificadorTest {

	private ICanalNotificacion canalEmail;
	private ICanalNotificacion canalMensajeria;
	private Notificador notificador;
	private Usuario usuario;

	@BeforeEach
	void setUp() {
		canalEmail = Mockito.mock(ICanalNotificacion.class);
		canalMensajeria = Mockito.mock(ICanalNotificacion.class);
		notificador = new Notificador(canalEmail, canalMensajeria);
		usuario = Mockito.mock(Usuario.class);
		Mockito.when(usuario.getNombre()).thenReturn("Juan");
		Mockito.when(usuario.getEmail()).thenReturn("juan@mail.com");
		Mockito.when(usuario.getTelefono()).thenReturn("1234567890");
		Mockito.when(usuario.getMedioPreferidoNotificacion()).thenReturn(MetodoNotificacion.EMAIL);
	}

	// TC01: enviar normal
	@Test
	void testEnviar_Normal() {
		Mockito.doNothing().when(canalEmail).enviarMensaje(Mockito.anyString(), Mockito.eq(usuario));
		assertDoesNotThrow(() -> notificador.enviar(usuario, canalEmail, "Hola"));
	}

	// TC02: enviar destinatario null
	@Test
	void testEnviar_DestinatarioNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviar(null, canalEmail, "Hola"));
		assertTrue(ex.getMessage().toLowerCase().contains("destinatario"));
	}

	// TC03: enviar canal null
	@Test
	void testEnviar_CanalNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviar(usuario, null, "Hola"));
		assertTrue(ex.getMessage().toLowerCase().contains("canal"));
	}

	// TC04: enviar mensaje null
	@Test
	void testEnviar_MensajeNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviar(usuario, canalEmail, null));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	// TC05: enviar mensaje vacío
	@Test
	void testEnviar_MensajeVacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviar(usuario, canalEmail, "   "));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	// TC06: enviarSegunPreferencia normal
	@Test
	void testEnviarSegunPreferencia_Normal() {
		Mockito.when(usuario.getMedioPreferidoNotificacion()).thenReturn(MetodoNotificacion.EMAIL);
		Mockito.doNothing().when(canalEmail).enviarMensaje(Mockito.anyString(), Mockito.eq(usuario));
		assertDoesNotThrow(() -> notificador.enviarSegunPreferencia(usuario, "Mensaje preferido"));
	}

	// TC07: enviarSegunPreferencia preferencia no mapeada (fallback)
	@Test
	void testEnviarSegunPreferencia_FallbackEmail() {
		Mockito.when(usuario.getMedioPreferidoNotificacion()).thenReturn(null);
		Mockito.doNothing().when(canalEmail).enviarMensaje(Mockito.anyString(), Mockito.eq(usuario));
		assertDoesNotThrow(() -> notificador.enviarSegunPreferencia(usuario, "Mensaje fallback"));
	}

	// TC08: enviarSegunPreferencia destinatario null
	@Test
	void testEnviarSegunPreferencia_DestinatarioNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviarSegunPreferencia(null, "Mensaje"));
		assertTrue(ex.getMessage().toLowerCase().contains("destinatario"));
	}

	// TC09: enviarSegunPreferencia mensaje null
	@Test
	void testEnviarSegunPreferencia_MensajeNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviarSegunPreferencia(usuario, null));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	// TC10: enviarSegunPreferencia mensaje vacío
	@Test
	void testEnviarSegunPreferencia_MensajeVacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviarSegunPreferencia(usuario, "   "));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	// TC11: enviarATodosLosCanales normal
	@Test
	void testEnviarATodosLosCanales_Normal() {
		Mockito.doNothing().when(canalEmail).enviarMensaje(Mockito.anyString(), Mockito.eq(usuario));
		Mockito.doNothing().when(canalMensajeria).enviarMensaje(Mockito.anyString(), Mockito.eq(usuario));
		assertDoesNotThrow(() -> notificador.enviarATodosLosCanales(usuario, "Mensaje broadcast"));
	}

	// TC12: enviarATodosLosCanales destinatario null
	@Test
	void testEnviarATodosLosCanales_DestinatarioNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviarATodosLosCanales(null, "Mensaje"));
		assertTrue(ex.getMessage().toLowerCase().contains("destinatario"));
	}

	// TC13: enviarATodosLosCanales mensaje null
	@Test
	void testEnviarATodosLosCanales_MensajeNull() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviarATodosLosCanales(usuario, null));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}

	// TC14: enviarATodosLosCanales mensaje vacío
	@Test
	void testEnviarATodosLosCanales_MensajeVacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> notificador.enviarATodosLosCanales(usuario, "   "));
		assertTrue(ex.getMessage().toLowerCase().contains("mensaje"));
	}
}
