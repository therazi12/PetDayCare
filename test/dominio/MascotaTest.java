import dominio.Mascota;
import valueobjects.PerfilMascotaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

class MascotaTest {

	private Mascota mascota;

	@BeforeEach
	void setUp() {
		mascota = new Mascota("1", "Firulais", "Perro", "Labrador", "Grande", 5, "Alergias");
	}

	@Test
	void testActualizarPerfil_DatosValidos() {
		// Arrange
		PerfilMascotaData datos = Mockito.mock(PerfilMascotaData.class);
		Mockito.when(datos.especie()).thenReturn("Gato");
		Mockito.when(datos.raza()).thenReturn("Siames");
		Mockito.when(datos.tamaño()).thenReturn("Pequeño");
		Mockito.when(datos.edad()).thenReturn(3);
		Mockito.when(datos.necesidadesEspeciales()).thenReturn("Ninguna");

		// Act
		mascota.actualizarPerfil(datos);

		// Assert
		assertEquals("Gato", mascota.getEspecie());
		assertEquals("Siames", mascota.getRaza());
		assertEquals("Pequeño", mascota.getTamaño());
		assertEquals(3, mascota.getEdad());
		assertEquals("Ninguna", mascota.getNecesidadesEspeciales());
		assertEquals("Firulais", mascota.getNombre(), "El nombre no debe cambiar");
	}

	@Test
	void testActualizarPerfil_DatosNull() {
		// Act & Assert
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
			mascota.actualizarPerfil(null);
		});
		assertTrue(ex.getMessage().contains("perfil"));
	}
}
