package dominio;

import valueobjects.PerfilMascotaData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Mascota.
 */
@DisplayName("Pruebas para Mascota")
class MascotaTest {

    @Test
    @DisplayName("Crear Mascota con datos válidos")
    void crearMascotaValida() {
        Mascota mascota = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 3, "");
        
        assertEquals("M001", mascota.getId());
        assertEquals("Max", mascota.getNombre());
        assertEquals("Perro", mascota.getEspecie());
    }

    @Test
    @DisplayName("Lanzar excepción al crear Mascota con ID null")
    void crearMascotaConIdNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Mascota(null, "Max", "Perro", "Labrador", "Grande", 3, "");
        });
    }

    @Test
    @DisplayName("Obtener PerfilMascotaData")
    void obtenerPerfilMascotaData() {
        Mascota mascota = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 2, "Alergias");
        
        PerfilMascotaData perfil = mascota.obtenerPerfil();
        assertEquals("Perro", perfil.especie());
        assertEquals("Labrador", perfil.raza());
        assertEquals("Grande", perfil.tamaño());
        assertEquals(2, perfil.edad());
        assertEquals("Alergias", perfil.necesidadesEspeciales());
    }

    @Test
    @DisplayName("Actualizar PerfilMascotaData")
    void actualizarPerfilMascotaData() {
        Mascota mascota = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 2, "");
        
        PerfilMascotaData nuevoPerfil = new PerfilMascotaData("Gato", "Persa", 
            "Mediano", 1, "Ninguna");
        mascota.actualizarPerfil(nuevoPerfil);
        
        assertEquals("Gato", mascota.getEspecie());
        assertEquals("Persa", mascota.getRaza());
    }

    @Test
    @DisplayName("Verificar si es cachorro")
    void esCachorro() {
        Mascota cachorro = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 1, "");
        Mascota adulto = new Mascota("M002", "Luna", "Perro", "Labrador", 
            "Grande", 5, "");
        
        assertTrue(cachorro.obtenerPerfil().esCachorro());
        assertFalse(adulto.obtenerPerfil().esCachorro());
    }

    @Test
    @DisplayName("Verificar igualdad con equals")
    void testEquals() {
        Mascota mascota1 = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 3, "");
        Mascota mascota2 = new Mascota("M001", "Max", "Perro", "Labrador", 
            "Grande", 3, "");
        
        assertEquals(mascota1, mascota2);
    }
}

