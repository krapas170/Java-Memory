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
        assertTrue(highscore.melde(VIER_MAL_VIER, 20, 95).gabEs());
        assertEquals(20, highscore.wenigsteZuege(VIER_MAL_VIER));
        assertEquals(95, highscore.schnellsteZeit(VIER_MAL_VIER));
    }

    @Test
    void schlechteresErgebnisAendertNichts() {
        highscore.melde(VIER_MAL_VIER, 20, 95);

        assertFalse(highscore.melde(VIER_MAL_VIER, 25, 120).gabEs());
        assertEquals(20, highscore.wenigsteZuege(VIER_MAL_VIER));
        assertEquals(95, highscore.schnellsteZeit(VIER_MAL_VIER));
    }

    @Test
    @DisplayName("Weniger Zuege und schnellere Zeit zaehlen einzeln")
    void verbessertJedenWertFuerSich() {
        highscore.melde(VIER_MAL_VIER, 20, 95);

        Highscore.Verbesserung v = highscore.melde(VIER_MAL_VIER, 18, 200);
        assertTrue(v.wenigereZuege());
        assertFalse(v.schnellereZeit(), "Die langsamere Zeit ist kein Bestwert");
        assertEquals(18, highscore.wenigsteZuege(VIER_MAL_VIER));
        assertEquals(95, highscore.schnellsteZeit(VIER_MAL_VIER), "Die langsamere Zeit darf nicht gewinnen");
    }

    @Test
    @DisplayName("Die beiden Bestwerte koennen aus verschiedenen Runden stammen")
    void bestwerteStammenAusVerschiedenenRunden() {
        // Die Runden, die den Anzeigefehler aufgedeckt haben: einmal wenige
        // Zuege bei viel Zeit, einmal schnell bei vielen Zuegen.
        Einstellungen elfMalSechs = new Einstellungen(11, 6, 300);

        Highscore.Verbesserung langsamAberSparsam = highscore.melde(elfMalSechs, 66, 249);
        assertTrue(langsamAberSparsam.wenigereZuege());
        assertTrue(langsamAberSparsam.schnellereZeit(), "Der erste Eintrag setzt beide Bestwerte");

        Highscore.Verbesserung schnellAberAufwendig = highscore.melde(elfMalSechs, 102, 178);
        assertFalse(schnellAberAufwendig.wenigereZuege(), "102 Zuege sind nicht weniger als 66");
        assertTrue(schnellAberAufwendig.schnellereZeit());

        // Keine einzige Runde hat 66 Zuege UND 178 Sekunden geschafft.
        assertEquals(66, highscore.wenigsteZuege(elfMalSechs));
        assertEquals(178, highscore.schnellsteZeit(elfMalSechs));
    }

    @Test
    void jedeFeldgroesseHatEigeneBestwerte() {
        highscore.melde(VIER_MAL_VIER, 20, 95);

        assertEquals(0, highscore.wenigsteZuege(SECHS_MAL_VIER));
        assertTrue(highscore.melde(SECHS_MAL_VIER, 40, 200).gabEs());
        assertEquals(20, highscore.wenigsteZuege(VIER_MAL_VIER));
    }
}
