package de.krapas170.memory;

/**
 * Die Konfiguration einer Spielrunde.
 *
 * <p>Unveraenderlich und selbst pruefend: Ein {@code Einstellungen}-Objekt zu
 * besitzen bedeutet, dass die Werte gueltig sind. Frueher lagen diese drei
 * Zahlen als statische Felder in {@code Main} und {@code Menue} und wurden
 * zwischen den Runden nie zurueckgesetzt.</p>
 */
public record Einstellungen(int breite, int hoehe, int sekunden) {

    /** Obergrenze der Felder, ergibt sich aus dem Vorrat an Kartensymbolen. */
    public static final int MAX_FELDER = Kartenblatt.maxFelder();

    public Einstellungen {
        if (breite < 1) {
            throw new IllegalArgumentException("Die Breite muss mindestens 1 sein, war: " + breite);
        }
        if (hoehe < 1) {
            throw new IllegalArgumentException("Die Hoehe muss mindestens 1 sein, war: " + hoehe);
        }
        if (sekunden < 1) {
            throw new IllegalArgumentException("Die Zeit muss mindestens 1 Sekunde sein, war: " + sekunden);
        }
        if ((breite * hoehe) % 2 != 0) {
            throw new IllegalArgumentException(
                    "Hoehe mal Breite muss gerade sein, sonst geht ein Paar nicht auf: " + breite + "x" + hoehe);
        }
        if (breite * hoehe > MAX_FELDER) {
            throw new IllegalArgumentException(
                    "Es sind hoechstens " + MAX_FELDER + " Felder moeglich, gefordert waren: " + (breite * hoehe));
        }
    }

    /** Voreinstellung fuer den ersten Start. */
    public static Einstellungen standard() {
        return new Einstellungen(4, 4, 3 * 60);
    }

    /** Bequemer Konstruktor fuer die Eingabe aus dem Menue. */
    public static Einstellungen ausMinuten(int breite, int hoehe, int minuten) {
        if (minuten < 1) {
            throw new IllegalArgumentException("Die Zeit muss mindestens 1 Minute sein, war: " + minuten);
        }
        return new Einstellungen(breite, hoehe, minuten * 60);
    }

    public int felder() {
        return breite * hoehe;
    }

    public int paare() {
        return felder() / 2;
    }

    public int minuten() {
        return sekunden / 60;
    }
}
