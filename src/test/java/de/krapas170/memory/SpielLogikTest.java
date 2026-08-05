package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpielLogikTest {

    private static final Einstellungen EINSTELLUNGEN = new Einstellungen(4, 4, 120);

    private TestAnzeige anzeige;
    private SpielLogik logik;

    @BeforeEach
    void bereiteVor() {
        anzeige = new TestAnzeige();
        logik = new SpielLogik(EINSTELLUNGEN, anzeige, new Random(2024));
    }

    @Test
    void ersteKarteWirdNurAufgedeckt() {
        logik.klick(0, 0);

        assertEquals(1, anzeige.ereignisse.size());
        assertTrue(anzeige.ereignisse.get(0).startsWith("zeige 0/0="));
        assertEquals(0, logik.zuege(), "Ein einzelner Klick ist noch kein Zug");
    }

    @Test
    void gleichesPaarBleibtOffen() {
        int[] partner = suchePartnerVon(0, 0);

        logik.klick(0, 0);
        logik.klick(partner[0], partner[1]);

        assertTrue(anzeige.ereignisse.contains("paar 0/0 " + partner[0] + "/" + partner[1]));
        assertFalse(anzeige.hatOffeneVerzoegerung(), "Ein Paar darf nicht wieder zugedeckt werden");
        assertEquals(1, logik.gefundenePaare());
        assertEquals(1, logik.zuege());
    }

    @Test
    void falschesPaarWirdWiederVerdeckt() {
        int[] anderes = sucheAndersalsVon(0, 0);

        logik.klick(0, 0);
        logik.klick(anderes[0], anderes[1]);

        assertTrue(anzeige.ereignisse.contains("fehler 0/0 " + anderes[0] + "/" + anderes[1]));
        assertTrue(anzeige.hatOffeneVerzoegerung());
        assertEquals(0, logik.gefundenePaare());

        anzeige.fuehreVerzoegerungAus();
        assertTrue(anzeige.ereignisse.contains("verdecke 0/0"));
        assertTrue(anzeige.ereignisse.contains("verdecke " + anderes[0] + "/" + anderes[1]));
    }

    @Test
    @DisplayName("Der dritte Klick waehrend eines falschen Paares wird ignoriert")
    void dritterKlickWirdIgnoriert() {
        int[] anderes = sucheAndersalsVon(0, 0);
        logik.klick(0, 0);
        logik.klick(anderes[0], anderes[1]);

        int vorher = anzeige.ereignisse.size();
        logik.klick(3, 3);
        assertEquals(vorher, anzeige.ereignisse.size(), "Es haette nichts passieren duerfen");

        anzeige.fuehreVerzoegerungAus();
        logik.klick(3, 3);
        assertTrue(anzeige.ereignisse.stream().anyMatch(e -> e.startsWith("zeige 3/3=")),
                "Nach dem Zudecken muss der Klick wieder wirken");
    }

    @Test
    void zweiterKlickAufDieselbeKarteZaehltNicht() {
        logik.klick(1, 1);
        int vorher = anzeige.ereignisse.size();

        logik.klick(1, 1);

        assertEquals(vorher, anzeige.ereignisse.size());
        assertEquals(0, logik.zuege());
    }

    @Test
    void bereitsGefundeneKarteReagiertNicht() {
        int[] partner = suchePartnerVon(0, 0);
        logik.klick(0, 0);
        logik.klick(partner[0], partner[1]);

        int vorher = anzeige.ereignisse.size();
        logik.klick(0, 0);

        assertEquals(vorher, anzeige.ereignisse.size());
    }

    @Test
    @DisplayName("Sind alle Paare gefunden, meldet die Logik den Sieg genau einmal")
    void meldetDenSieg() {
        boolean[][] erledigt = new boolean[EINSTELLUNGEN.breite()][EINSTELLUNGEN.hoehe()];
        int zuege = 0;
        for (int x = 0; x < EINSTELLUNGEN.breite(); x++) {
            for (int y = 0; y < EINSTELLUNGEN.hoehe(); y++) {
                if (erledigt[x][y]) {
                    continue;
                }
                int[] partner = suchePartnerVon(x, y);
                logik.klick(x, y);
                logik.klick(partner[0], partner[1]);
                erledigt[x][y] = true;
                erledigt[partner[0]][partner[1]] = true;
                zuege++;
            }
        }

        assertTrue(logik.istGewonnen());
        assertTrue(anzeige.gewonnen);
        assertEquals(EINSTELLUNGEN.paare(), logik.gefundenePaare());
        assertEquals(zuege, logik.zuege());
        assertEquals(1, anzeige.ereignisse.stream().filter("gewonnen"::equals).count());
    }

    @Test
    void zaehltFehlversucheAlsZuege() {
        int[] anderes = sucheAndersalsVon(0, 0);
        logik.klick(0, 0);
        logik.klick(anderes[0], anderes[1]);
        anzeige.fuehreVerzoegerungAus();

        assertEquals(1, logik.zuege());
        assertEquals(1, anzeige.letzteZuege);
    }

    // ------------------------------------------------------------------

    private int[] suchePartnerVon(int x, int y) {
        return suche(x, y, true);
    }

    private int[] sucheAndersalsVon(int x, int y) {
        return suche(x, y, false);
    }

    private int[] suche(int x, int y, boolean gleich) {
        char gesucht = logik.wertVon(x, y);
        for (int px = 0; px < EINSTELLUNGEN.breite(); px++) {
            for (int py = 0; py < EINSTELLUNGEN.hoehe(); py++) {
                if (px == x && py == y) {
                    continue;
                }
                boolean passt = logik.wertVon(px, py) == gesucht;
                if (passt == gleich) {
                    return new int[]{px, py};
                }
            }
        }
        throw new AssertionError("Kein passendes Feld zu " + x + "/" + y + " gefunden");
    }
}
