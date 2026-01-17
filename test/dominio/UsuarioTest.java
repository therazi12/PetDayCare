import dominio.Usuario;
import dominio.Mascota;
import enums.MetodoNotificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

	private Usuario usuario;

	@BeforeEach
	void setUp() {
		usuario = new Usuario("U1", "Juan", "juan@mail.com", "1234567890", MetodoNotificacion.EMAIL);
	}

	// TC01: setNombre válido
	@Test
	void testSetNombre_Valido() {
		usuario.setNombre("Pedro");
		assertEquals("Pedro", usuario.getNombre());
	}

	// TC02: setNombre null
	@Test
	void testSetNombre_Null() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> usuario.setNombre(null));
		assertTrue(ex.getMessage().toLowerCase().contains("nombre"));
	}

	// TC03: setNombre vacío
	@Test
	void testSetNombre_Vacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> usuario.setNombre("   "));
		assertTrue(ex.getMessage().toLowerCase().contains("nombre"));
	}

	// TC04: setEmail válido
	@Test
	void testSetEmail_Valido() {
		usuario.setEmail("nuevo@mail.com");
		assertEquals("nuevo@mail.com", usuario.getEmail());
	}

	// TC05: setEmail null
	@Test
	void testSetEmail_Null() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> usuario.setEmail(null));
		assertTrue(ex.getMessage().toLowerCase().contains("email"));
	}

	// TC06: setEmail vacío
	@Test
	void testSetEmail_Vacio() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> usuario.setEmail("   "));
		assertTrue(ex.getMessage().toLowerCase().contains("email"));
	}

	// TC07: setEmail sin arroba
	@Test
	void testSetEmail_SinArroba() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> usuario.setEmail("correo.mail.com"));
		assertTrue(ex.getMessage().toLowerCase().contains("formato"));
	}

	// TC08: registrarMascota válida
	@Test
	void testRegistrarMascota_Valida() {
		Mascota mascota = Mockito.mock(Mascota.class);
		Mockito.when(mascota.getNombre()).thenReturn("Rocky");
		// No lanza excepción
		assertDoesNotThrow(() -> usuario.registrarMascota(mascota));
	}

	// TC09: registrarMascota null
	@Test
	void testRegistrarMascota_Null() {
		Exception ex = assertThrows(IllegalArgumentException.class, () -> usuario.registrarMascota(null));
		assertTrue(ex.getMessage().toLowerCase().contains("mascota"));
	}
}
