package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SpielDatenTest {

    @ParameterizedTest(name = "{0} x {1}")
    @CsvSource({"2,2", "4,4", "6,4", "8,8", "1,2", "12,4"})
    @DisplayName("Jedes Symbol kommt genau zweimal vor - sonst waere das Spiel nicht loesbar")
    void jedesSymbolGenauZweimal(int breite, int hoehe) {
        Einstellungen einstellungen = new Einstellungen(breite, hoehe, 60);
        SpielDaten daten = new SpielDaten(einstellungen, new Random(1234));

        Map<Character, Integer> haeufigkeit = new HashMap<>();
        for (int x = 0; x < breite; x++) {
            for (int y = 0; y < hoehe; y++) {
                haeufigkeit.merge(daten.gibFeldWert(x, y), 1, Integer::sum);
            }
        }

        assertEquals(einstellungen.paare(), haeufigkeit.size(), "Anzahl verschiedener Symbole");
        haeufigkeit.forEach((symbol, anzahl) ->
                assertEquals(2, anzahl, "Symbol " + symbol + " kommt " + anzahl + "-mal vor"));
    }

    @Test
    @DisplayName("Gleicher Startwert erzeugt das gleiche Feld - Voraussetzung fuer reproduzierbare Tests")
    void istMitFestemStartwertReproduzierbar() {
        Einstellungen einstellungen = new Einstellungen(6, 4, 60);
        SpielDaten ersteRunde = new SpielDaten(einstellungen, new Random(42));
        SpielDaten zweiteRunde = new SpielDaten(einstellungen, new Random(42));

        for (int x = 0; x < einstellungen.breite(); x++) {
            for (int y = 0; y < einstellungen.hoehe(); y++) {
                assertEquals(ersteRunde.gibFeldWert(x, y), zweiteRunde.gibFeldWert(x, y));
            }
        }
    }

    @Test
    void mischtTatsaechlich() {
        Einstellungen einstellungen = new Einstellungen(8, 8, 60);
        SpielDaten a = new SpielDaten(einstellungen, new Random(1));
        SpielDaten b = new SpielDaten(einstellungen, new Random(2));

        boolean irgendwoUnterschiedlich = false;
        for (int x = 0; x < einstellungen.breite() && !irgendwoUnterschiedlich; x++) {
            for (int y = 0; y < einstellungen.hoehe(); y++) {
                if (a.gibFeldWert(x, y) != b.gibFeldWert(x, y)) {
                    irgendwoUnterschiedlich = true;
                    break;
                }
            }
        }
        assertTrue(irgendwoUnterschiedlich, "Zwei verschiedene Startwerte ergaben dasselbe Feld");
    }

    @Test
    void kenntSeineGroesse() {
        SpielDaten daten = new SpielDaten(new Einstellungen(6, 4, 60), new Random());
        assertEquals(6, daten.breite());
        assertEquals(4, daten.hoehe());
    }
}
