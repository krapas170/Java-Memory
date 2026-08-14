package de.krapas170.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Prueft das Auswerten von {@code version.json}.
 *
 * <p>Diese Tests brauchen kein Netz. Sie decken die einzige Stelle ab, an der
 * das Programm org.json benutzt &ndash; ohne sie wuerde ein Versionssprung
 * dieser Bibliothek von keinem Test beruehrt und erst beim Nutzer auffallen.</p>
 */
class UpdatePruefungTest {

    @Test
    @DisplayName("Das echte Format aus version.json wird gelesen")
    void liestDasAusgelieferteFormat() throws IOException {
        String inhalt = """
                {
                    "version": "1.5.0",
                    "date": "05/08/2026"
                }
                """;
        assertEquals("1.5.0", UpdatePruefung.leseVersionAus(inhalt));
    }

    @Test
    void ignoriertUmgebendeLeerzeichen() throws IOException {
        assertEquals("1.6", UpdatePruefung.leseVersionAus("{\"version\": \"  1.6  \"}"));
    }

    @Test
    void unbekannteFelderStoerenNicht() throws IOException {
        String inhalt = "{\"version\": \"2.0\", \"date\": \"01/01/2027\", \"hinweis\": \"egal\"}";
        assertEquals("2.0", UpdatePruefung.leseVersionAus(inhalt));
    }

    @Test
    @DisplayName("Fehlendes Feld, leeres Feld und falscher Typ werden abgelehnt")
    void lehntUnbrauchbarenInhaltAb() {
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus("{\"date\": \"01/01/2027\"}"));
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus("{\"version\": \"\"}"));
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus("{\"version\": \"   \"}"));
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus("{\"version\": null}"));
    }

    @Test
    @DisplayName("Kaputtes JSON fuehrt zu IOException, nicht zu einem Absturz")
    void lehntKaputtesJsonAb() {
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus(""));
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus("kein json"));
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus("{\"version\": "));
        // Eine HTML-Fehlerseite statt der Datei - der wahrscheinlichste Ausfall
        assertThrows(IOException.class, () -> UpdatePruefung.leseVersionAus("<html><body>404</body></html>"));
    }

    @Test
    @DisplayName("Der gelesene Wert wird mit der eigenen Version verglichen")
    void zusammenspielMitDemVersionsvergleich() throws IOException {
        String serverVersion = UpdatePruefung.leseVersionAus("{\"version\": \"1.6.0\"}");
        assertEquals(true, UpdatePruefung.istUpdateVerfuegbar(serverVersion, "1.5.0"));
        assertEquals(false, UpdatePruefung.istUpdateVerfuegbar(serverVersion, "1.6"));
        assertEquals(false, UpdatePruefung.istUpdateVerfuegbar(serverVersion, "2.0"));
    }
}
