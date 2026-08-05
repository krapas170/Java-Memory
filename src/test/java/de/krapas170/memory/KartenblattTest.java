package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KartenblattTest {

    @Test
    void alleSymboleSindVerschieden() {
        Set<Character> gesehen = new HashSet<>();
        for (int i = 0; i < Kartenblatt.anzahlSymbole(); i++) {
            assertTrue(gesehen.add(Kartenblatt.symbolFuer(i)),
                    "Symbol " + Kartenblatt.symbolFuer(i) + " kommt doppelt vor");
        }
        assertEquals(Kartenblatt.anzahlSymbole(), gesehen.size());
    }

    @Test
    @DisplayName("Verwechselbare Zeichen sind ausgelassen")
    void enthaeltKeineVerwechselbarenZeichen() {
        Set<Character> symbole = new HashSet<>();
        for (int i = 0; i < Kartenblatt.anzahlSymbole(); i++) {
            symbole.add(Kartenblatt.symbolFuer(i));
        }
        assertTrue(symbole.contains('0') && !symbole.contains('O'), "0 und O sind zu aehnlich");
        assertTrue(symbole.contains('1') && !symbole.contains('I'), "1 und I sind zu aehnlich");
    }

    @Test
    void feldgrenzeFolgtAusDemSymbolvorrat() {
        assertEquals(Kartenblatt.anzahlSymbole() * 2, Kartenblatt.maxFelder());
        assertEquals(Kartenblatt.maxFelder(), Einstellungen.MAX_FELDER);
    }

    @Test
    void lehntUngueltigeNummernAb() {
        assertThrows(IllegalArgumentException.class, () -> Kartenblatt.symbolFuer(-1));
        assertThrows(IllegalArgumentException.class, () -> Kartenblatt.symbolFuer(Kartenblatt.anzahlSymbole()));
    }
}
