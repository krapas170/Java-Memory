package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VersionTest {

    @Test
    void hoehereVersionGewinnt() {
        assertTrue(Version.vergleiche("1.5", "1.4") > 0);
        assertTrue(Version.vergleiche("1.4", "1.5") < 0);
        assertTrue(Version.vergleiche("2.0", "1.99") > 0);
    }

    @Test
    @DisplayName("1.10 ist neuer als 1.9 - ein reiner Textvergleich sagt das Gegenteil")
    void vergleichtAbschnitteAlsZahlen() {
        assertTrue(Version.vergleiche("1.10", "1.9") > 0);
    }

    @Test
    void fehlendeAbschnitteZaehlenAlsNull() {
        assertEquals(0, Version.vergleiche("1.5", "1.5.0"));
        assertEquals(0, Version.vergleiche("1.5.0.0", "1.5"));
    }

    @Test
    void zusaetzeWerdenIgnoriert() {
        assertEquals(0, Version.vergleiche("1.5.0-SNAPSHOT", "1.5.0"));
    }

    @Test
    @DisplayName("Die eigene Version gilt nicht als veraltet")
    void keinUpdateBeiGleichstandOderNeueremLokalenStand() {
        assertFalse(UpdatePruefung.istUpdateVerfuegbar("1.4", "1.4"));
        assertFalse(UpdatePruefung.istUpdateVerfuegbar("1.4", "1.5.0"));
        assertTrue(UpdatePruefung.istUpdateVerfuegbar("1.6", "1.5.0"));
    }

    @Test
    @DisplayName("Die eigene Version wird beim Bauen eingesetzt, nicht aus einer Datei daneben gelesen")
    void lokaleVersionKommtAusDerRessource() {
        String lokal = Version.lokal();
        assertTrue(lokal.matches("\\d+\\.\\d+.*"), "Unerwartete Version: " + lokal);
        assertTrue(Version.vergleiche(lokal, "0.0.0") > 0, "Die Version wurde nicht ersetzt: " + lokal);
    }
}
