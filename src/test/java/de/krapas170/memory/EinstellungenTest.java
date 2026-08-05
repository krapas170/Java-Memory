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
    @DisplayName("Ein ueberlaufendes Produkt darf nicht als gueltiges Feld durchgehen")
    void lehntUeberlaufBeiDerFeldgroesseAb() {
        // 65536 * 65536 ist als int genau 0: gerade und kleiner als MAX_FELDER.
        assertThrows(IllegalArgumentException.class, () -> new Einstellungen(65536, 65536, 60));
        assertThrows(IllegalArgumentException.class,
                () -> new Einstellungen(Integer.MAX_VALUE, 2, 60));
    }

    @Test
    @DisplayName("Minuten mal 60 darf nicht ueberlaufen und aus viel Zeit wenig machen")
    void lehntUeberlaufBeiDerZeitAb() {
        // 71582789 Minuten waeren als int 44 Sekunden.
        assertThrows(IllegalArgumentException.class,
                () -> Einstellungen.ausMinuten(4, 4, 71582789));
        assertThrows(IllegalArgumentException.class,
                () -> Einstellungen.ausMinuten(4, 4, Integer.MAX_VALUE));
    }

    @Test
    void dieVorgabeIstGueltig() {
        Einstellungen standard = Einstellungen.standard();
        assertEquals(16, standard.felder());
        assertEquals(180, standard.sekunden());
    }
}
