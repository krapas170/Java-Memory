package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EinstellungenTest {

    @Test
    void rechnetFelderUndPaare() {
        Einstellungen e = Einstellungen.ausMinuten(6, 4, 5);
        assertEquals(24, e.felder());
        assertEquals(12, e.paare());
        assertEquals(300, e.sekunden());
        assertEquals(5, e.minuten());
    }

    @Test
    @DisplayName("Ungerade Feldanzahl wird abgelehnt, sonst bleibt eine Karte ohne Partner")
    void lehntUngeradeFeldanzahlAb() {
        assertThrows(IllegalArgumentException.class, () -> new Einstellungen(3, 3, 60));
    }

    @Test
    void lehntZuVieleFelderAb() {
        assertThrows(IllegalArgumentException.class,
                () -> new Einstellungen(Einstellungen.MAX_FELDER, 2, 60));
    }

    @Test
    @DisplayName("Null und negative Werte fuehrten frueher zu Abstuerzen im Spielfeld")
    void lehntNullUndNegativeWerteAb() {
        assertThrows(IllegalArgumentException.class, () -> new Einstellungen(0, 4, 60));
        assertThrows(IllegalArgumentException.class, () -> new Einstellungen(4, 0, 60));
        assertThrows(IllegalArgumentException.class, () -> new Einstellungen(4, 4, 0));
        assertThrows(IllegalArgumentException.class, () -> new Einstellungen(-2, 4, 60));
        assertThrows(IllegalArgumentException.class, () -> Einstellungen.ausMinuten(4, 4, 0));
    }

    @Test
    void dieVorgabeIstGueltig() {
        Einstellungen standard = Einstellungen.standard();
        assertEquals(16, standard.felder());
        assertEquals(180, standard.sekunden());
    }
}
