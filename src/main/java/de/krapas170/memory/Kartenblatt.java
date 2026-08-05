package de.krapas170.memory;

/**
 * Liefert die Symbole, die auf den Karten stehen.
 *
 * <p>Die Symbole sind bewusst reines ASCII, damit sie in jeder Schriftart
 * darstellbar sind. Verwechselbare Zeichen sind ausgelassen: kein 'I' neben
 * '1' und kein 'O' neben '0'.</p>
 */
public final class Kartenblatt {

    private static final String SYMBOLE = "ABCDEFGHJKLMNPQRSTUVWXYZ0123456789";

    private Kartenblatt() {
    }

    /** Anzahl der verfuegbaren unterschiedlichen Symbole. */
    public static int anzahlSymbole() {
        return SYMBOLE.length();
    }

    /** Groesstmoegliche Feldanzahl: fuer jedes Symbol genau ein Paar. */
    public static int maxFelder() {
        return anzahlSymbole() * 2;
    }

    /**
     * Symbol fuer das Paar mit der angegebenen Nummer.
     *
     * @param index 0 bis {@link #anzahlSymbole()} - 1
     */
    public static char symbolFuer(int index) {
        if (index < 0 || index >= SYMBOLE.length()) {
            throw new IllegalArgumentException(
                    "Es gibt nur " + SYMBOLE.length() + " Symbole, angefragt war Nummer " + index);
        }
        return SYMBOLE.charAt(index);
    }
}
