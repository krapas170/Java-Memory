package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.prefs.Preferences;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HighscoreTest {

    private static final Einstellungen VIER_MAL_VIER = new Einstellungen(4, 4, 180);
    private static final Einstellungen SECHS_MAL_VIER = new Einstellungen(6, 4, 180);

    private Preferences knoten;
    private Highscore highscore;

    @BeforeEach
    void bereiteVor() throws Exception {
        knoten = Preferences.userRoot().node("de/krapas170/memory/test");
        knoten.clear();
        highscore = new Highscore(knoten);
    }

    @AfterEach
    void raeumeAuf() throws Exception {
        // Der Elternknoten wird geleert, nicht der entfernte selbst: Nach
        // removeNode() ist der Knoten ungueltig, und persistiert werden muss
        // ohnehin die Aenderung im Elternknoten.
        Preferences eltern = knoten.parent();
        knoten.removeNode();
        eltern.flush();
    }

    @Test
    void ohneEintragGibtEsKeinenRekord() {
        assertEquals(0, highscore.wenigsteZuege(VIER_MAL_VIER));
        assertEquals(0, highscore.schnellsteZeit(VIER_MAL_VIER));
    }

    @Test
    void ersterEintragIstImmerEinRekord() {
        assertTrue(highscore.melde(VIER_MAL_VIER, 20, 95));
        assertEquals(20, highscore.wenigsteZuege(VIER_MAL_VIER));
        assertEquals(95, highscore.schnellsteZeit(VIER_MAL_VIER));
    }

    @Test
    void schlechteresErgebnisAendertNichts() {
        highscore.melde(VIER_MAL_VIER, 20, 95);

        assertFalse(highscore.melde(VIER_MAL_VIER, 25, 120));
        assertEquals(20, highscore.wenigsteZuege(VIER_MAL_VIER));
        assertEquals(95, highscore.schnellsteZeit(VIER_MAL_VIER));
    }

    @Test
    @DisplayName("Weniger Zuege und schnellere Zeit zaehlen einzeln")
    void verbessertJedenWertFuerSich() {
        highscore.melde(VIER_MAL_VIER, 20, 95);

        assertTrue(highscore.melde(VIER_MAL_VIER, 18, 200));
        assertEquals(18, highscore.wenigsteZuege(VIER_MAL_VIER));
        assertEquals(95, highscore.schnellsteZeit(VIER_MAL_VIER), "Die langsamere Zeit darf nicht gewinnen");
    }

    @Test
    void jedeFeldgroesseHatEigeneBestwerte() {
        highscore.melde(VIER_MAL_VIER, 20, 95);

        assertEquals(0, highscore.wenigsteZuege(SECHS_MAL_VIER));
        assertTrue(highscore.melde(SECHS_MAL_VIER, 40, 200));
        assertEquals(20, highscore.wenigsteZuege(VIER_MAL_VIER));
    }
}
