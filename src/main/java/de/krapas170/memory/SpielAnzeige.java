package de.krapas170.memory;

/**
 * Alles, was {@link SpielLogik} von einer Oberflaeche braucht.
 *
 * <p>Dieses Interface ist der Grund, warum die Spiellogik ohne Swing
 * getestet werden kann: Im Spiel implementiert es {@code SpielFeld}, im
 * Test eine Attrappe, die die Aufrufe nur mitschreibt.</p>
 */
public interface SpielAnzeige {

    /** Deckt die Karte auf und zeigt ihr Symbol. */
    void zeigeKarte(int x, int y, char wert);

    /** Dreht die Karte wieder auf die Rueckseite. */
    void verdeckeKarte(int x, int y);

    /** Zwei Karten passen zusammen und bleiben dauerhaft offen. */
    void markierePaar(int x1, int y1, int x2, int y2);

    /** Zwei Karten passen nicht zusammen; sie werden gleich wieder verdeckt. */
    void markiereFehlversuch(int x1, int y1, int x2, int y2);

    /** Der Zugzaehler hat sich geaendert. */
    void zeigeZuege(int zuege);

    /** Alle Paare sind gefunden. */
    void spielGewonnen();

    /**
     * Fuehrt {@code aktion} nach der angegebenen Zeit aus, ohne zu blockieren.
     *
     * <p>Die Oberflaeche benutzt dafuer einen {@link javax.swing.Timer}, damit
     * der Event Dispatch Thread frei bleibt und zwischendurch neu zeichnen
     * kann. Der Test ruft die Aktion einfach sofort auf.</p>
     */
    void verzoegert(int millisekunden, Runnable aktion);
}
