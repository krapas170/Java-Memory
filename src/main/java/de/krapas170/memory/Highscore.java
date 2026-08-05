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
     * Welche Bestwerte eine Runde verbessert hat.
     *
     * <p>Die beiden Werte werden getrennt gefuehrt: Wer wenige Zuege braucht,
     * hat oft laenger nachgedacht, und wer schnell klickt, braucht meist mehr
     * Zuege. Beide Bestwerte koennen deshalb aus verschiedenen Runden stammen
     * &ndash; wer sie zusammen anzeigt, muss das dazusagen.</p>
     */
    public record Verbesserung(boolean wenigereZuege, boolean schnellereZeit) {
        public boolean gabEs() {
            return wenigereZuege || schnellereZeit;
        }
    }

    /**
     * Traegt ein Ergebnis ein, sofern es besser als das bisherige ist.
     *
     * @param benoetigteSekunden wie lange der Sieg gedauert hat
     * @return welche der beiden Bestwerte dabei verbessert wurden
     */
    public Verbesserung melde(Einstellungen runde, int zuege, int benoetigteSekunden) {
        boolean zuegeBesser = false;
        boolean zeitBesser = false;

        int bisherigeZuege = wenigsteZuege(runde);
        if (bisherigeZuege == 0 || zuege < bisherigeZuege) {
            einstellungen.putInt(schluessel(runde, "zuege"), zuege);
            zuegeBesser = true;
        }

        int bisherigeZeit = schnellsteZeit(runde);
        if (bisherigeZeit == 0 || benoetigteSekunden < bisherigeZeit) {
            einstellungen.putInt(schluessel(runde, "zeit"), benoetigteSekunden);
            zeitBesser = true;
        }

        return new Verbesserung(zuegeBesser, zeitBesser);
    }

    private String schluessel(Einstellungen runde, String art) {
        return runde.breite() + "x" + runde.hoehe() + "." + art;
    }
}
