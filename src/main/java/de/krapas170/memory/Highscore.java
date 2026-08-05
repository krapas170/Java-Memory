package de.krapas170.memory;

import java.util.prefs.Preferences;

/**
 * Bestleistungen je Feldgroesse, dauerhaft in den Benutzereinstellungen
 * gespeichert (Registry unter Windows, {@code ~/.java} unter Linux).
 *
 * <p>Der Wert 0 bedeutet "noch kein Eintrag vorhanden".</p>
 */
public class Highscore {

    private final Preferences einstellungen;

    public Highscore() {
        this(Preferences.userNodeForPackage(Highscore.class));
    }

    /** Fuer Tests, damit nicht in die echten Benutzereinstellungen geschrieben wird. */
    Highscore(Preferences einstellungen) {
        this.einstellungen = einstellungen;
    }

    public int wenigsteZuege(Einstellungen runde) {
        return einstellungen.getInt(schluessel(runde, "zuege"), 0);
    }

    public int schnellsteZeit(Einstellungen runde) {
        return einstellungen.getInt(schluessel(runde, "zeit"), 0);
    }

    /**
     * Traegt ein Ergebnis ein, sofern es besser als das bisherige ist.
     *
     * @param benoetigteSekunden wie lange der Sieg gedauert hat
     * @return true, wenn dabei mindestens ein Rekord verbessert wurde
     */
    public boolean melde(Einstellungen runde, int zuege, int benoetigteSekunden) {
        boolean rekord = false;

        int bisherigeZuege = wenigsteZuege(runde);
        if (bisherigeZuege == 0 || zuege < bisherigeZuege) {
            einstellungen.putInt(schluessel(runde, "zuege"), zuege);
            rekord = true;
        }

        int bisherigeZeit = schnellsteZeit(runde);
        if (bisherigeZeit == 0 || benoetigteSekunden < bisherigeZeit) {
            einstellungen.putInt(schluessel(runde, "zeit"), benoetigteSekunden);
            rekord = true;
        }

        return rekord;
    }

    private String schluessel(Einstellungen runde, String art) {
        return runde.breite() + "x" + runde.hoehe() + "." + art;
    }
}
