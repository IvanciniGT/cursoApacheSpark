package com.curso;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PalabrasSimilaresTest {

    @Test
    void leerPalabrasValidasTest() throws URISyntaxException, IOException {
        // Given... Planteo el escenario de prueba
        // When.... Ejecuto lo que quiero probar
        List<String> palabrasValidas = PalabrasSimilares.leerPalabrasValidas();
        // Then.... Me aseguro que ha ido bien
        assertNotNull(palabrasValidas);
        assertTrue(palabrasValidas.size() > 640 * 1000);
        assertTrue(palabrasValidas.contains("manzana"));
        assertTrue(palabrasValidas.contains("albaricoque"));
        assertTrue(palabrasValidas.contains("ángela"));
    }
}